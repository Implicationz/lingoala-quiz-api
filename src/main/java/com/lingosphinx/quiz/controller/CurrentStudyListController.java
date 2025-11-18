package com.lingosphinx.quiz.controller;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.StudyListDto;
import com.lingosphinx.quiz.service.StudyListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me/study-list")
@RequiredArgsConstructor
@Tag(name = "CurrentStudyList API")
public class CurrentStudyListController {

    private final StudyListService studyListService;

    @PostMapping("{language}")
    public ResponseEntity<StudyListDto> activate(@PathVariable String language) {
        return ResponseEntity.ok(studyListService.activate(LanguageCode.valueOf(language)));
    }

}