package com.university.itis.controller.admin;

import com.university.itis.model.Quiz;
import com.university.itis.model.User;
import com.university.itis.repository.QuizRepository;
import com.university.itis.repository.UserRepository;
import com.university.itis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "admin/index";
    }

    @RequestMapping(value = "/boards/quiz-list", method = RequestMethod.GET)
    public String quizList(HttpServletRequest request, ModelMap modelMap, Authentication authentication) {
        if (Utils.isAjax(request)) {
            List<Quiz> quizList = quizRepository.findAllByAuthorUsername(authentication.getName());
            modelMap.put("quizzes", quizList);
            return "admin/boards/quiz-list";
        } else {
            return index();
        }
    }

    @RequestMapping(value = "/boards/quiz-list/add", method = RequestMethod.GET)
    public String addQuiz(HttpServletRequest request, ModelMap modelMap) {
        if (Utils.isAjax(request)) {
            modelMap.put("quiz", new Quiz());
            return "admin/boards/quiz-list-add";
        } else {
            return index();
        }
    }

    @RequestMapping(value = "/boards/quiz-list/add", method = RequestMethod.POST)
    public @ResponseBody Map saveQuiz(@ModelAttribute Quiz quiz, Authentication authentication) {

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
