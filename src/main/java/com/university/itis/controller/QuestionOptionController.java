package com.university.itis.controller;

import com.university.itis.dto.question_option.QuestionOptionDto;
import com.university.itis.model.User;
import com.university.itis.services.QuestionOptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
@RequestMapping("/question/{questionId}/option")
@AllArgsConstructor
public class QuestionOptionController {
    private final QuestionOptionService questionOptionService;

    @GetMapping
    List<QuestionOptionDto> getAllByQuestionId(@PathVariable Long questionId) {
        return questionOptionService.getAllByQuestionId(questionId);
    }

    @PostMapping
    QuestionOptionDto createQuestionOption(
            ServletRequest request,
            @PathVariable Long questionId,
            @RequestBody QuestionOptionDto questionOptionDto
    ) {
        User user = (User) request.getAttribute("user");
        return questionOptionService.save(questionId, questionOptionDto, user);
    }

    @PutMapping(value = "/{questionOptionId}")
    QuestionOptionDto updateQuestionOption(
            ServletRequest request,
            @RequestBody QuestionOptionDto questionDto,
            @PathVariable Long questionId,
            @PathVariable Long questionOptionId
    ) {
        User user = (User) request.getAttribute("user");
        return questionOptionService.update(questionId, questionOptionId, questionDto, user);
    }

    @GetMapping(value = "/{questionOptionId}")
    QuestionOptionDto getQuestionOptionById(@PathVariable Long questionId, @PathVariable Long questionOptionId) {
        return questionOptionService.getById(questionId, questionOptionId);
    }

    @DeleteMapping(value = "/{questionOptionId}")
    ResponseEntity deleteQuestionOption(ServletRequest request, @PathVariable Long questionId, @PathVariable Long questionOptionId) {
        User user = (User) request.getAttribute("user");
        questionOptionService.delete(questionId, questionOptionId, user);
        return ResponseEntity.ok().build();
    }
}
