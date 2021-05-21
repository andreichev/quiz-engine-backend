package com.university.itis.controller.admin_view.boards;

import com.university.itis.model.Quiz;
import com.university.itis.repository.QuizRepository;
import com.university.itis.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@AllArgsConstructor
public class BoardsQuizzesController {

    private final QuizRepository quizRepository;

    @RequestMapping(value = "/boards/quiz-list", method = RequestMethod.GET)
    public String quizList(HttpServletRequest request, ModelMap modelMap, Authentication authentication) {
        if (Utils.isAjax(request)) {
            List<Quiz> quizList = quizRepository.findAllByAuthorUsername(authentication.getName());
            modelMap.put("quizzes", quizList);
            return "admin/boards/quiz-list";
        } else {
            return "admin/index";
        }
    }

    @RequestMapping(value = "/boards/quiz-list/add", method = RequestMethod.GET)
    public String addQuiz(HttpServletRequest request, ModelMap modelMap) {
        if (Utils.isAjax(request)) {
            modelMap.put("quiz", new Quiz());
            return "admin/boards/quiz-list-add";
        } else {
            return "admin/index";
        }
    }
}
