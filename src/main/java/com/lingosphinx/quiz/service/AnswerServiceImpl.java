package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Override
    public void syncAnswers(Question existing, List<Answer> incomingAnswers) {
        var existingMap = existing.getAnswers().stream()
                .collect(Collectors.toMap(Answer::getId, a -> a));

        var updatedList = new ArrayList<Answer>();

        for (var incoming : incomingAnswers) {
            if (incoming.getId() == null) {
                incoming.setQuestion(existing);
                updatedList.add(incoming);
            } else {
                var existingAnswer = existingMap.remove(incoming.getId());
                if (existingAnswer != null) {
                    existingAnswer.setText(incoming.getText());
                    existingAnswer.setCorrect(incoming.isCorrect());
                    updatedList.add(existingAnswer);
                }
            }
        }

        deleteAll(existing, new HashSet<>(updatedList));

        existing.getAnswers().clear();
        updatedList.forEach(a -> {
            a.setQuestion(existing);
            existing.getAnswers().add(a);
        });
    }

    @Override
    public void createAll(Question parent, List<Answer> newAnswers) {
        newAnswers.forEach(a -> a.setQuestion(parent));
        parent.getAnswers().addAll(newAnswers);
    }

    @Override
    public void deleteAll(Question parent, Set<Answer> toKeep) {
        parent.getAnswers().removeIf(a -> !toKeep.contains(a));
    }
}
