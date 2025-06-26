package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.TopicDto;

import java.util.List;

public interface TopicService {
    TopicDto create(TopicDto topic);
    TopicDto readById(String id);
    List<TopicDto> readAll();
    TopicDto update(String id, TopicDto topic);
    void delete(String id);
}
