package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    @EntityGraph(attributePaths = {"topic", "topic.subject", "questions"})
    Optional<Question> findWithQuestionsById(Long id);

    @EntityGraph(attributePaths = {"topic", "topic.subject"})
    List<Question> findAll();

    @EntityGraph(attributePaths = {"quiz"})
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    @Query("SELECT q.quiz.id, COUNT(q) FROM Question q " +
            "WHERE q.quiz.id IN :quizIds AND " +
            "NOT EXISTS (SELECT t FROM Trial t WHERE t.question = q AND t.student = :student) " +
            "GROUP BY q.quiz.id")
    List<Object[]> countNewQuestionsByQuizIds(@Param("student") Student student, @Param("quizIds") List<Long> quizIds);
}
