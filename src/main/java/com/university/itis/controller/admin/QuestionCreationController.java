package com.university.itis.controller.admin;

import com.university.itis.model.Question;
import com.university.itis.model.Quiz;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class QuestionCreationController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

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

    @RequestMapping(value = "/quiz/{quizId}/add-question/", method = RequestMethod.POST)
    public @ResponseBody
    Map addQuestion(@PathVariable Long quizId, @ModelAttribute Question question, Authentication authentication) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        question = questionRepository.save(question);

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("status", "ok");
        map.put("questionId", question.getId());

        return map;
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

    @RequestMapping(value = "/quiz/{quizId}/add-question-with-options", method = RequestMethod.POST)
    public @ResponseBody Map addQuestionWithOptions(HttpServletRequest request, @PathVariable Long quizId, Authentication authentication) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        Question question = new Question();
        question.setQuiz(quiz.get());
        question.setText(request.getParameter("questionText"));

        System.out.println(request.getParameter("options"));

        question = questionRepository.save(question);

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("status", "ok");
        map.put("questionId", question.getId());

        return map;
    }
}
