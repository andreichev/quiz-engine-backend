package com.university.itis.controller.admin_view;

import com.university.itis.model.Question;
import com.university.itis.model.Quiz;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.utils.Utils;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class QuizViewController {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    @RequestMapping(value = "/quiz/{id}", method = RequestMethod.GET)
    public String editQuizView(HttpServletRequest request, @PathVariable Long id, Authentication authentication, ModelMap modelMap) throws Exception {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent() == false) {
            throw new Exception("Quiz not exists");
        }
        if (quiz.get().getAuthor().getUsername().equals(authentication.getName()) == false) {
            throw new Exception("Access is denied");
        }
        modelMap.put("content", "edit");
        modelMap.put("quiz", quiz.get());
        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/edit";
        } else {
            return "admin/quiz-edit/index";
        }
    }

    @RequestMapping(value = "/quiz/{quizId}/question/{questionId}", method = RequestMethod.GET)
    public String editQuestionView(HttpServletRequest request, @PathVariable Long quizId, @PathVariable Long questionId, Authentication authentication, ModelMap modelMap) throws Exception {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        Optional<Question> question = questionRepository.findById(questionId);
        if (quiz.isPresent() == false) {
            throw new Exception("Quiz not exists");
        }
        if (question.isPresent() == false) {
            throw new Exception("Question not exists");
        }
        if (quiz.get().getAuthor().getUsername().equals(authentication.getName()) == false) {
            throw new Exception("Access is denied");
        }
        modelMap.put("content", "question-edit");
        modelMap.put("quiz", quiz.get());
        modelMap.put("question", question.get());
        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-edit";
        } else {
            return "admin/quiz-edit/index";
        }
    }
}
