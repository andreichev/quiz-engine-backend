package com.university.itis.controller;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;
import com.university.itis.services.QuestionService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.ResponseCreator;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/quiz/{quizId}")
@AllArgsConstructor
public class QuestionController extends ResponseCreator {
    private final Validator validator;
    private final QuestionService questionService;

    @PostMapping
    ResponseEntity createQuestion(
            ServletRequest request,
            @PathVariable Long quizId,
            @RequestBody QuestionDto questionDto
    ) {
        questionDto.setQuizId(quizId);
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuestionFormError(questionDto);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = (User) request.getAttribute("user");
        return createGoodResponse(questionService.saveQuestion(questionDto, user));
    }
}
