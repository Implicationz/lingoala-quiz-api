package com.lingosphinx.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingosphinx.quiz.controller.QuizController;
import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.dto.TopicDto;
import com.lingosphinx.quiz.service.QuizService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = QuizController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createQuiz_shouldReturnCreatedQuiz() throws Exception {
        QuizDto quiz = QuizDto.builder()
                .name("Test Quiz")
                .language(LanguageCode.ENGLISH)
                .userId("user-1")
                .source("Custom")
                .build();

        Mockito.when(quizService.create(any(QuizDto.class))).thenReturn(quiz);

        mockMvc.perform(post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quiz)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Quiz"));
    }

    @Test
    void readById_shouldReturnQuiz() throws Exception {
        QuizDto quiz = QuizDto.builder()
                .name("Test Quiz")
                .language(LanguageCode.ENGLISH)
                .userId("user-1")
                .source("Custom")
                .build();

        Mockito.when(quizService.readById(1L)).thenReturn(quiz);

        mockMvc.perform(get("/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Test Quiz"));
    }
}