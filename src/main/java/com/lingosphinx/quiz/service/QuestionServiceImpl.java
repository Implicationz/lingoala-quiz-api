package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Answer;
import com.lingosphinx.quiz.dto.AnswerDto;
import com.lingosphinx.quiz.dto.QuestionDto;
import com.lingosphinx.quiz.mapper.QuestionMapper;
import com.lingosphinx.quiz.repository.QuestionRepository;
import com.lingosphinx.quiz.repository.QuizRepository;
import com.lingosphinx.quiz.utility.EntitySyncUtils;
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
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionDto create(QuestionDto questionDto) {
        var question = questionMapper.toEntity(questionDto);
        var saved = questionRepository.save(question);
        log.info("Question created successfully: id={}", saved.getId());
        return questionMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionDto readById(Long id) {
        var question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
        log.info("Question read successfully: id={}", id);
        return questionMapper.toDto(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> readAll() {
        var result = questionRepository.findAll().stream()
                .map(questionMapper::toDto)
                .toList();
        log.info("All questions read successfully, count={}", result.size());
        return result;
    }

    @Override
    public QuestionDto update(Long id, QuestionDto questionDto) {
        var existing = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        questionMapper.updateEntityFromDto(questionDto, existing);

        EntitySyncUtils.syncChildEntities(
            existing.getAnswers(),
            questionDto.getAnswers(),
            Answer::getId,
            AnswerDto::getId,
            questionMapper::toEntity,
            answer -> answer.setQuestion(existing),
            questionMapper::updateFromDto
        );

        var saved = questionRepository.save(existing);
        log.info("Question updated successfully: id={}", saved.getId());
        return questionMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);
        log.info("Question deleted successfully: id={}", id);
    }
}
