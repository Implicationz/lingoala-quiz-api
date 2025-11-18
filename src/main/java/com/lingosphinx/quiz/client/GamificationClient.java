package com.lingosphinx.quiz.client;

import com.lingosphinx.gamification.dto.GoalDto;
import com.lingosphinx.gamification.dto.GoalTypeDto;
import com.lingosphinx.gamification.dto.GoalZoneDto;
import com.lingosphinx.quiz.configuration.FeignClientConfiguration;
import com.lingosphinx.quiz.domain.LanguageCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "gamification-service",
        url = "${lingosphinx.gamification.client.url}",
        configuration = FeignClientConfiguration.class
)
public interface GamificationClient {
    @GetMapping("/goal")
    List<GoalDto> getGoals(
            @RequestParam("zone") String zone,
            @RequestParam("type") String type,
            @RequestParam(value = "references", required = false) List<String> references
    );

    @PostMapping("/goal-type")
    GoalTypeDto createType(@RequestBody GoalTypeDto typeDto);

    @PostMapping("/goal-zone")
    GoalZoneDto createZone(@RequestBody GoalZoneDto zoneDto);

    default List<GoalDto> getQuizGoals(LanguageCode code) {
        return getQuizGoals(code, null);
    }

    default List<GoalDto> getQuizGoals(LanguageCode code, List<String> references) {
        return getGoals(code.getValue(), "quiz", references);
    }
}