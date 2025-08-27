package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {}
