package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.Overview;

public interface OverviewService {
    Overview readByStudyList(Long studyList);
}
