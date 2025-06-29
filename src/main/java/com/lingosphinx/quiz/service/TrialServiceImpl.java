package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.TrialDto;
import com.lingosphinx.quiz.mapper.TrialMapper;
import com.lingosphinx.quiz.repository.TrialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TrialServiceImpl implements TrialService {

    private final TrialRepository trialRepository;
    private final TrialMapper trialMapper;

    @Override
    public TrialDto create(TrialDto trialDto) {
        var trial = trialMapper.toEntity(trialDto);
        var savedTrial = trialRepository.save(trial);
        log.info("Trial created successfully: id={}", savedTrial.getId());
        return trialMapper.toDto(savedTrial);
    }

    @Override
    @Transactional(readOnly = true)
    public TrialDto readById(Long id) {
        var trial = trialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trial not found"));
        log.info("Trial read successfully: id={}", id);
        return trialMapper.toDto(trial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrialDto> readAll() {
        var result = trialRepository.findAll().stream()
                .map(trialMapper::toDto)
                .toList();
        log.info("All trials read successfully, count={}", result.size());
        return result;
    }

    @Override
    public TrialDto update(Long id, TrialDto trialDto) {
        var existingTrial = trialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trial not found"));

        trialMapper.updateEntityFromDto(trialDto, existingTrial);

        var savedTrial = trialRepository.save(existingTrial);
        log.info("Trial updated successfully: id={}", savedTrial.getId());
        return trialMapper.toDto(savedTrial);
    }

    @Override
    public void delete(Long id) {
        trialRepository.deleteById(id);
        log.info("Trial deleted successfully: id={}", id);
    }
}