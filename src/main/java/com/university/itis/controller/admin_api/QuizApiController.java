package com.university.itis.controller.admin_api;

import com.university.itis.model.Quiz;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class QuizApiController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "/quiz/{id}", method = RequestMethod.POST)
    public @ResponseBody
    Map editQuiz(@ModelAttribute Quiz quiz, Authentication authentication) throws Exception {

        Optional<Quiz> oldQuiz = quizRepository.findById(quiz.getId());

        if(!oldQuiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if(!oldQuiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        oldQuiz.get().setTitle(quiz.getTitle());
        oldQuiz.get().setDescription(quiz.getDescription());
        oldQuiz.get().setStartDate(quiz.getStartDate());
        oldQuiz.get().setActive(quiz.isActive());

        quizRepository.save(oldQuiz.get());

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("status", "ok");

        return map;
    }
}
