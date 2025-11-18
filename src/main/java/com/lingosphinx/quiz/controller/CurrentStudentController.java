package com.lingosphinx.quiz.controller;

import com.lingosphinx.quiz.dto.StudentDto;
import com.lingosphinx.quiz.dto.StudentRegistrationDto;
import com.lingosphinx.quiz.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me/student")
@RequiredArgsConstructor
@Tag(name = "CurrentStudent API")
public class CurrentStudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> register(@RequestBody StudentRegistrationDto studentRegistration) {
        return ResponseEntity.ok(studentService.register(studentRegistration));
    }

}