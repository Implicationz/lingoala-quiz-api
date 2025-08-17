package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.domain.Trial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TrialRepository extends JpaRepository<Trial, Long>, JpaSpecificationExecutor<Trial> {

    @EntityGraph(attributePaths = {"question", "question.quiz"})
    Optional<Trial> findById(Long id);

    @EntityGraph(attributePaths = {"topic", "topic.subject", "question"})
    List<Trial> findAll();

    @EntityGraph(attributePaths = {"question", "question.quiz"})
    Page<Trial> findAll(Specification<Trial> spec, Pageable pageable);
}
