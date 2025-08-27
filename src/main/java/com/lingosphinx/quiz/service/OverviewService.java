package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Overview;

public interface OverviewService {
    Overview readByLanguage(LanguageCode language);
}
