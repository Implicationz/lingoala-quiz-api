package com.lingosphinx.quiz;

import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.*;
import com.lingosphinx.quiz.service.QuizService;
import com.lingosphinx.quiz.service.SubjectService;
import com.lingosphinx.quiz.service.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class QuizServiceTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private QuizService quizService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TopicService topicService;

    private SubjectDto createSubject(String name) {
        return subjectService.create(SubjectDto.builder()
                .name(name)
                .language(LanguageCode.ENGLISH)
                .build());
    }

    private TopicDto createTopic(SubjectDto subject) {
        return topicService.create(TopicDto.builder()
                .name(subject.getName())
                .subject(subject)
                .build());
    }

    private QuizDto baseQuiz(String name, String userId, TopicDto topic) {
        return QuizDto.builder()
                .language(LanguageCode.ENGLISH)
                .name(name)
                .userId(userId)
                .source("Custom")
                .build();
    }

    private QuestionDto createSampleQuestion(QuizDto quiz) {
        var explanation = ExplanationDto.builder()
                .text("London ist die Hauptstadt von England.")
                .translation("London is the capital of England.")
                .transcription("ˈlʌndən")
                .build();

        var question = QuestionDto.builder()
                .text("What is the capital of England?")
                .difficulty(1)
                .quiz(quiz)
                .explanation(explanation)
                .build();

        var answer1 = AnswerDto.builder()
                .text("London")
                .correct(true)
                .question(question)
                .build();

        var answer2 = AnswerDto.builder()
                .text("Paris")
                .correct(false)
                .question(question)
                .build();

        question.setAnswers(List.of(answer1, answer2));
        return question;
    }

    private QuizDto createQuiz(String name, String userId) {
        var subject = createSubject(name);
        var topic = createTopic(subject);
        var quiz = baseQuiz(name, userId, topic);
        return quizService.create(quiz);
    }

    @Test
    void createQuiz_shouldPersistQuiz() {
        var subject = createSubject("My Subject");
        var topic = createTopic(subject);
        var quiz = baseQuiz("My Quiz", "user-123", topic);
        var question = createSampleQuestion(quiz);

        quiz.setQuestions(List.of(question));
        var savedQuiz = quizService.create(quiz);

        assertNotNull(savedQuiz.getId());
        assertEquals(1, savedQuiz.getQuestions().size());
        var savedQuestion = savedQuiz.getQuestions().get(0);
        assertEquals("What is the capital of England?", savedQuestion.getText());
        assertNotNull(savedQuestion.getExplanation());
        assertEquals("London ist die Hauptstadt von England.", savedQuestion.getExplanation().getText());
        assertEquals(2, savedQuestion.getAnswers().size());
        assertTrue(savedQuestion.getAnswers().stream().anyMatch(a -> a.getText().equals("London") && a.isCorrect()));
        assertTrue(savedQuestion.getAnswers().stream().anyMatch(a -> a.getText().equals("Paris") && !a.isCorrect()));
    }

    @Test
    void readById_shouldReturnPersistedQuiz() {
        var quiz = createQuiz("Test Quiz", "user-1");
        var found = quizService.readById(quiz.getId());
        assertNotNull(found);
        assertEquals("Test Quiz", found.getName());
    }

    @Test
    void readAll_shouldReturnAllQuizzes() {
        createQuiz("Quiz 1", "user-1");
        createQuiz("Quiz 2", "user-2");

        var all = quizService.readAll();
        assertTrue(all.size() >= 2);
        assertTrue(all.stream().anyMatch(q -> "Quiz 1".equals(q.getName())));
        assertTrue(all.stream().anyMatch(q -> "Quiz 2".equals(q.getName())));
    }

    @Test
    void delete_shouldRemoveQuiz() {
        var quiz = createQuiz("To Delete", "user-1");
        quizService.delete(quiz.getId());
        assertThrows(Exception.class, () -> quizService.readById(quiz.getId()));
    }
}
