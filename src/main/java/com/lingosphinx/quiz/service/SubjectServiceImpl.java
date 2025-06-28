package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Subject;
import com.lingosphinx.quiz.dto.SubjectDto;
import com.lingosphinx.quiz.mapper.SubjectMapper;
import com.lingosphinx.quiz.repository.QuizRepository;
import com.lingosphinx.quiz.repository.SubjectRepository;
import com.lingosphinx.quiz.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public SubjectDto create(SubjectDto subjectDto) {
        var subject = subjectMapper.toEntity(subjectDto);
        var savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDto(savedSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDto readById(String id) {
        var subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
        return subjectMapper.toDto(subject);
    }

    @Override
    @Cacheable("subjects")
    @Transactional(readOnly = true)
    public List<SubjectDto> readAll() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    @Override
    public SubjectDto update(String id, SubjectDto subjectDto) {
        var existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        var savedSubject = subjectRepository.save(existingSubject);
        return subjectMapper.toDto(savedSubject);
    }

    @Override
    public void delete(String id) {
        subjectRepository.deleteById(id);
    }
}