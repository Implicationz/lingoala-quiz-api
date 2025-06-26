package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    SubjectDto create(SubjectDto subject);
    SubjectDto readById(String id);
    List<SubjectDto> readAll();
    SubjectDto update(String id, SubjectDto subject);
    void delete(String id);
}
