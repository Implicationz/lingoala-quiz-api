package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.SchedulingStrategy;
import com.lingosphinx.quiz.dto.AttemptDto;
import com.lingosphinx.quiz.mapper.AttemptMapper;
import com.lingosphinx.quiz.repository.AttemptRepository;
import com.lingosphinx.quiz.repository.TrialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AttemptServiceImpl implements AttemptService {

    private final TrialRepository trialRepository;
    private final AttemptRepository attemptRepository;
    private final AttemptMapper attemptMapper;
    private final SchedulingStrategy schedulingStrategy;

    @Override
    public AttemptDto create(AttemptDto dto) {
        var attempt = attemptMapper.toEntity(dto);
        var trial = attempt.getTrial();

        var id = trial.getId();
        var exists = id != null && id > 0;
        if(exists) {
            trial = trialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Trial not found"));
        }

        trial.setSchedulingStrategy(schedulingStrategy);
        trial.apply(attempt);
        trialRepository.save(trial);

        attempt.setTrial(trial);
        attemptRepository.save(attempt);
        return attemptMapper.toDto(attempt);
    }
}