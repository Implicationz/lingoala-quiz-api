package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Quiz;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    void createAll(Quiz quiz, List<Question> questions);

    void syncQuestions(Quiz existingQuiz, List<Question> incomingQuestions);

    void deleteAll(Quiz quiz, Set<Question> toKeep);
}
