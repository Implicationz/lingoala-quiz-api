package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.domain.StudyList;
import com.lingosphinx.quiz.domain.StudyListItem;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.dto.StudyListDto;
import com.lingosphinx.quiz.dto.StudyListItemDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface StudyListItemMapper {
    StudyListItemDto toDto(StudyListItem studyListItem);
    StudyListItem toEntity(StudyListItemDto studyListItemDto);

    @Mapping(target = "items", ignore = true)
    StudyListDto toDto(StudyList studyList);
    @Mapping(target = "items", ignore = true)
    StudyList toEntity(StudyListDto studyListDto);

    @Mapping(target = "questions", ignore = true)
    QuizDto toDto(Quiz quiz);
    @Mapping(target = "questions", ignore = true)
    Quiz toEntity(QuizDto quizDto);

    @Mapping(target = "studyList", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    void updateEntityFromDto(StudyListItemDto studyListItemDto, @MappingTarget StudyListItem studyListItem);
}