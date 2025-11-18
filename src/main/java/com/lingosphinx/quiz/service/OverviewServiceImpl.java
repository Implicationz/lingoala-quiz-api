package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.Overview;
import com.lingosphinx.quiz.domain.StudyList;
import com.lingosphinx.quiz.repository.QuestionRepository;
import com.lingosphinx.quiz.repository.QuestionSpecifications;
import com.lingosphinx.quiz.repository.TrialRepository;
import com.lingosphinx.quiz.repository.StudyListRepository;
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
    final private StudyListRepository studyListRepository;
    final private StudentService studentService;

    @Override
    public Overview readByStudyList(Long studyListId) {
        final var student = studentService.readCurrent();
        final var studyList = studyListRepository.findById(studyListId).orElseThrow();
        final var dueCount = trialRepository.count(TrialSpecifications.isDue(student, studyList));
        final var newCount = questionRepository.count(QuestionSpecifications.isNew(student, studyList));
        return Overview.builder()
                .dueCount(dueCount)
                .newCount(newCount)
                .build();
    }
}