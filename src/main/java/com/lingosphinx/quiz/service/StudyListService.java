package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.StudyListDto;

import java.util.List;

public interface StudyListService {
    StudyListDto create(StudyListDto studyList);
    StudyListDto readById(Long id);
    List<StudyListDto> readAll();
    StudyListDto update(Long id, StudyListDto studyList);
    void delete(Long id);

    StudyListDto activate(LanguageCode languageCode);
}
