package com.university.itis.controller;

import com.university.itis.dto.answer.QuestionAnswerForm;
import com.university.itis.dto.quiz.QuizParticipantsDto;
import com.university.itis.dto.quiz_passing.FinishedQuizPassingDto;
import com.university.itis.dto.quiz_passing.QuizPassingDto;
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
    void giveAnswer(
            ServletRequest request, @PathVariable String quizId,
            @PathVariable Long passingId, @RequestBody QuestionAnswerForm answer
    ) {
        User user = (User) request.getAttribute("user");
        quizPassingService.giveAnswer(user, answer, passingId, quizId);
    }

    @PostMapping("/{passingId}/finish")
    FinishedQuizPassingDto giveAnswer(
            ServletRequest request, @PathVariable String quizId,
            @PathVariable Long passingId
    ) {
        User user = (User) request.getAttribute("user");
        return quizPassingService.finishPassing(user, quizId, passingId);
    }

    @GetMapping("/{passingId}")
    FinishedQuizPassingDto get(@PathVariable String quizId, @PathVariable Long passingId) {
        return quizPassingService.getFinishedQuizPassing(quizId, passingId);
    }

    @GetMapping(value = "user/{userId}/results")
    QuizParticipantsDto getUserResults(@PathVariable Long userId, @PathVariable String quizId) {
        return quizPassingService.getParticipants(userId, quizId);
    }
}
