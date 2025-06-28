package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    @EntityGraph(attributePaths = {"topic", "topic.subject", "questions"})
    Optional<Question> findWithQuestionsById(Long id);

    @EntityGraph(attributePaths = {"topic", "topic.subject"})
    List<Question> findAll();
}
