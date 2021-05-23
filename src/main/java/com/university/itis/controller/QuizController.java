package com.university.itis.controller;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.model.User;
import com.university.itis.services.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping(value = "/public-quiz-list")
    ResponseEntity getAllPublic() {
        return quizService.getAllActive().getResponseEntity();
    }

    @GetMapping(value = "/own-list")
    ResponseEntity getAllByAuthor(ServletRequest request) {
        User user = (User) request.getAttribute("user");
        return quizService.getAllByAuthor(user).getResponseEntity();
    }

    @PostMapping
    ResponseEntity saveQuiz(ServletRequest request, @RequestBody EditQuizForm form) {
        User user = (User) request.getAttribute("user");
        return quizService.save(form, user).getResponseEntity();
    }

    @PutMapping(value = "/{id}")
    ResponseEntity updateQuiz(
            ServletRequest request,
            @RequestBody EditQuizForm form,
            @PathVariable Long id
    ) {
        User user = (User) request.getAttribute("user");
        return quizService.update(id, form, user).getResponseEntity();
    }

    @GetMapping(value = "/{id}")
    ResponseEntity getQuiz(@PathVariable Long id) {
        return quizService.getById(id).getResponseEntity();
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity deleteQuiz(ServletRequest request, @PathVariable Long id) {
        User user = (User) request.getAttribute("user");
        return quizService.delete(id, user).getResponseEntity();
    }
}
