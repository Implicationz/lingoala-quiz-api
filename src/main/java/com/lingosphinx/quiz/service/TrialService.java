package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.TrialDto;

import java.util.List;

public interface TrialService {
    TrialDto create(TrialDto trial);
    TrialDto readById(Long id);
    List<TrialDto> readAll();
    TrialDto update(Long id, TrialDto trial);
    void delete(Long id);
}
