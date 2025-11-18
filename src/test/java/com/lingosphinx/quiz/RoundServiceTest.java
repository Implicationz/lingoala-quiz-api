package com.lingosphinx.quiz;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.domain.StudyList;
import com.lingosphinx.quiz.domain.StudyStatus;
import com.lingosphinx.quiz.dto.*;
import com.lingosphinx.quiz.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoundServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = QuizServiceTest.postgres;

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("GAMIFICATION_CLIENT_URL", () -> "localhost");
    }

    @Autowired
    private RoundService roundService;

    @Autowired
    private TrialService trialService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudyListService studyListService;

    @Autowired
    private StudyListItemService studyListItemService;

    private StudentDto student;
    private StudyListDto studyList;
    private QuizDto quiz;
    private TrialDto trial;

    void registerStudent() {
        studentService.register(StudentRegistrationDto.builder().build());
        var read = studentService.readCurrent();
        student = StudentDto.builder().id(read.getId()).build();
        studyList = studyListService.activate(LanguageCode.ENGLISH);
    }

    void setupStudyListItem(QuizDto quiz) {
        var studyListItem = StudyListItemDto.builder()
                .dueQuestions(StudyStatus.ENABLED)
                .newQuestions(StudyStatus.ENABLED)
                .studyList(studyList)
                .quiz(quiz)
                .build();
        studyListItemService.create(studyListItem);
    }

    void setupQuiz() {
        quiz = quizService.create(QuizDto.builder()
                .language(LanguageCode.ENGLISH)
                .name("Test Quiz")
                .owner(student)
                .source("Custom")
                .build());

        var question1 = QuestionDto.builder()
                .text("What is the capital of England?")
                .difficulty(1)
                .quiz(quiz)
                .explanation(ExplanationDto.builder()
                        .text("London ist die Hauptstadt von England.")
                        .translation("London is the capital of England.")
                        .transcription("ˈlʌndən")
                        .build())
                .answers(List.of(
                        AnswerDto.builder().text("London").correct(true).build(),
                        AnswerDto.builder().text("Paris").correct(false).build()
                ))
                .build();

        var question2 = QuestionDto.builder()
                .text("What is the capital of France?")
                .difficulty(1)
                .quiz(quiz)
                .explanation(ExplanationDto.builder()
                        .text("Paris ist die Hauptstadt von Frankreich.")
                        .translation("Paris is the capital of France.")
                        .transcription("ˈlʌndən")
                        .build())
                .answers(List.of(
                        AnswerDto.builder().text("London").correct(false).build(),
                        AnswerDto.builder().text("Paris").correct(true).build()
                ))
                .build();

        quiz.setQuestions(List.of(question1, question2));
        quiz = quizService.create(quiz);

        var trial = TrialDto.builder()
                .question(quiz.getQuestions().get(0))
                .student(student)
                .nextDueDate(Instant.now())
                .build();
        this.trial = trialService.create(trial);
    }

    @BeforeAll
    void setup() {
        registerStudent();
        setupQuiz();
        setupStudyListItem(quiz);
    }

    @Test
    void createLearningRound_byQuiz_shouldReturnRoundWithTrials() {
        var roundDto = RoundDto.builder()
                .quiz(quiz)
                .newCount(10)
                .dueCount(0)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertFalse(round.getTrials().isEmpty());
    }

    @Test
    void createLearningRound_byLanguage_shouldReturnRoundWithTrials() {
        var roundDto = RoundDto.builder()
                .studyList(studyList)
                .newCount(10)
                .dueCount(0)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertEquals(studyList.getId(), round.getStudyList().getId());
        assertFalse(round.getTrials().isEmpty());
    }

    @Test
    void createPracticeRound_byQuiz_shouldReturnRoundWithTrials() {
        var roundDto = RoundDto.builder()
                .quiz(quiz)
                .newCount(0)
                .dueCount(10)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertFalse(round.getTrials().isEmpty());
    }

    @Test
    void createPracticeRound_byLanguage_shouldReturnRoundWithTrials() {
        var roundDto = RoundDto.builder()
                .studyList(studyList)
                .newCount(0)
                .dueCount(10)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertEquals(studyList.getId(), round.getStudyList().getId());
        assertFalse(round.getTrials().isEmpty());
    }
}