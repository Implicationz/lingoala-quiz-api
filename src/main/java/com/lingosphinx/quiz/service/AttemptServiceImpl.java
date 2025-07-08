package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Attempt;
import com.lingosphinx.quiz.domain.SchedulingStrategy;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.dto.AttemptDto;
import com.lingosphinx.quiz.mapper.AttemptMapper;
import com.lingosphinx.quiz.mapper.TrialMapper;
import com.lingosphinx.quiz.repository.QuestionRepository;
import com.lingosphinx.quiz.repository.QuestionSpecifications;
import com.lingosphinx.quiz.repository.TrialRepository;
import com.lingosphinx.quiz.repository.TrialSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AttemptServiceImpl implements AttemptService {

    private final TrialRepository trialRepository;
    private final AttemptMapper attemptMapper;
    private final SchedulingStrategy schedulingStrategy;

    @Override
    public AttemptDto create(AttemptDto dto) {
        var attempt = attemptMapper.toEntity(dto);
        var id = attempt.getTrial().getId();
        var exists = id != null && id > 0;
        if(exists) {
            var found = trialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Trial not found"));
            attempt.setTrial(found);
        }
        var trial = attempt.getTrial();
        trial.setSchedulingStrategy(schedulingStrategy);
        trial.apply(attempt);
        trialRepository.save(trial);
        return attemptMapper.toDto(attempt);
    }
}