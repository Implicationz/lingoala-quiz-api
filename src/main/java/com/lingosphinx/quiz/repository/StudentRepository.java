package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(UUID userId);
}
