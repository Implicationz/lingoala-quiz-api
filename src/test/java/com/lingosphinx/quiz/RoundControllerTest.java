package com.lingosphinx.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingosphinx.quiz.controller.RoundController;
import com.lingosphinx.quiz.domain.LanguageCode;
import com.lingosphinx.quiz.dto.QuizDto;
import com.lingosphinx.quiz.dto.RoundDto;
import com.lingosphinx.quiz.dto.TopicDto;
import com.lingosphinx.quiz.service.RoundService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RoundController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoundService roundService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createLearningRoundByQuiz_shouldReturnCreatedRound() throws Exception {
        QuizDto quiz = QuizDto.builder()
                .name("Test Quiz")
                .language(LanguageCode.ENGLISH)
                .userId("user-1")
                .source("Custom")
                .build();

        RoundDto roundDto = RoundDto.builder()
                .quiz(quiz)
                .language(LanguageCode.ENGLISH)
                .newCount(10)
                .dueCount(0)
                .build();

        Mockito.when(roundService.create(any(RoundDto.class))).thenReturn(roundDto);

        mockMvc.perform(post("/round/practice/language")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roundDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.language").value("english"));
    }

    @Test
    void createLearningRoundByLanguage_shouldReturnCreatedRound() throws Exception {
        RoundDto roundDto = RoundDto.builder()
                .language(LanguageCode.ENGLISH)
                .newCount(10)
                .dueCount(0)
                .build();

        Mockito.when(roundService.create(any(RoundDto.class))).thenReturn(roundDto);

        mockMvc.perform(post("/round/practice/language")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roundDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.language").value("english"));
    }

    @Test
    void createPracticeRoundByQuiz_shouldReturnCreatedRound() throws Exception {
        QuizDto quiz = QuizDto.builder()
                .name("Test Quiz")
                .language(LanguageCode.ENGLISH)
                .userId("user-1")
                .source("Custom")
                .build();

        RoundDto roundDto = RoundDto.builder()
                .quiz(quiz)
                .language(LanguageCode.ENGLISH)
                .newCount(0)
                .dueCount(10)
                .build();

        Mockito.when(roundService.create(any(RoundDto.class))).thenReturn(roundDto);

        mockMvc.perform(post("/round/practice/language")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roundDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.language").value("english"));
    }

    @Test
    void createPracticeRoundByLanguage_shouldReturnCreatedRound() throws Exception {
        RoundDto roundDto = RoundDto.builder()
                .language(LanguageCode.ENGLISH)
                .newCount(0)
                .dueCount(10)
                .build();

        Mockito.when(roundService.create(any(RoundDto.class))).thenReturn(roundDto);

        mockMvc.perform(post("/round/practice/language")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roundDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.language").value("english"));
    }
}