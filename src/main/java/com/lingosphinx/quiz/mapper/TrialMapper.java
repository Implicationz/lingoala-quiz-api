package com.lingosphinx.quiz.mapper;

import com.lingosphinx.quiz.domain.Attempt;
import com.lingosphinx.quiz.domain.Question;
import com.lingosphinx.quiz.domain.Trial;
import com.lingosphinx.quiz.dto.AttemptDto;
import com.lingosphinx.quiz.dto.QuestionDto;
import com.lingosphinx.quiz.dto.TrialDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TrialMapper {
    TrialDto toDto(Trial topic);
    Trial toEntity(TrialDto topicDto);

    @Mapping(target = "trial", ignore = true)
    AttemptDto toDto(Attempt attempt);

    @Mapping(target = "trial", ignore = true)
    Attempt toEntity(AttemptDto attemptDto);

    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "answers", ignore = true)
    QuestionDto toDto(Question question);
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDto questionDto);
    
    void updateEntityFromDto(TrialDto trialDto, @MappingTarget  Trial existingTrial);
}