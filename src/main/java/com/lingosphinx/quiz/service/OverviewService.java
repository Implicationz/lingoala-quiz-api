package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Overview;
import com.lingosphinx.quiz.dto.AttemptDto;

public interface OverviewService {
    Overview readByLanguage(LanguageCode language);
}
