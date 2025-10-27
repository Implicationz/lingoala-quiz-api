package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.StudyListItemDto;
import com.lingosphinx.quiz.mapper.StudyListItemMapper;
import com.lingosphinx.quiz.repository.QuizRepository;
import com.lingosphinx.quiz.repository.StudyListItemRepository;
import com.lingosphinx.quiz.repository.StudyListRepository;
import com.lingosphinx.quiz.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudyListItemServiceImpl implements StudyListItemService {

    private final StudyListRepository studyListRepository;
    private final QuizRepository quizRepository;
    private final StudyListItemRepository studyListItemRepository;
    private final StudyListItemMapper studyListItemMapper;

    @Override
    public StudyListItemDto create(StudyListItemDto studyListItemDto) {
        var studyListItem = studyListItemMapper.toEntity(studyListItemDto);
        var savedStudyListItem = studyListItemRepository.save(studyListItem);
        log.info("StudyListItem created successfully: id={}", savedStudyListItem.getId());
        return studyListItemMapper.toDto(savedStudyListItem);
    }

    @Override
    @Transactional(readOnly = true)
    public StudyListItemDto readById(Long id) {
        var studyListItem = studyListItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudyListItem not found"));
        log.info("StudyListItem read successfully: id={}", id);
        return studyListItemMapper.toDto(studyListItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudyListItemDto> readAll() {
        var result = studyListItemRepository.findAll().stream()
                .map(studyListItemMapper::toDto)
                .toList();
        log.info("All studyListItemzes read successfully, count={}", result.size());
        return result;
    }

    @Override
    public StudyListItemDto update(Long id, StudyListItemDto studyListItemDto) {
        var existingStudyListItem = studyListItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudyListItem not found"));

        this.studyListItemMapper.updateEntityFromDto(studyListItemDto, existingStudyListItem);
        existingStudyListItem.setStudyList(studyListRepository.getReferenceById(studyListItemDto.getStudyList().getId()));
        existingStudyListItem.setQuiz(quizRepository.getReferenceById(studyListItemDto.getQuiz().getId()));

        var savedStudyListItem = studyListItemRepository.save(existingStudyListItem);
        log.info("StudyListItem updated successfully: id={}", savedStudyListItem.getId());
        return studyListItemMapper.toDto(savedStudyListItem);
    }

    @Override
    public void delete(Long id) {
        studyListItemRepository.deleteById(id);
        log.info("StudyListItem deleted successfully: id={}", id);
    }
}