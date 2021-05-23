package com.university.itis.controller;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;
import com.university.itis.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/quiz/{quizId}/question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    ResponseEntity getAll(@PathVariable Long quizId) {
        return questionService.getAllByQuizId(quizId).getResponseEntity();
    }

    @PostMapping
    ResponseEntity createQuestion(
            ServletRequest request,
            @PathVariable Long quizId,
            @RequestBody QuestionDto questionDto
    ) {
        User user = (User) request.getAttribute("user");
        return questionService.save(quizId, questionDto, user).getResponseEntity();
    }

    @PutMapping(value = "/{questionId}")
    ResponseEntity updateQuestion(
            ServletRequest request,
            @RequestBody QuestionDto questionDto,
            @PathVariable Long quizId,
            @PathVariable Long questionId
    ) {
        User user = (User) request.getAttribute("user");
        return questionService.update(quizId, questionId, questionDto, user).getResponseEntity();
    }

    @GetMapping(value = "/{questionId}")
    ResponseEntity getQuestionById(@PathVariable Long quizId, @PathVariable Long questionId) {
        return questionService.getById(quizId, questionId).getResponseEntity();
    }

    @DeleteMapping(value = "/{questionId}")
    ResponseEntity deleteQuestion(ServletRequest request, @PathVariable Long quizId, @PathVariable Long questionId) {
        User user = (User) request.getAttribute("user");
        return questionService.delete(quizId, questionId, user).getResponseEntity();
    }
}
