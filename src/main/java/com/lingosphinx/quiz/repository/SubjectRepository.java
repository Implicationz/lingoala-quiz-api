package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {}
