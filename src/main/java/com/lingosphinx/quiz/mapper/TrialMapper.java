package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Attempt;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.dto.AttemptDto;
import com.lingosphinx.quiz.dto.TrialDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {QuestionMapper.class})
public interface TrialMapper {
    TrialDto toDto(Trial topic);
    Trial toEntity(TrialDto topicDto);

    @Mapping(target = "trial", ignore = true)
    AttemptDto toDto(Attempt attempt);

    @Mapping(target = "trial", ignore = true)
    Attempt toEntity(AttemptDto attemptDto);
    
    void updateEntityFromDto(TrialDto trialDto, @MappingTarget  Trial existingTrial);
}