package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.*;
import com.lingosphinx.quiz.dto.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {TopicMapper.class})
public interface RoundMapper {
    RoundDto toDto(Round round);
    Round toEntity(RoundDto roundDto);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "student", ignore = true)
    StudyListDto toDto(StudyList studyList);
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "student", ignore = true)
    StudyList toEntity(StudyListDto studyListDto);

    @Mapping(target = "attempts", ignore = true)
    TrialDto toDto(Trial trial);

    @Mapping(target = "attempts", ignore = true)
    Trial toEntity(TrialDto trialDto);

    @Mapping(target = "quiz", ignore = true)
    QuestionDto toDto(Question question);

    @Mapping(target = "quiz", ignore = true)
    Question toEntity(QuestionDto questionDto);

    @Mapping(target = "question", ignore = true)
    AnswerDto toDto(Answer answer);
    @Mapping(target = "question", ignore = true)
    Answer toEntity(AnswerDto answerDto);
}
