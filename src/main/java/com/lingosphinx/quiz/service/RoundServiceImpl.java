package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Round;
import com.lingosphinx.quiz.domain.Student;
import com.lingosphinx.quiz.domain.StudyList;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.dto.RoundDto;
import com.lingosphinx.quiz.mapper.RoundMapper;
import com.lingosphinx.quiz.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoundServiceImpl implements RoundService {

    private final StudyListRepository studyListRepository;
    private final TrialRepository trialRepository;
    private final QuestionRepository questionRepository;
    private final StudentService studentService;
    private final RoundMapper roundMapper;

    protected Stream<Trial> newTrials(Student student, StudyList studyList, Round round) {
        if(round.getNewCount() <= 0) {
            return Stream.empty();
        }
        var quiz = round.getQuiz();

        var spec = round.getQuiz() != null
                ? QuestionSpecifications.isNew(student, quiz.getId())
                : QuestionSpecifications.isNew(student, studyList);
        spec = spec.and(QuestionSpecifications.randomOrder());

        var questions = questionRepository.findAll(spec, PageRequest.of(0, round.getNewCount()));
        var trials = questions.stream().map(q -> Trial.builder()
                        .question(q)
                        .student(student)
                        .build()).map(Trial.class::cast);
        return trials;
    }

    protected Stream<Trial> dueTrials(Student student, StudyList studyList, Round round) {
        if(round.getDueCount() <= 0) {
            return Stream.empty();
        }
        var quiz = round.getQuiz();

        var spec = quiz != null
                ? TrialSpecifications.isDue(student, quiz.getId())
                : TrialSpecifications.isDue(student, studyList);
        spec = spec.and(TrialSpecifications.randomOrder());

        var trials = trialRepository.findAll(spec, PageRequest.of(0, round.getDueCount())).stream();
        return trials;
    }

    @Override
    public RoundDto create(RoundDto dto) {
        var round = roundMapper.toEntity(dto);
        var student = studentService.readCurrent();
        StudyList studyList = null;
        if(round.getStudyList() != null) {
            studyList = studyListRepository.getReferenceById(round.getStudyList().getId());
        }
        var dueTrials = this.dueTrials(student, studyList, round).toList();
        var newTrials = this.newTrials(student, studyList, round).toList();
        var trials = Stream.concat(dueTrials.stream(), newTrials.stream())
                .collect(Collectors.toCollection(ArrayList::new));
        var entity = Round.builder()
                .studyList(round.getStudyList())
                .quiz(round.getQuiz())
                .trials(trials)
                .newCount(newTrials.size())
                .dueCount(dueTrials.size())
                .build();
        entity.shuffle();
        return roundMapper.toDto(entity);
    }
}