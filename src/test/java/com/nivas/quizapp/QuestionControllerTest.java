package com.nivas.quizapp;


import com.nivas.quizapp.controller.QuestionController;
import com.nivas.quizapp.model.Question;
import com.nivas.quizapp.service.QuestionService;
import com.nivas.quizapp.service.QuizService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Test
    public void ContextLoads() {
        assertThat(questionService).isNotNull();
    }

    @Test
    public void testGetQuestionsByCategory() throws Exception {
        Question question =new Question();
        question.setId(1);
        question.setQuestionTitle("What is gravity?");
        question.setOption1("A force");
        question.setOption2("An illusion");
        question.setOption3("A magic");
        question.setOption4("Unknown");
        question.setAnswer("A force");
        question.setDifficultLevel("Medium");
        question.setCategory("Java");
        List<Question> mockQuestions = Arrays.asList(question);
        ResponseEntity<List<Question>> mockResponse=ResponseEntity.ok(mockQuestions);

        when(questionService.getQuestionsByCategory("Java")).thenReturn(mockResponse);
        mockMvc.perform(get("/question/category/Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Java"))
                .andExpect(jsonPath("$[0].questionTitle").value("What is gravity?"))
                .andExpect(jsonPath("$[0].option1").value("A force"))
                .andExpect(jsonPath("$[0].answer").value("A force"));;
    }

    @Test
    public void testGetAllQuestion() throws Exception {
        Question question1 = new Question();
        question1.setId(1);
        question1.setQuestionTitle("What is gravity?");
        question1.setOption1("A force");
        question1.setOption2("An illusion");
        question1.setOption3("A magic");
        question1.setOption4("Unknown");
        question1.setAnswer("A force");
        question1.setDifficultLevel("Medium");
        question1.setCategory("Science");

        Question question2 = new Question();
        question2.setId(2);
        question2.setQuestionTitle("What is 2 + 2?");
        question2.setOption1("3");
        question2.setOption2("4");
        question2.setOption3("5");
        question2.setOption4("6");
        question2.setAnswer("4");
        question2.setDifficultLevel("Easy");
        question2.setCategory("Math");

        List<Question> mockQuestions=Arrays.asList(question1,question2);
        ResponseEntity<List<Question>> mockResponse=ResponseEntity.ok(mockQuestions);

        when(questionService.getAllQuestions()).thenReturn(mockResponse);

        mockMvc.perform(get("/question/allquestions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Science"))
                .andExpect(jsonPath("$[0].questionTitle").value("What is gravity?"))
                .andExpect(jsonPath("$[1].category").value("Math"))
                .andExpect(jsonPath("$[1].questionTitle").value("What is 2 + 2?"));
    }

    @Test
    public void testAddQuestion() throws Exception {
        Question newQuestion = new Question();
        newQuestion.setId(3);
        newQuestion.setQuestionTitle("Who was the first president of the US?");
        newQuestion.setOption1("George Washington");
        newQuestion.setOption2("Thomas Jefferson");
        newQuestion.setOption3("Abraham Lincoln");
        newQuestion.setOption4("John Adams");
        newQuestion.setAnswer("George Washington");
        newQuestion.setDifficultLevel("Easy");
        newQuestion.setCategory("History");

        ResponseEntity<String> mockResponse=ResponseEntity.ok("Question added successfully!");
        when(questionService.addQuestion(any(Question.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/question/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"questionTitle\": \"Who was the first president of the US?\", " +
                                "\"option1\": \"George Washington\", \"option2\": \"Thomas Jefferson\", " +
                                "\"option3\": \"Abraham Lincoln\", \"option4\": \"John Adams\", " +
                                "\"answer\": \"George Washington\", \"difficultLevel\": \"Easy\", \"category\": \"History\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Question added successfully!"));
    }
}
