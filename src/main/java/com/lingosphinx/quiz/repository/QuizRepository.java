package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, String> {
    @EntityGraph(attributePaths = {"topic", "topic.subject", "questions"})
    Optional<Quiz> findWithQuestionsById(String id);

    @EntityGraph(attributePaths = {"topic", "topic.subject"})
    List<Quiz> findAll();
}
