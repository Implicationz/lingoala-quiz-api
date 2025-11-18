package com.lingosphinx.quiz;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.*;
import com.lingosphinx.quiz.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AttemptServiceTest {

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
    private AttemptService attemptService;

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

    private StudentDto student;
    private TrialDto trial;
    private AnswerDto correctAnswer;

    @BeforeAll
    void registerStudent() {
        studentService.register(StudentRegistrationDto.builder().build());
        var read = studentService.readCurrent();
        student = StudentDto.builder().id(read.getId()).build();
    }

    @BeforeEach
    void setup() {
        var subject = subjectService.create(SubjectDto.builder()
                .name("English")
                .build());
        var topic = topicService.create(TopicDto.builder()
                .name("Literature")
                .subject(subject)
                .build());
        var quiz = quizService.create(QuizDto.builder()
                .language(LanguageCode.ENGLISH)
                .name("Test Quiz")
                .owner(student)
                .source("Custom")
                .build());

        var question = QuestionDto.builder()
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

        quiz.setQuestions(List.of(question));
        quiz = quizService.create(quiz);


        trial = trialService.create(TrialDto.builder()
                .question(quiz.getQuestions().get(0))
                .student(student)
                .nextDueDate(Instant.now())
                .build());

        correctAnswer = quiz.getQuestions().get(0).getAnswers().stream()
                .filter(AnswerDto::isCorrect)
                .findFirst()
                .orElseThrow();
    }

    @Test
    void createAttempt_shouldRescheduleTrial() {
        var before = trialService.readById(trial.getId());
        var beforeDue = before.getNextDueDate();
        var beforeLastAttempted = before.getLastAttemptedAt();

        var attemptDto = AttemptDto.builder()
                .trial(trial)
                .answer(correctAnswer)
                .build();

        var createdAttempt = attemptService.create(attemptDto);
        assertNotNull(createdAttempt);

        var after = trialService.readById(trial.getId());
        assertNotNull(after.getLastAttemptedAt());
        assertNotNull(after.getNextDueDate());

        assertTrue(after.getLastAttemptedAt().isAfter(beforeLastAttempted));
        assertTrue(after.getNextDueDate().isAfter(beforeDue));
    }
}