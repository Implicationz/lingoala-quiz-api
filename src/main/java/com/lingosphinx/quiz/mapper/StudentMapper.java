package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Student;
import com.lingosphinx.quiz.dto.StudentDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface StudentMapper {
    StudentDto toDto(Student student);
    Student toEntity(StudentDto studentDto);

    void updateEntityFromDto(StudentDto studentDto, @MappingTarget  Student existingStudent);
}