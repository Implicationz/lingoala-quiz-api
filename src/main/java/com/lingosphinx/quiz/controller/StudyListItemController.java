package com.lingosphinx.quiz.controller;
import com.lingosphinx.quiz.dto.StudyListItemDto;
import com.lingosphinx.quiz.service.StudyListItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study-list-item")
@RequiredArgsConstructor
@Tag(name = "StudyListItem API")
public class StudyListItemController {

    private final StudyListItemService studyListItemService;

    @PostMapping
    public ResponseEntity<StudyListItemDto> create(@RequestBody @Valid StudyListItemDto studyListItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyListItemService.create(studyListItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyListItemDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(studyListItemService.readById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudyListItemDto>> readAll() {
        return ResponseEntity.ok(studyListItemService.readAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyListItemDto> update(@PathVariable Long id, @RequestBody @Valid StudyListItemDto studyListItem) {
        return ResponseEntity.ok(studyListItemService.update(id, studyListItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studyListItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}