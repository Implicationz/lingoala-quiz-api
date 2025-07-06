package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.dto.AnswerDto;
import com.lingosphinx.quiz.dto.QuestionDto;
import com.lingosphinx.quiz.dto.QuizDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {TopicMapper.class})
public interface QuizMapper {
    QuizDto toDto(Quiz quiz);
    Quiz toEntity(QuizDto quizDto);

    @Mapping(target = "quiz", ignore = true)
    QuestionDto toDto(Question question);

    @Mapping(target = "quiz", ignore = true)
    Question toEntity(QuestionDto questionDto);

    @Mapping(target = "question", ignore = true)
    AnswerDto toDto(Answer answer);
    @Mapping(target = "question", ignore = true)
    Answer toEntity(AnswerDto answerDto);

    @AfterMapping
    default void linkQuestions(QuizDto dto, @MappingTarget Quiz quiz) {
        quiz.linkQuestions();
    }

    void updateEntityFromDto(QuizDto quizDto, @MappingTarget Quiz existingQuiz);
}
