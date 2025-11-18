package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.TopicDto;
import com.lingosphinx.quiz.mapper.TopicMapper;
import com.lingosphinx.quiz.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicServiceImpl implements TopicService {

    private final SubjectService subjectService;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Override
    public TopicDto create(TopicDto topicDto) {
        var topic = topicMapper.toEntity(topicDto);
        var savedTopic = topicRepository.save(topic);
        return topicMapper.toDto(savedTopic);
    }

    @Override
    @Transactional(readOnly = true)
    public TopicDto readById(Long id) {
        var topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));
        return topicMapper.toDto(topic);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopicDto> readAll() {
        return topicRepository.findAll().stream()
                .map(topicMapper::toDto)
                .toList();
    }

    @Override
    public TopicDto update(Long id, TopicDto topicDto) {
        var existingTopic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));

        var savedTopic = topicRepository.save(existingTopic);
        return topicMapper.toDto(savedTopic);
    }

    @Override
    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public List<TopicDto> readAll(String name) {
        return List.of();
    }
}