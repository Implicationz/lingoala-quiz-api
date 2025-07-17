package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Tag;
import com.lingosphinx.quiz.dto.AnswerDto;
import com.lingosphinx.quiz.dto.QuestionDto;
import com.lingosphinx.quiz.dto.TagDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TagMapper {
    TagDto toDto(Tag quiz);
    Tag toEntity(TagDto quizDto);

    void updateEntityFromDto(TagDto quizDto, @MappingTarget Tag existingTag);
}
