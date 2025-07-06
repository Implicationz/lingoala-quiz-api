// Datei: src/test/java/com/lingosphinx/quiz/AttemptServiceTest.java
package com.lingosphinx.quiz;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.*;
import com.lingosphinx.quiz.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class AttemptServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = QuizServiceTest.postgres;

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
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

    private TrialDto trial;
    private AnswerDto correctAnswer;

    @BeforeEach
    void setup() {
        var subject = subjectService.create(SubjectDto.builder()
                .name("English")
                .language(LanguageCode.ENGLISH)
                .build());
        var topic = topicService.create(TopicDto.builder()
                .name("Literature")
                .subject(subject)
                .build());
        var quiz = quizService.create(QuizDto.builder()
                .language(LanguageCode.ENGLISH)
                .topic(topic)
                .name("Test Quiz")
                .userId("user-1")
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
                        AnswerDto.builder().text("London").isCorrect(true).build(),
                        AnswerDto.builder().text("Paris").isCorrect(false).build()
                ))
                .build();

        quiz.setQuestions(List.of(question));
        quiz = quizService.create(quiz);

        trial = trialService.create(TrialDto.builder()
                .question(quiz.getQuestions().get(0))
                .userId("test-user")
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