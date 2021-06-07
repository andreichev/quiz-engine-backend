package com.university.itis.controller;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizPreviewDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.model.User;
import com.university.itis.services.QuizService;
import lombok.AllArgsConstructor;
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
    QuizPreviewDto saveQuiz(ServletRequest request, @RequestBody EditQuizForm form) {
        User user = (User) request.getAttribute("user");
        return quizService.save(form, user);
    }

    @PutMapping(value = "/{id}")
    QuizPreviewDto updateQuiz(
            ServletRequest request,
            @RequestBody EditQuizForm form,
            @PathVariable String id
    ) {
        User user = (User) request.getAttribute("user");
        return quizService.update(id, form, user);
    }

    @GetMapping(value = "/{id}")
    QuizPreviewDto getQuizById(@PathVariable String id) {
        return quizService.getById(id);
    }

    @GetMapping(value = "/{id}/full")
    QuizFullDto getFullQuizById(ServletRequest request, @PathVariable String id) {
        User user = (User) request.getAttribute("user");
        return quizService.getFullById(user, id);
    }

    @DeleteMapping(value = "/{id}")
    void deleteQuiz(ServletRequest request, @PathVariable String id) {
        User user = (User) request.getAttribute("user");
        quizService.delete(id, user);
    }
}
