package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @EntityGraph(attributePaths = {"questions"})
    Optional<Quiz> findWithQuestionsById(Long id);

    List<Quiz> findAll();
}
