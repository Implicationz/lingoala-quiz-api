package com.lingosphinx.quiz;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.*;
import com.lingosphinx.quiz.service.*;
import org.junit.jupiter.api.AfterEach;
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
class RoundServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = QuizServiceTest.postgres;

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
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

    private QuizDto quiz;
    private TrialDto trial;

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
        quiz = quizService.create(QuizDto.builder()
                .language(LanguageCode.ENGLISH)
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
    }

    @AfterEach
    void cleanup() {
        if (trial != null) {
            trialService.delete(trial.getId());
        }
        if (quiz != null) {
            quizService.delete(quiz.getId());
        }
        if (quiz != null && quiz.getTopic() != null) {
            topicService.delete(quiz.getTopic().getId());
        }
        if (quiz != null && quiz.getTopic() != null && quiz.getTopic().getSubject() != null) {
            subjectService.delete(quiz.getTopic().getSubject().getId());
        }
    }

    @Test
    void createLearningRound_byQuiz_shouldReturnRoundWithTrials() {
        var roundDto = RoundDto.builder()
                .quiz(quiz)
                .language(quiz.getLanguage())
                .newCount(10)
                .dueCount(0)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertEquals("english", round.getLanguage());
        assertFalse(round.getTrials().isEmpty());
        assertEquals(quiz.getId(), round.getTrials().get(0).getQuestion().getQuiz().getId());
    }

    @Test
    void createLearningRound_byLanguage_shouldReturnRoundWithTrials() {
        var roundDto = RoundDto.builder()
                .language(LanguageCode.ENGLISH)
                .newCount(10)
                .dueCount(0)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertEquals("english", round.getLanguage());
        assertFalse(round.getTrials().isEmpty());
    }

    @Test
    void createPracticeRound_byQuiz_shouldReturnRoundWithTrials() {
        var trial = TrialDto.builder()
                .question(quiz.getQuestions().get(0))
                .userId("currentUserId")
                .nextDueDate(Instant.now())
                .build();
        this.trial = trialService.create(trial);

        var roundDto = RoundDto.builder()
                .quiz(quiz)
                .language(quiz.getLanguage())
                .newCount(0)
                .dueCount(10)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertEquals("english", round.getLanguage());
        assertFalse(round.getTrials().isEmpty());
        assertEquals(quiz.getId(), round.getTrials().get(0).getQuestion().getQuiz().getId());
    }

    @Test
    void createPracticeRound_byLanguage_shouldReturnRoundWithTrials() {
        var trial = TrialDto.builder()
                .question(quiz.getQuestions().get(0))
                .userId("currentUserId")
                .nextDueDate(Instant.now())
                .build();
        this.trial = trialService.create(trial);

        var roundDto = RoundDto.builder()
                .language(LanguageCode.ENGLISH)
                .newCount(0)
                .dueCount(10)
                .build();

        var round = roundService.create(roundDto);
        assertNotNull(round);
        assertEquals("english", round.getLanguage());
        assertFalse(round.getTrials().isEmpty());
        assertEquals(quiz.getId(), round.getTrials().get(0).getQuestion().getQuiz().getId());
    }
}