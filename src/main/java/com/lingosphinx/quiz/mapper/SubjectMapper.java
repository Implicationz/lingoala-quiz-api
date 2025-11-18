package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Subject;
import com.lingosphinx.quiz.domain.Topic;
import com.lingosphinx.quiz.dto.SubjectDto;
import com.lingosphinx.quiz.dto.TopicDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SubjectMapper {
    SubjectDto toDto(Subject subject);
    Subject toEntity(SubjectDto subjectDto);

    @Mapping(ignore = true, target = "subject")
    TopicDto toDto(Topic topic);
    @Mapping(ignore = true, target = "subject")
    Topic toEntity(TopicDto topicDto);

    @Mapping(ignore = true, target = "topics")
    void updateEntityFromDto(SubjectDto subjectDto, @MappingTarget Subject existingSubject);
    void updateEntityFromDto(TopicDto topicDto, @MappingTarget Topic existingTopic);
}