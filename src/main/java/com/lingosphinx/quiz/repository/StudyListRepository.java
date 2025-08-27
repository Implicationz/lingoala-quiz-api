package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.StudyList;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudyListRepository extends JpaRepository<StudyList, Long> {
    @Override
    @EntityGraph(attributePaths = {"items", "items.quiz"})
    Optional<StudyList> findById(Long id);

    @EntityGraph(attributePaths = {"items", "items.quiz"})
    Optional<StudyList> findByUserIdAndLanguage(UUID userId, LanguageCode language);
}
