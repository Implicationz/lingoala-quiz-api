package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.SchedulingStrategy;
import com.lingosphinx.quiz.dto.AttemptDto;
import com.lingosphinx.quiz.mapper.AttemptMapper;
import com.lingosphinx.quiz.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AttemptServiceImpl implements AttemptService {

    private final TrialRepository trialRepository;
    private final AttemptRepository attemptRepository;
    private final QuestionRepository questionRepository;
    private final StudentRepository studentRepository;
    private final AttemptMapper attemptMapper;
    private final SchedulingStrategy schedulingStrategy;

    @Override
    public AttemptDto create(AttemptDto dto) {
        var attempt = attemptMapper.toEntity(dto);

        var trial = Optional.ofNullable(attempt.getTrial().getId())
                .filter(id -> id > 0)
                .flatMap(trialRepository::findById)
                .orElseGet(() -> {
                    var newTrial = attempt.getTrial();
                    newTrial.setQuestion(questionRepository.getReferenceById(newTrial.getQuestion().getId()));
                    newTrial.setStudent(studentRepository.getReferenceById(newTrial.getStudent().getId()));
                    return newTrial;
                });

        trial.setSchedulingStrategy(schedulingStrategy);
        trial.apply(attempt);
        trialRepository.save(trial);

        attempt.setTrial(trial);
        attemptRepository.save(attempt);
        log.info("Attempt created, id={}", attempt.getId());

        return attemptMapper.toDto(attempt);
    }
}