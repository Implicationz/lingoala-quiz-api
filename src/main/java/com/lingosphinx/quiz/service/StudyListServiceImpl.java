package com.lingosphinx.quiz.service;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.StudyList;
import com.lingosphinx.quiz.domain.StudyListItem;
import com.lingosphinx.quiz.dto.StudyListDto;
import com.lingosphinx.quiz.dto.StudyListItemDto;
import com.lingosphinx.quiz.dto.StudyListOverview;
import com.lingosphinx.quiz.dto.StudyListOverviewItem;
import com.lingosphinx.quiz.mapper.StudyListMapper;
import com.lingosphinx.quiz.repository.QuestionRepository;
import com.lingosphinx.quiz.repository.QuizRepository;
import com.lingosphinx.quiz.repository.StudyListRepository;
import com.lingosphinx.quiz.repository.TrialRepository;
import com.lingosphinx.quiz.utility.EntitySyncUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyListServiceImpl implements StudyListService {

    private final UserService userService;
    private final StudentService studentService;
    private final StudyListRepository studyListRepository;
    private final StudyListMapper studyListMapper;
    private final QuizRepository quizRepository;

    private final TrialRepository trialRepository;
    private final QuestionRepository questionRepository;

    @Override
    public StudyListDto create(StudyListDto studyListDto) {
        var studyList = studyListMapper.toEntity(studyListDto);
        var savedStudyList = studyListRepository.save(studyList);
        return studyListMapper.toDto(savedStudyList);
    }

    @Override
    @Transactional(readOnly = true)
    public StudyListDto readById(Long id) {
        var studyList = studyListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudyList not found"));
        return studyListMapper.toDto(studyList);
    }

    @Override
    @Cacheable("studyLists")
    @Transactional(readOnly = true)
    public List<StudyListDto> readAll() {
        return studyListRepository.findAll().stream()
                .map(studyListMapper::toDto)
                .toList();
    }

    @Override
    public StudyListDto update(Long id, StudyListDto studyListDto) {
        var existingStudyList = studyListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudyList not found"));
        EntitySyncUtils.syncChildEntities(existingStudyList.getItems(), studyListDto.getItems(),
                StudyListItem::getId,
                StudyListItemDto::getId,
                studyListMapper::toEntity,
                item -> item.setStudyList(existingStudyList),
                (studyListItemDto, studyListItem) -> {
                    studyListMapper.updateEntityFromDto(studyListItemDto, studyListItem);
                    studyListItem.setStudyList(studyListRepository.getReferenceById(studyListItemDto.getStudyList().getId()));
                    studyListItem.setQuiz(quizRepository.getReferenceById(studyListItemDto.getQuiz().getId()));
                }
        );
        var savedStudyList = studyListRepository.save(existingStudyList);
        studyListRepository.flush();
        return studyListMapper.toDto(savedStudyList);
    }

    @Override
    public void delete(Long id) {
        studyListRepository.deleteById(id);
    }

    @Override
    public StudyListDto activate(LanguageCode language) {
        var userId = userService.getCurrentUserId();
        var student = studentService.readCurrent();
        var studyList = studyListRepository.findByStudentAndLanguage(student, language).orElseGet(() -> {
                            var newStudyList = StudyList.builder()
                                    .student(student)
                                    .language(language)
                                    .build();
                            return studyListRepository.save(newStudyList);
                        });

        return studyListMapper.toDto(studyList);
    }

    public StudyListOverview getOverview(UUID userId, LanguageCode language) {
        var student = studentService.readCurrent();
        var studyList = studyListRepository.findByStudentAndLanguage(student, language)
                .orElseThrow(() -> new EntityNotFoundException("StudyList not found."));
        var quizIds = studyList.getItems().stream()
                .map(item -> item.getQuiz().getId())
                .toList();

        var newCounts = questionRepository.countNewQuestionsByQuizIds(student, quizIds);
        var dueCounts = trialRepository.countDueTrialsByQuizIds(student, quizIds, Instant.now());

        var newMap = newCounts.stream()
                .collect(Collectors.toMap(row -> (Long) row[0], row -> (Long) row[1]));
        var dueMap = dueCounts.stream()
                .collect(Collectors.toMap(row -> (Long) row[0], row -> (Long) row[1]));

        var items = studyList.getItems().stream()
                .map(item -> new StudyListOverviewItem(
                        item.getQuiz().getId(),
                        newMap.getOrDefault(item.getQuiz().getId(), 0L),
                        dueMap.getOrDefault(item.getQuiz().getId(), 0L)
                ))
                .toList();

        long totalNew = newMap.values().stream().mapToLong(Long::longValue).sum();
        long totalDue = dueMap.values().stream().mapToLong(Long::longValue).sum();

        return StudyListOverview.builder()
                .newCount(totalNew)
                .dueCount(totalDue)
                .items(items)
                .build();
    }
}