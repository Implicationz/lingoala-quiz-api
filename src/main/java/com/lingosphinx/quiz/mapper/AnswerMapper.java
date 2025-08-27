package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.dto.AnswerDto;
import com.lingosphinx.quiz.dto.QuestionDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AnswerMapper {
    AnswerDto toDto(Answer answer);
    Answer toEntity(AnswerDto answerDto);

    @Mapping(target = "answers", ignore = true)
    QuestionDto toDto(Question question);
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDto questionDto);
}