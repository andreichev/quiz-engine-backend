package com.university.itis.controller.admin_api;

import com.github.jsonldjava.utils.Obj;
import com.university.itis.model.Question;
import com.university.itis.model.Quiz;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.services.SparqlQueryService;
import com.university.itis.utils.ClassesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class QuestionApiController {

    @Autowired
    private SparqlQueryService sparqlQueryService;

    @Autowired
    private ClassesStorage classesStorage;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "/quiz/{quizId}/add-question/", method = RequestMethod.POST)
    public @ResponseBody
    Map addQuestion(@PathVariable Long quizId,
                    @ModelAttribute Question question,
                    Authentication authentication) throws Exception {

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

    @RequestMapping(value = "/quiz/{quizId}/question/{questionId}", method = RequestMethod.POST)
    public @ResponseBody Map editQuestion(@ModelAttribute Question question, @PathVariable Long questionId, Authentication authentication) throws Exception {

        Optional<Question> oldQuestion = questionRepository.findById(questionId);

        if(!oldQuestion.isPresent()) {
            throw new Exception("Question not exists");
        }

        if(!oldQuestion.get().getQuiz().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied");
        }

        oldQuestion.get().setText(question.getText());

        questionRepository.save(oldQuestion.get());

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("status", "ok");

        return map;
    }

    @RequestMapping(value = "/quiz/{quizId}/question/{questionId}", method = RequestMethod.DELETE)
    public @ResponseBody
    Map deleteQuestion(@PathVariable Long questionId, @PathVariable Long quizId, Authentication authentication) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists.");
        }

        if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
            throw new Exception("Access is denied.");
        }

        questionRepository.deleteById(questionId);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("status", "ok");

        return map;
    }

    @RequestMapping(value = "/api/random-entity", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Map fetchRandomEntity(@RequestParam String type,
                          @RequestParam String region) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        if (type.equals("dbo:Place")) {

            String[] coords = region.split(",");
            map.put("entity", sparqlQueryService.selectPlaceInRegion(coords));
            return map;

        } else {

            map.put("entity", sparqlQueryService.selectEntityForQuestion(type));
            return map;

        }
    }

    @RequestMapping(value = "/api/find-entity", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Map fetchRandomEntityList(@RequestParam String type,
                              @RequestParam String region,
                              @RequestParam String query) {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        if (type.equals("dbo:Place")) {
            String[] coords = region.split(",");
            map.put("entities", sparqlQueryService.selectPlacesInRegion(coords));
        } else {
            map.put("entities", sparqlQueryService.findEntities(type, query));
        }

        return map;
    }

    @RequestMapping(value = "/api/types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Map types() {
        return classesStorage.getClasses();
    }
}
