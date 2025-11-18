package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.dto.TopicDto;

import java.util.List;

public interface TopicService extends CrudService<TopicDto> {
    List<TopicDto> readAll(String name);
}
