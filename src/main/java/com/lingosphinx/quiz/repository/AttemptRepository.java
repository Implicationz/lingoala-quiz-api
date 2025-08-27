package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttemptRepository extends JpaRepository<Attempt, Long>, JpaSpecificationExecutor<Attempt> {

}
