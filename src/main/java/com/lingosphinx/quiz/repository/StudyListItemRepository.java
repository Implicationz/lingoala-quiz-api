package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.StudyList;
import com.lingosphinx.quiz.domain.StudyListItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudyListItemRepository extends JpaRepository<StudyListItem, Long> {
}
