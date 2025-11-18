package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Student;
import com.lingosphinx.quiz.dto.StudentDto;
import com.lingosphinx.quiz.dto.StudentRegistrationDto;

public interface StudentService extends CrudService<StudentDto> {

    StudentDto register(StudentRegistrationDto studentRegistration);

    Student readCurrent();
}
