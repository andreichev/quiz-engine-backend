package com.university.itis.controller;

import com.university.itis.dto.QuizDto;
import com.university.itis.dto.UserDto;
import com.university.itis.model.User;
import com.university.itis.services.QuizService;
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
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController extends ResponseCreator {

    private final Validator validator;
    private final QuizService quizService;

    @GetMapping(value = "/public-quiz-list")
    ResponseEntity getAllPublic() {
        return createGoodResponse(quizService.getAllActive());
    }

    @GetMapping(value = "/own-list")
    ResponseEntity getAllByAuthor(ServletRequest request) {
        User user = (User) request.getAttribute("user");
        return createGoodResponse(quizService.getAllByAuthor(user));
    }

    @PostMapping
    ResponseEntity saveQuiz(ServletRequest request, @RequestBody QuizDto quizDto) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuizFormError(quizDto);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = (User) request.getAttribute("user");
        quizDto.setAuthor(
                UserDto
                        .builder()
                        .id(user.getId())
                        .build()
        );
        return createGoodResponse(quizService.saveQuiz(quizDto));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity getQuiz(@PathVariable Long id) {
        return createGoodResponse(quizService.getQuizById(id));
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity deleteQuiz(ServletRequest request, @PathVariable Long id) {
        User user = (User) request.getAttribute("user");
        quizService.deleteById(id, user);
        return createGoodResponse();
    }
}
