package com.university.itis.controller;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;
import com.university.itis.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
@RequestMapping("/quiz/{quizId}/question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    List<QuestionDto> getAll(@PathVariable String quizId) {
        return questionService.getAllByQuizId(quizId);
    }

    @PostMapping
    QuestionDto createQuestion(
            ServletRequest request,
            @PathVariable String quizId,
            @RequestBody QuestionDto questionDto
    ) {
        User user = (User) request.getAttribute("user");
        return questionService.save(quizId, questionDto, user);
    }

    @PutMapping(value = "/{questionId}")
    QuestionDto updateQuestion(
            ServletRequest request,
            @RequestBody QuestionDto questionDto,
            @PathVariable String quizId,
            @PathVariable Long questionId
    ) {
        User user = (User) request.getAttribute("user");
        return questionService.update(quizId, questionId, questionDto, user);
    }

    @GetMapping(value = "/{questionId}")
    QuestionDto getQuestionById(@PathVariable String quizId, @PathVariable Long questionId) {
        return questionService.getById(quizId, questionId);
    }

    @DeleteMapping(value = "/{questionId}")
    ResponseEntity deleteQuestion(ServletRequest request, @PathVariable String quizId, @PathVariable Long questionId) {
        User user = (User) request.getAttribute("user");
        questionService.delete(quizId, questionId, user);
        return ResponseEntity.ok().build();
    }
}
