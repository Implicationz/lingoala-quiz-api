package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Trial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrialRepository extends JpaRepository<Trial, Long>, JpaSpecificationExecutor<Trial> {

    @EntityGraph(attributePaths = {"question", "question.quiz"})
    Optional<Trial> findById(Long id);

    @EntityGraph(attributePaths = {"topic", "topic.subject", "question"})
    List<Trial> findAll();

    @EntityGraph(attributePaths = {"question", "question.quiz"})
    Page<Trial> findAll(Specification<Trial> spec, Pageable pageable);


    @Query("SELECT q.quiz.id, COUNT(t) FROM Trial t " +
            "JOIN t.question q " +
            "WHERE q.quiz.id IN :quizIds AND t.userId = :userId AND t.nextDueDate <= :now " +
            "GROUP BY q.quiz.id")
    List<Object[]> countDueTrialsByQuizIds(@Param("userId") UUID userId, @Param("quizIds") List<Long> quizIds, @Param("now") Instant now);
}
