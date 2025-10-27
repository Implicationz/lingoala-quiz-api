package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.StudyListItemDto;

import java.util.List;

public interface StudyListItemService {
    StudyListItemDto create(StudyListItemDto studyListItem);
    StudyListItemDto readById(Long id);
    List<StudyListItemDto> readAll();
    StudyListItemDto update(Long id, StudyListItemDto studyListItem);
    void delete(Long id);
}
