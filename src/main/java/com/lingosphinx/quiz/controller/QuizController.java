package com.lingosphinx.quiz.controller;

import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.service.QuizService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Tag(name = "Quiz API")
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizDto> create(@RequestBody @Valid QuizDto quiz) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizService.create(quiz));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> readById(@PathVariable String id) {
        return ResponseEntity.ok(quizService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> readAll() {
        return ResponseEntity.ok(quizService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDto> update(@PathVariable String id, @RequestBody @Valid QuizDto quiz) {
        return ResponseEntity.ok(quizService.update(id, quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }
}