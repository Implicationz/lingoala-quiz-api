package com.lingosphinx.quiz.controller;
import com.lingosphinx.quiz.dto.SubjectDto;
import com.lingosphinx.quiz.service.SubjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@Tag(name = "Subject API")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDto> create(@RequestBody @Valid SubjectDto subject) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create(subject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> readById(@PathVariable String id) {
        return ResponseEntity.ok(subjectService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectDto>> readAll() {
        return ResponseEntity.ok(subjectService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDto> update(@PathVariable String id, @RequestBody @Valid SubjectDto subject) {
        return ResponseEntity.ok(subjectService.update(id, subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}