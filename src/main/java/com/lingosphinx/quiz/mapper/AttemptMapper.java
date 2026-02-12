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
    TrialDto toDto(Trial trial);

    @Mapping(target = "attempts", ignore = true)
    Trial toEntity(TrialDto trialDto);

    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "answers", ignore = true)
    QuestionDto toDto(Question question);
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionDto questionDto);
}