package com.university.itis.controller.admin_view;

import com.university.itis.dto.TripleDto;
import com.university.itis.model.Quiz;
import com.university.itis.repository.QuizRepository;
import com.university.itis.services.impl.SparqlServiceImpl;
import com.university.itis.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
@AllArgsConstructor
public class QuestionCreationViewController {

    private final QuizRepository quizRepository;
    private final SparqlServiceImpl sparqlService;

    @RequestMapping(value = "/quiz/{quizId}/add-question/", method = RequestMethod.GET)
    public String addQuestionView(HttpServletRequest request,
                                  @PathVariable Long quizId,
                                  Authentication authentication,
                                  ModelMap modelMap) throws Exception {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isPresent() == false) {
            throw new Exception("Quiz not exists");
        }
        if (quiz.get().getAuthor().getUsername().equals(authentication.getName()) == false) {
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
    public String addQuestionManuallyView(HttpServletRequest request,
                                          @PathVariable Long quizId,
                                          Authentication authentication,
                                          ModelMap modelMap) throws Exception {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isPresent() == false) {
            throw new Exception("Quiz not exists");
        }
        if (quiz.get().getAuthor().getUsername().equals(authentication.getName()) == false) {
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
    public String addQuestionBySubjectView(HttpServletRequest request,
                                           @PathVariable Long quizId,
                                           Authentication authentication,
                                           ModelMap modelMap) throws Exception {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isPresent() == false) {
            throw new Exception("Quiz not exists");
        }
        if (quiz.get().getAuthor().getUsername().equals(authentication.getName()) == false) {
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

    @RequestMapping(value = "/quiz/{quizId}/add-question-by-search", method = RequestMethod.GET)
    public String addQuestionBySearchView(HttpServletRequest request,
                                        @PathVariable Long quizId,
                                        Authentication authentication,
                                        ModelMap modelMap) throws Exception {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isPresent() == false) {
            throw new Exception("Quiz not exists");
        }
        if (quiz.get().getAuthor().getUsername().equals(authentication.getName()) == false) {
            throw new Exception("Access is denied");
        }
        modelMap.put("content", "question-add-by-search");
        modelMap.put("quiz", quiz.get());
        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-add-by-search";
        } else {
            return "admin/quiz-edit/index";
        }
    }

    @RequestMapping(value = "/quiz/{quizId}/add-question-random", method = RequestMethod.GET)
    public String addQuestionRandomView(HttpServletRequest request,
                                          @PathVariable Long quizId,
                                          Authentication authentication,
                                          ModelMap modelMap) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        modelMap.put("content", "question-add-random");
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-add-random";
        } else {
            return "admin/quiz-edit/index";
        }
    }

    @RequestMapping(value = "/quiz/{quizId}/add-question-with-entity", method = RequestMethod.GET)
    public String addQuestionByEntityView(HttpServletRequest request,
                                        @PathVariable Long quizId,
                                        @RequestParam String entity,
                                        @RequestParam(required = false) String label,
                                        Authentication authentication,
                                        ModelMap modelMap) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        List<TripleDto> suitableTriples = sparqlService.getSuitableTriples(entity);

        modelMap.put("content", "question-add-with-entity");
        modelMap.put("entity", entity);
        modelMap.put("label", (label == null) ? entity : label);
        modelMap.put("triples", suitableTriples);
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "admin/quiz-edit/question-add-with-entity";
        } else {
            return "admin/quiz-edit/index";
        }
    }
}
