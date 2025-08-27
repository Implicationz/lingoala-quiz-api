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
public interface StudyListMapper {
    StudyListDto toDto(StudyList studyList);
    StudyList toEntity(StudyListDto studyListDto);

    @Mapping(target = "studyList", ignore = true)
    StudyListItemDto toDto(StudyListItem studyListItem);
    @Mapping(target = "studyList", ignore = true)
    StudyListItem toEntity(StudyListItemDto studyListItemDto);

    @Mapping(target = "questions", ignore = true)
    QuizDto toDto(Quiz quiz);
    @Mapping(target = "questions", ignore = true)
    Quiz toEntity(QuizDto quizDto);


    void updateEntityFromDto(StudyListDto studyListDto, @MappingTarget StudyList existingStudyList);

    void updateEntityFromDto(StudyListItemDto studyListItemDto, @MappingTarget StudyListItem existingStudyListItem);
}
