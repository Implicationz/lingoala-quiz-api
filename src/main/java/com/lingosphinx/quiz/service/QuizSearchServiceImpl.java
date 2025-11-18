package com.lingosphinx.quiz.service;

import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.quiz.client.GamificationClient;
import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.domain.QuizSearch;
import com.lingosphinx.quiz.domain.QuizSearchHit;
import com.lingosphinx.quiz.domain.QuizSearchSort;
import com.lingosphinx.quiz.dto.QuizSearchDto;
import com.lingosphinx.quiz.mapper.QuizSearchMapper;
import com.lingosphinx.quiz.repository.QuizRepository;
import com.lingosphinx.quiz.repository.QuizSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizSearchServiceImpl implements QuizSearchService {

    private final StudentService studentService;
    private final QuizRepository quizRepository;
    private final QuizSearchMapper quizSearchMapper;
    private final GamificationClient gamificationClient;

    protected List<QuizSearchHit> toHits(List<Quiz> found, List<GoalDto> goals) {
        var goalByRef = goals.stream()
                .collect(Collectors.toMap(
                        goal -> Long.parseLong(goal.getDefinition().getReference()),
                        Function.identity()
                ));

        var hits = found.stream()
                .map(quiz -> {
                    var goal = goalByRef.getOrDefault(quiz.getId(), null);
                    return QuizSearchHit.builder()
                            .quiz(quiz)
                            .goal(goal)
                            .build();
                })
                .toList();
        return hits;
    }
    @Override
    public QuizSearchDto create(QuizSearchDto quizSearchDto) {
        var quizSearch = quizSearchMapper.toEntity(quizSearchDto);
        var found = search(quizSearch);
        var references = found.stream().map(e -> e.getId().toString()).toList();
        var goals = gamificationClient.getQuizGoals(quizSearch.getLanguage(), references);
        var hits = toHits(found, goals);
        var savedQuizSearch = QuizSearch.builder()
                .subject(quizSearch.getSubject())
                .topic(quizSearch.getTopic())
                .hits(hits)
                .build();
        log.info("QuizSearch created with.");
        return quizSearchMapper.toDto(savedQuizSearch);
    }

    protected List<Quiz> search(QuizSearch search) {
        Specification<Quiz> spec = Specification.where(null);

        if (search.getLanguage() != null) {
            spec = spec.and(QuizSpecifications.hasLanguage(search.getLanguage()));
        }
        if (search.getSubject() != null) {
            spec = spec.and(QuizSpecifications.hasSubject(search.getSubject()));
        }
        if (search.getTopic() != null) {
            spec = spec.and(QuizSpecifications.hasTopic(search.getTopic()));
        }

        if (QuizSearchSort.RANDOM.equals(search.getSort())) {
            spec = spec.and(QuizSpecifications.randomOrder());
        }

        var student = studentService.readCurrent();
        spec = spec.and(QuizSpecifications.isVisible(student));

        return quizRepository.findAll(spec);
    }


}