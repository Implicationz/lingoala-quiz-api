package com.lingosphinx.quiz.mapper;


import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.domain.QuizSearch;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.dto.QuizSearchDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface QuizSearchMapper {
    
    QuizSearchDto toDto(QuizSearch quizSearch);
    QuizSearch toEntity(QuizSearchDto quizSearchDto);

    @Mapping(target = "questions", ignore = true)
    QuizDto toDto(Quiz quiz);
    @Mapping(target = "questions", ignore = true)
    Quiz toEntity(QuizDto quizDto);
}
