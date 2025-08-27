package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.mapper.QuizMapper;
import com.lingosphinx.quiz.repository.QuizRepository;
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
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final TopicRepository topicRepository;
    private final QuestionService questionService;
    private final QuizMapper quizMapper;

    @Override
    public QuizDto create(QuizDto quizDto) {
        var quiz = quizMapper.toEntity(quizDto);
        var savedQuiz = quizRepository.save(quiz);
        log.info("Quiz created successfully: id={}", savedQuiz.getId());
        return quizMapper.toDto(savedQuiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDto readById(Long id) {
        var quiz = quizRepository.findWithQuestionsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));
        log.info("Quiz read successfully: id={}", id);
        return quizMapper.toDto(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizDto> readAll() {
        var result = quizRepository.findAll().stream()
                .map(quizMapper::toDto)
                .toList();
        log.info("All quizzes read successfully, count={}", result.size());
        return result;
    }

    @Override
    public QuizDto update(Long id, QuizDto quizDto) {
        var existingQuiz = quizRepository.findWithQuestionsById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        this.quizMapper.updateEntityFromDto(quizDto, existingQuiz);

        var savedQuiz = quizRepository.save(existingQuiz);
        log.info("Quiz updated successfully: id={}", savedQuiz.getId());
        return quizMapper.toDto(savedQuiz);
    }

    @Override
    public void delete(Long id) {
        quizRepository.deleteById(id);
        log.info("Quiz deleted successfully: id={}", id);
    }
}