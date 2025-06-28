package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Attempt;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.dto.AttemptDto;
import com.lingosphinx.quiz.dto.TrialDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {AnswerMapper.class})
public interface AttemptMapper {
    AttemptDto toDto(Attempt topic);
    Attempt toEntity(AttemptDto topicDto);

    @Mapping(target = "attempts", ignore = true)
    TrialDto toDto(Trial trial);

    @Mapping(target = "attempts", ignore = true)
    Trial toEntity(TrialDto trialDto);
}