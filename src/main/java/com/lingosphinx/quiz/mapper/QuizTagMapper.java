package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.QuizTag;
import com.lingosphinx.quiz.dto.QuizTagDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface QuizTagMapper {
    QuizTagDto toDto(QuizTag quiz);
    QuizTag toEntity(QuizTagDto quizDto);

    void updateEntityFromDto(QuizTagDto quizDto, @MappingTarget QuizTag existingQuizTag);
}
