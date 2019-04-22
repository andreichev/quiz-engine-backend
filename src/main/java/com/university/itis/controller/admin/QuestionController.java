package com.university.itis.controller.admin;

import com.university.itis.model.Question;
import com.university.itis.model.Quiz;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.services.SparqlQueryService;
import com.university.itis.utils.ClassesStorage;
import com.university.itis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class QuestionController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    private SparqlQueryService sparqlQueryService;

    @Autowired
    private ClassesStorage classesStorage;

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

        questionRepository.save(question);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("status", "ok");

        return map;
    }

    @RequestMapping(value = "/api/random-entity", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Map fetchRandomEntity(@RequestParam String type, @RequestParam String region) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        if (type.equals("dbo:Place")) {
            String[] coords = region.split(",");
            map.put("entity", sparqlQueryService.selectPlaceInRegion(coords));
        } else {
            map.put("entity", sparqlQueryService.selectEntityForQuestion(type));
        }

        return map;
    }

    @RequestMapping(value = "/api/entity-list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Map fetchRandomEntityList(@RequestParam String type, @RequestParam String region) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        if (type.equals("dbo:Place")) {
            String[] coords = region.split(",");
            map.put("entities", sparqlQueryService.selectPlacesInRegion(coords));
        } else {
            map.put("entity", sparqlQueryService.selectEntityForQuestion(type));
        }

        return map;
    }

    @RequestMapping(value = "/api/types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Map types() {
        return classesStorage.getClasses();
    }
}
