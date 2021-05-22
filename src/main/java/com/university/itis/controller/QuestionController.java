package com.university.itis.controller;

import com.university.itis.dto.QuestionDto;
import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.model.User;
import com.university.itis.services.QuestionService;
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
@RequestMapping("/quiz/{quizId}/question")
@AllArgsConstructor
public class QuestionController extends ResponseCreator {
    private final Validator validator;
    private final QuestionService questionService;

    @GetMapping
    ResponseEntity getAll(@PathVariable Long quizId) {
        return createGoodResponse(questionService.getAllByQuizId(quizId));
    }

    @PostMapping
    ResponseEntity createQuestion(
            ServletRequest request,
            @PathVariable Long quizId,
            @RequestBody QuestionDto questionDto
    ) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuestionFormError(questionDto);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = (User) request.getAttribute("user");
        return createGoodResponse(questionService.saveQuestion(quizId, questionDto, user));
    }

    @PutMapping(value = "/{questionId}")
    ResponseEntity updateQuestion(
            ServletRequest request,
            @RequestBody QuestionDto questionDto,
            @PathVariable Long quizId,
            @PathVariable Long questionId
    ) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuestionFormError(questionDto);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = (User) request.getAttribute("user");
        return createGoodResponse(questionService.updateQuestion(quizId, questionId, questionDto, user));
    }

    @GetMapping(value = "/{questionId}")
    ResponseEntity getQuestionById(@PathVariable Long quizId, @PathVariable Long questionId) {
        return createGoodResponse(questionService.getById(quizId, questionId));
    }

    @DeleteMapping(value = "/{questionId}")
    ResponseEntity deleteQuestion(ServletRequest request, @PathVariable Long quizId, @PathVariable Long questionId) {
        User user = (User) request.getAttribute("user");
        questionService.delete(quizId, questionId, user);
        return createGoodResponse();
    }
}
