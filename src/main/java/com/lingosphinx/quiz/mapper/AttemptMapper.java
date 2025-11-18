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

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface AttemptMapper {
    AttemptDto toDto(Attempt topic);
    Attempt toEntity(AttemptDto topicDto);

    @Mapping(target = "attempts", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "question", ignore = true)
    TrialDto toDto(Trial trial);

    @Mapping(target = "attempts", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "question", ignore = true)
    Trial toEntity(TrialDto trialDto);
}