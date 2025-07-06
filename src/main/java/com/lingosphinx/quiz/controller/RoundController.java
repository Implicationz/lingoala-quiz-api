package com.lingosphinx.quiz.controller;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.dto.RoundDto;
import com.lingosphinx.quiz.service.RoundService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/round")
@RequiredArgsConstructor
@Tag(name = "Round API")
public class RoundController {

    private final RoundService roundService;


    @PostMapping()
    public ResponseEntity<RoundDto> create(@RequestBody RoundDto round) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roundService.create(round));
    }
}