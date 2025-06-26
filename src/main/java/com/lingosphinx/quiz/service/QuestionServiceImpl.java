package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Quiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final AnswerService answerService;

    public QuestionServiceImpl(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public void createAll(Quiz quiz, List<Question> questions) {
        for (var question : questions) {
            question.setQuiz(quiz);
            if (question.getAnswers() != null) {
                for (var answer : question.getAnswers()) {
                    answer.setQuestion(question);
                }
            }
            quiz.getQuestions().add(question);
        }
    }

    @Override
    public void syncQuestions(Quiz existingQuiz, List<Question> incomingQuestions) {
        var existingMap = existingQuiz.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        var updatedList = new ArrayList<Question>();
        var newQuestions = new ArrayList<Question>();

        for (var incoming : incomingQuestions) {
            if (incoming.getId() == null) {
                incoming.setQuiz(existingQuiz);
                newQuestions.add(incoming);
                updatedList.add(incoming);
                continue;
            }

            var existing = existingMap.remove(incoming.getId());
            if (existing != null) {
                existing.setText(incoming.getText());
                existing.setTranslation(incoming.getTranslation());
                existing.setTranscription(incoming.getTranscription());
                existing.setExplanation(incoming.getExplanation());
                existing.setDifficulty(incoming.getDifficulty());
                answerService.syncAnswers(existing, incoming.getAnswers());
                updatedList.add(existing);
            }
        }

        if (!newQuestions.isEmpty()) {
            this.createAll(existingQuiz, newQuestions);
        }

        deleteAll(existingQuiz, new HashSet<>(updatedList));

        existingQuiz.getQuestions().clear();
        updatedList.forEach(q -> {
            q.setQuiz(existingQuiz);
            existingQuiz.getQuestions().add(q);
        });
    }

    @Override
    public void deleteAll(Quiz quiz, Set<Question> toKeep) {
        quiz.getQuestions().removeIf(q -> !toKeep.contains(q));
    }
}
