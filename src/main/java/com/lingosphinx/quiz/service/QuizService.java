package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.dto.QuizDto;

import java.util.List;

public interface QuizService {
    QuizDto create(QuizDto quiz);
    QuizDto readById(String id);
    List<QuizDto> readAll();
    QuizDto update(String id, QuizDto quiz);
    void delete(String id);
}
