package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.dto.AnswerDto;
import com.lingosphinx.quiz.dto.QuestionDto;
import com.lingosphinx.quiz.dto.QuizDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AnswerMapper {
    AnswerDto toDto(Answer answer);
    Answer toEntity(AnswerDto answerDto);

    @Mapping(target = "answers", ignore = true)
    QuestionDto toDto(Question question);
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDto questionDto);
}