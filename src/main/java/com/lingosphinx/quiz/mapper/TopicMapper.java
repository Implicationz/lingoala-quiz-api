package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Topic;
import com.lingosphinx.quiz.dto.TopicDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {SubjectMapper.class})
public interface TopicMapper {
    TopicDto toDto(Topic topic);
    Topic toEntity(TopicDto topicDto);
}