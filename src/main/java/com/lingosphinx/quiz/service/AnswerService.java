package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Question;

import java.util.List;
import java.util.Set;

public interface AnswerService {
    void syncAnswers(Question existing, List<Answer> incomingAnswers);

    void createAll(Question parent, List<Answer> newAnswers);

    void deleteAll(Question parent, Set<Answer> toKeep);
}
