package com.lingosphinx.quiz.service;


import com.lingosphinx.quiz.domain.Student;
import com.lingosphinx.quiz.dto.StudentDto;
import com.lingosphinx.quiz.dto.StudentRegistrationDto;
import com.lingosphinx.quiz.exception.ResourceNotFoundException;
import com.lingosphinx.quiz.mapper.StudentMapper;
import com.lingosphinx.quiz.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final UserService userService;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDto create(StudentDto studentDto) {
        var student = studentMapper.toEntity(studentDto);
        var savedStudent = studentRepository.save(student);
        log.info("Student created with id: {}", savedStudent.getId());
        return studentMapper.toDto(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto readById(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        log.info("Student found with id: {}", id);
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> readAll() {
        log.info("Reading all students");
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDto) {
        var existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentMapper.updateEntityFromDto(studentDto, existingStudent);
        studentRepository.flush();
        log.info("Student updated with id: {}", id);
        return studentMapper.toDto(existingStudent);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
        log.info("Student deleted with id: {}", id);
    }

    @Override
    public StudentDto register(StudentRegistrationDto studentRegistration) {
        var userId = userService.getCurrentUserId();
        var registeredStudent = studentRepository
                .findByUserId(userId)
                .orElseGet(() -> {
                    var student = Student.builder()
                                .userId(userId)
                                .name(studentRegistration.getName())
                                .accessLevel(studentRegistration.getAccessLevel())
                                .build();
                    var savedStudent = studentRepository.save(student);
                    log.info("Student registered with id: {}", savedStudent.getId());
                    return savedStudent;
                });

        return this.studentMapper.toDto(registeredStudent);
    }

    @Override
    public Student readCurrent() {
        var userId = userService.getCurrentUserId();
        return studentRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for user id: " + userId));
    }
}