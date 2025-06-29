package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.dto.QuizDto;

import java.util.List;

public interface QuizService {
    QuizDto create(QuizDto quiz);
    QuizDto readById(Long id);
    List<QuizDto> readAll();
    QuizDto update(Long id, QuizDto quiz);
    void delete(Long id);
}
