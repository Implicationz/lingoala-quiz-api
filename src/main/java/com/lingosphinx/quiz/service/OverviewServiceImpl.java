package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Overview;
import com.lingosphinx.quiz.repository.QuestionRepository;
import com.lingosphinx.quiz.repository.QuestionSpecifications;
import com.lingosphinx.quiz.repository.TrialRepository;
import com.lingosphinx.quiz.repository.TrialSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OverviewServiceImpl implements OverviewService {

    final private QuestionRepository questionRepository;
    final private TrialRepository trialRepository;
    final private UserService userService;

    @Override
    public Overview readByLanguage(LanguageCode language) {
        final var userId = userService.getCurrentUserId();
        final var dueCount = trialRepository.count(TrialSpecifications.isDue(userId, language));
        final var newCount = questionRepository.count(QuestionSpecifications.isNew(userId, language));
        return Overview.builder()
                .dueCount(dueCount)
                .newCount(newCount)
                .build();
    }
}