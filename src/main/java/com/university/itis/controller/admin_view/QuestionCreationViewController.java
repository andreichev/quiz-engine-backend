package com.university.itis.controller.admin_view;

import com.university.itis.model.Quiz;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class QuestionCreationViewController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "/quiz/{quizId}/add-question/", method = RequestMethod.GET)
    public String addQuestionView(HttpServletRequest request, @PathVariable Long quizId, Authentication authentication, ModelMap modelMap) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        modelMap.put("content", "question-add");
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-add";
        } else {
            return "admin/quiz-edit/index";
        }
    }


    @RequestMapping(value = "/quiz/{quizId}/add-question-manually", method = RequestMethod.GET)
    public String addQuestionManuallyView(HttpServletRequest request, @PathVariable Long quizId, Authentication authentication, ModelMap modelMap) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        modelMap.put("content", "question-add-manually");
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-add-manually";
        } else {
            return "admin/quiz-edit/index";
        }
    }


    @RequestMapping(value = "/quiz/{quizId}/add-question-by-subject", method = RequestMethod.GET)
    public String addQuestionBySubjectView(HttpServletRequest request, @PathVariable Long quizId, Authentication authentication, ModelMap modelMap) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        modelMap.put("content", "question-add-by-subject");
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-add-by-subject";
        } else {
            return "admin/quiz-edit/index";
        }
    }

    @RequestMapping(value = "/quiz/{quizId}/add-question-by-type", method = RequestMethod.GET)
    public String addQuestionByTypeView(HttpServletRequest request, @PathVariable Long quizId, Authentication authentication, ModelMap modelMap) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        modelMap.put("content", "question-add-by-type");
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-add-by-type";
        } else {
            return "admin/quiz-edit/index";
        }
    }
}
