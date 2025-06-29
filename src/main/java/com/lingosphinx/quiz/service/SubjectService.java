package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    SubjectDto create(SubjectDto subject);
    SubjectDto readById(Long id);
    List<SubjectDto> readAll();
    SubjectDto update(Long id, SubjectDto subject);
    void delete(Long id);
}
