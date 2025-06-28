package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.dto.RoundDto;

import java.util.List;

public interface RoundService {
    RoundDto create(RoundDto round);
}
