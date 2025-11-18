package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.SubjectDto;

import java.util.List;

public interface SubjectService extends CrudService<SubjectDto> {
    List<SubjectDto> readAll(String name);
}
