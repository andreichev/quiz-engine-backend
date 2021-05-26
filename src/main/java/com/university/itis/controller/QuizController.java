package com.university.itis.controller;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.model.User;
import com.university.itis.services.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping(value = "/public-quiz-list")
    List<QuizShortDto> getAllPublic() {
        return quizService.getAllPublic();
    }

    @GetMapping(value = "/own-list")
    List<QuizShortDto> getAllByAuthor(ServletRequest request) {
        User user = (User) request.getAttribute("user");
        return quizService.getAllByAuthor(user);
    }

    @PostMapping
    QuizFullDto saveQuiz(ServletRequest request, @RequestBody EditQuizForm form) {
        User user = (User) request.getAttribute("user");
        return quizService.save(form, user);
    }

    @PutMapping(value = "/{id}")
    QuizFullDto updateQuiz(
            ServletRequest request,
            @RequestBody EditQuizForm form,
            @PathVariable Long id
    ) {
        User user = (User) request.getAttribute("user");
        return quizService.update(id, form, user);
    }

    @GetMapping(value = "/{id}")
    QuizFullDto getQuizById(ServletRequest request, @PathVariable Long id) {
        User user = (User) request.getAttribute("user");
        return quizService.getById(id, user);
    }

    @GetMapping(value = "/secret/{secret}")
    QuizFullDto getQuizBySecret(@PathVariable String secret) {
        return quizService.getBySecret(secret);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity deleteQuiz(ServletRequest request, @PathVariable Long id) {
        User user = (User) request.getAttribute("user");
        quizService.delete(id, user);
        return ResponseEntity.ok().build();
    }
}
