package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.dto.AnswerDto;
import com.lingosphinx.quiz.dto.QuestionDto;
import com.lingosphinx.quiz.dto.QuizDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface QuestionMapper {
    QuestionDto toDto(Question question);
    Question toEntity(QuestionDto questionDto);

    @Mapping(target = "questions", ignore = true)
    QuizDto toDto(Quiz quiz);
    @Mapping(target = "questions", ignore = true)
    Quiz toEntity(QuizDto quizDto);

    @Mapping(target = "question", ignore = true)
    AnswerDto toDto(Answer answer);
    @Mapping(target = "question", ignore = true)
    Answer toEntity(AnswerDto answerDto);

    @AfterMapping
    default void linkAnswers(@MappingTarget Question question) {
        question.linkAnswers();
    }

    void updateEntityFromDto(QuestionDto questionDto, @MappingTarget Question existing);

    void updateFromDto(AnswerDto answerDto, @MappingTarget Answer answer);
}