package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Tag;
import com.lingosphinx.quiz.dto.TagDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TagMapper {
    TagDto toDto(Tag quiz);
    Tag toEntity(TagDto quizDto);

    void updateEntityFromDto(TagDto quizDto, @MappingTarget Tag existingTag);
}
