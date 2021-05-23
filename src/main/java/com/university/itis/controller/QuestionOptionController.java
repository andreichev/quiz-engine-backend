package com.university.itis.controller;

import com.university.itis.dto.QuestionOptionDto;
import com.university.itis.model.User;
import com.university.itis.services.QuestionOptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/question/{questionId}/option")
@AllArgsConstructor
public class QuestionOptionController {
    private final QuestionOptionService questionOptionService;

    @GetMapping
    ResponseEntity getAllByQuestionId(@PathVariable Long questionId) {
        return questionOptionService.getAllByQuestionId(questionId).getResponseEntity();
    }

    @PostMapping
    ResponseEntity createQuestionOption(
            ServletRequest request,
            @PathVariable Long questionId,
            @RequestBody QuestionOptionDto questionOptionDto
    ) {
        User user = (User) request.getAttribute("user");
        return questionOptionService.save(questionId, questionOptionDto, user).getResponseEntity();
    }

    @PutMapping(value = "/{questionOptionId}")
    ResponseEntity updateQuestionOption(
            ServletRequest request,
            @RequestBody QuestionOptionDto questionDto,
            @PathVariable Long questionId,
            @PathVariable Long questionOptionId
    ) {
        User user = (User) request.getAttribute("user");
        return questionOptionService.update(questionId, questionOptionId, questionDto, user)
                .getResponseEntity();
    }

    @GetMapping(value = "/{questionOptionId}")
    ResponseEntity getQuestionOptionById(@PathVariable Long questionId, @PathVariable Long questionOptionId) {
        return questionOptionService.getById(questionId, questionOptionId).getResponseEntity();
    }

    @DeleteMapping(value = "/{questionOptionId}")
    ResponseEntity deleteQuestionOption(ServletRequest request, @PathVariable Long questionId, @PathVariable Long questionOptionId) {
        User user = (User) request.getAttribute("user");
        return questionOptionService.delete(questionId, questionOptionId, user).getResponseEntity();
    }
}
