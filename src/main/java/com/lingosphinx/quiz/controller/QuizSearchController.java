package com.lingosphinx.quiz.controller;


import com.lingosphinx.quiz.dto.QuizSearchDto;
import com.lingosphinx.quiz.service.QuizSearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz-search")
@RequiredArgsConstructor
@Tag(name = "QuizSearch API")
public class QuizSearchController {

    private final QuizSearchService quizSearchService;

    @PostMapping
    public ResponseEntity<QuizSearchDto> search(@RequestBody @Valid QuizSearchDto quizSearch) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizSearchService.create(quizSearch));
    }

}