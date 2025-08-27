package com.lingosphinx.quiz.controller;
import com.lingosphinx.quiz.dto.StudyListDto;
import com.lingosphinx.quiz.service.StudyListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study-list")
@RequiredArgsConstructor
@Tag(name = "StudyList API")
public class StudyListController {

    private final StudyListService studyListService;

    @PostMapping
    public ResponseEntity<StudyListDto> create(@RequestBody @Valid StudyListDto studyList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyListService.create(studyList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyListDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(studyListService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudyListDto>> readAll() {
        return ResponseEntity.ok(studyListService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyListDto> update(@PathVariable Long id, @RequestBody @Valid StudyListDto studyList) {
        return ResponseEntity.ok(studyListService.update(id, studyList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studyListService.delete(id);
        return ResponseEntity.noContent().build();
    }
}