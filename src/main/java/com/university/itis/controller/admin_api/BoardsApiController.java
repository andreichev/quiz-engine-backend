package com.university.itis.controller.admin_api;

import com.university.itis.model.Quiz;
import com.university.itis.model.User;
import com.university.itis.repository.QuizRepository;
import com.university.itis.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
@AllArgsConstructor
public class BoardsApiController {

    final private QuizRepository quizRepository;
    final private UserRepository userRepository;

    @RequestMapping(value = "/boards/quiz-list/add", method = RequestMethod.POST)
    public @ResponseBody
    Map saveQuiz(@ModelAttribute Quiz quiz, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());
        quiz.setStartDate(new Date());
        quiz.setAuthor(user);
        quiz.setActive(true);
        quiz.setAnyOrder(true);
        quizRepository.save(quiz);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("status", "ok");
        return map;
    }

    @RequestMapping(value = "/boards/quiz-list/edit", method = RequestMethod.POST)
    public @ResponseBody Map editQuizFromTable(@ModelAttribute Quiz quiz) throws Exception {

        Optional<Quiz> oldQuiz = quizRepository.findById(quiz.getId());
        if(!oldQuiz.isPresent() || quiz.getTitle() == null || quiz.getStartDate() == null) {
            throw new Exception("Can't edit quiz that doesn't exists.");
        }

        oldQuiz.get().setTitle(quiz.getTitle());
        oldQuiz.get().setStartDate(quiz.getStartDate());
        oldQuiz.get().setActive(quiz.isActive());

        quizRepository.save(oldQuiz.get());

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("status", "ok");
        return map;
    }

    @RequestMapping(value = "/boards/quiz-list/edit", method = RequestMethod.DELETE)
    public @ResponseBody Map deleteQuiz(@RequestParam Long id) {

        quizRepository.deleteById(id);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("status", "ok");
        return map;
    }
}
