package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.domain.Trial;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TrialRepository extends JpaRepository<Trial, String>, JpaSpecificationExecutor<Trial> {

    @EntityGraph(attributePaths = {"topic", "topic.subject", "question"})
    List<Trial> findAll();
}
