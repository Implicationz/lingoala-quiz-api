package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Subject;
import com.lingosphinx.quiz.dto.SubjectDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SubjectMapper {
    SubjectDto toDto(Subject topic);
    Subject toEntity(SubjectDto topicDto);
}