package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Overview;
import com.lingosphinx.quiz.domain.StudyList;

public interface OverviewService {
    Overview readByStudyList(Long studyList);
}
