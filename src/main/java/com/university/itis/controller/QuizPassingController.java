package com.university.itis.controller;

import com.university.itis.dto.QuestionAnswerDto;
import com.university.itis.dto.QuizPassingDto;
import com.university.itis.model.User;
import com.university.itis.services.QuizPassingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@RestController
@RequestMapping("/quiz/{quizId}/passing")
@AllArgsConstructor
public class QuizPassingController {
    private final QuizPassingService quizPassingService;

    @PostMapping
    QuizPassingDto createPassing(ServletRequest request, @PathVariable String quizId) {
        User user = (User) request.getAttribute("user");
        return quizPassingService.createPassing(user, quizId);
    }

    @PostMapping("/{passingId}/answer")
    QuestionAnswerDto giveAnswer(
            ServletRequest request, @PathVariable String quizId,
            @PathVariable Long passingId, @RequestBody QuestionAnswerDto answer
    ) {
        User user = (User) request.getAttribute("user");
        return quizPassingService.giveAnswer(user, answer, passingId, quizId);
    }
}
