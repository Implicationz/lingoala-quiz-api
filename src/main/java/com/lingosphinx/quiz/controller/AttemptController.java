package com.lingosphinx.quiz.controller;

import com.lingosphinx.quiz.dto.AttemptDto;
import com.lingosphinx.quiz.service.AttemptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attempt")
@RequiredArgsConstructor
@Tag(name = "Attempt API")
public class AttemptController {

    private final AttemptService attemptService;


    @PostMapping("/practice/language")
    public ResponseEntity<AttemptDto> create(@RequestBody AttemptDto attempt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attemptService.create(attempt));
    }
}