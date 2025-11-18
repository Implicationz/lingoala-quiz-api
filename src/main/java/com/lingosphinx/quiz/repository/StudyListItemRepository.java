package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.StudyListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyListItemRepository extends JpaRepository<StudyListItem, Long> {
}
