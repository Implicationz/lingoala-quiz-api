package com.lingosphinx.quiz.controller;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Overview;
import com.lingosphinx.quiz.service.OverviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/overview")
@RequiredArgsConstructor
@Tag(name = "Overview API")
public class OverviewController {

    private final OverviewService overviewService;


    @GetMapping("/{language}")
    public ResponseEntity<Overview> read(@PathVariable LanguageCode language) {
        return ResponseEntity.status(HttpStatus.CREATED).body(overviewService.readByLanguage(language));
    }
}