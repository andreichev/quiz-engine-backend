package com.university.itis.controller;

import com.university.itis.dto.QuestionOptionDto;
import com.university.itis.model.User;
import com.university.itis.services.QuestionOptionService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.ResponseCreator;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/question/{questionId}/option")
@AllArgsConstructor
public class QuestionOptionController extends ResponseCreator {
    private final Validator validator;
    private final QuestionOptionService questionOptionService;

    @GetMapping
    ResponseEntity getAllByQuestionId(@PathVariable Long questionId) {
        return createGoodResponse(questionOptionService.getAllByQuestionId(questionId));
    }

    @PostMapping
    ResponseEntity createQuestionOption(
            ServletRequest request,
            @PathVariable Long questionId,
            @RequestBody QuestionOptionDto questionOptionDto
    ) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuestionOptionFormError(questionOptionDto);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = (User) request.getAttribute("user");
        return createGoodResponse(questionOptionService.save(questionId, questionOptionDto, user));
    }

    @PutMapping(value = "/{questionOptionId}")
    ResponseEntity updateQuestionOption(
            ServletRequest request,
            @RequestBody QuestionOptionDto questionDto,
            @PathVariable Long questionId,
            @PathVariable Long questionOptionId
    ) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuestionOptionFormError(questionDto);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = (User) request.getAttribute("user");
        return createGoodResponse(questionOptionService.update(questionId, questionOptionId, questionDto, user));
    }

    @GetMapping(value = "/{questionOptionId}")
    ResponseEntity getQuestionOptionById(@PathVariable Long questionId, @PathVariable Long questionOptionId) {
        return createGoodResponse(questionOptionService.getById(questionId, questionOptionId));
    }

    @DeleteMapping(value = "/{questionId}")
    ResponseEntity deleteQuestionOption(ServletRequest request, @PathVariable Long questionId, @PathVariable Long questionOptionId) {
        User user = (User) request.getAttribute("user");
        questionOptionService.delete(questionId, questionOptionId, user);
        return createGoodResponse();
    }
}
