package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Round;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.dto.RoundDto;
import com.lingosphinx.quiz.mapper.QuizMapper;
import com.lingosphinx.quiz.mapper.RoundMapper;
import com.lingosphinx.quiz.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoundServiceImpl implements RoundService {

    private final TrialRepository trialRepository;
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final RoundMapper roundMapper;

    protected Stream<Trial> newTrials(Round round) {
        if(round.getNewCount() <= 0) {
            return Stream.empty();
        }
        var currentUserId = this.userService.getCurrentUserId();
        var quiz = round.getQuiz();

        var spec = round.getQuiz() != null
                ? QuestionSpecifications.isNew(currentUserId, quiz.getId())
                : QuestionSpecifications.isNew(currentUserId, round.getLanguage());
        spec = spec.and(QuestionSpecifications.randomOrder());

        var questions = questionRepository.findAll(spec, PageRequest.of(0, round.getNewCount()));
        var trials = questions.stream().map(q -> Trial.builder()
                        .question(q)
                        .userId(currentUserId)
                        .build());
        return trials;
    }

    protected Stream<Trial> dueTrials(Round round) {
        if(round.getDueCount() <= 0) {
            return Stream.empty();
        }
        var currentUserId = this.userService.getCurrentUserId();
        var quiz = round.getQuiz();

        var spec = quiz != null
                ? TrialSpecifications.isDue(currentUserId, quiz.getId())
                : TrialSpecifications.isDue(currentUserId, round.getLanguage());
        spec = spec.and(TrialSpecifications.randomOrder());

        var trials = trialRepository.findAll(spec, PageRequest.of(0, round.getDueCount())).stream();
        return trials;
    }

    @Override
    public RoundDto create(RoundDto dto) {
        var round = roundMapper.toEntity(dto);

        var dueTrials = this.dueTrials(round);
        var newTrials = this.newTrials(round);
        var trials = Stream.concat(dueTrials, newTrials).toList();
        var entity = Round.builder()
                .language(round.getLanguage())
                .quiz(round.getQuiz())
                .trials(trials)
                .dueCount(trials.size())
                .build();

        return roundMapper.toDto(entity);
    }
}