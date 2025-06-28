package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.TrialDto;

import java.util.List;

public interface TrialService {
    TrialDto create(TrialDto trial);
    TrialDto readById(String id);
    List<TrialDto> readAll();
    TrialDto update(String id, TrialDto trial);
    void delete(String id);
}
