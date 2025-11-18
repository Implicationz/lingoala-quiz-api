package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Topic;
import com.lingosphinx.quiz.dto.SubjectDto;
import com.lingosphinx.quiz.dto.TopicDto;
import com.lingosphinx.quiz.mapper.SubjectMapper;
import com.lingosphinx.quiz.repository.SubjectRepository;
import com.lingosphinx.quiz.utility.EntitySyncUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
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
    public SubjectDto readById(Long id) {
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

    @Transactional(readOnly = true)
    @Override
    public List<SubjectDto> readAll(String name) {
        var subjects = Strings.isBlank(name) ? subjectRepository.findAll() : subjectRepository.findByNameStartingWithIgnoreCase(name);
        return subjects.stream().map(subjectMapper::toDto).toList();
    }

    @Override
    public SubjectDto update(Long id, SubjectDto subjectDto) {
        var existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));
        subjectMapper.updateEntityFromDto(subjectDto, existingSubject);
        EntitySyncUtils.syncChildEntities(existingSubject.getTopics(), subjectDto.getTopics(),
                Topic::getId,
                TopicDto::getId,
                subjectMapper::toEntity,
                item -> item.setSubject(existingSubject),
                (topicDto, topic) -> {
                    subjectMapper.updateEntityFromDto(topicDto, topic);
                    topic.setSubject(subjectRepository.getReferenceById(topicDto.getSubject().getId()));
                }
        );
        var savedSubject = subjectRepository.save(existingSubject);
        return subjectMapper.toDto(savedSubject);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}