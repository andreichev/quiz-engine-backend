package com.university.itis.controller;

import com.university.itis.model.Question;
import com.university.itis.model.QuestionAnswer;
import com.university.itis.model.Quiz;
import com.university.itis.model.QuizParticipant;
import com.university.itis.repository.QuestionAnswerRepository;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.repository.QuizParticipantRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/quiz")
public class QuizController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizParticipantRepository quizParticipantRepository;

    @Autowired
    QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelMap modelMap) {

        List<Quiz> quizList = quizRepository.findAll();

        modelMap.put("content", "quiz-list");
        modelMap.put("quizList", quizList);

        if (Utils.isAjax(request)) {
            return "site/quiz-list";
        } else {
            return "site/index";
        }
    }

    @RequestMapping(value = "/{quizId}", method = RequestMethod.GET)
    public String participants(HttpServletRequest request,
                               ModelMap modelMap,
                               @PathVariable Long quizId) throws Exception {

        List<QuizParticipant> quizParticipants = quizParticipantRepository.findAll();
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        modelMap.put("content", "participants");
        modelMap.put("quiz", quiz.get());
        modelMap.put("participants", quizParticipants);

        if (Utils.isAjax(request)) {
            return "site/participants";
        } else {
            return "site/index";
        }
    }

    @RequestMapping(value = "/{quizId}/participant", method = RequestMethod.POST)
    public @ResponseBody
    Map addQuestion(@PathVariable Long quizId, @ModelAttribute QuizParticipant quizParticipant) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        quizParticipant = quizParticipantRepository.save(quizParticipant);

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("status", "ok");
        map.put("participantId", quizParticipant.getId());

        return map;
    }

    @RequestMapping(value = "/{quizId}/participant/{participantId}", method = RequestMethod.GET)
    public String participantStatus(HttpServletRequest request,
                                    ModelMap modelMap,
                                    @PathVariable Long participantId,
                                    @PathVariable Long quizId) throws Exception {

        List<QuestionAnswer> questionAnswers = questionAnswerRepository.findByParticipantId(participantId);
        List<Question> questions = questionRepository.findAllByQuizId(quizId);
        Optional<QuizParticipant> participant = quizParticipantRepository.findById(participantId);
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!participant.isPresent()) {
            throw new Exception("Participant doesnt exist.");
        }

        modelMap.put("content", "participant-status");
        modelMap.put("participant", participant.get());
        modelMap.put("answers", questionAnswers);
        modelMap.put("questions", questions);
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "site/participant-status";
        } else {
            return "site/index";
        }
    }

    @RequestMapping(value = "/{quizId}/participant/{participantId}/question/{questionId}", method = RequestMethod.GET)
    public String question(HttpServletRequest request,
                           ModelMap modelMap,
                           @PathVariable Long participantId,
                           @PathVariable Long quizId,
                           @PathVariable Long questionId) throws Exception {

        Optional<Question> currentQuestion = questionRepository.findById(questionId);
        Optional<QuizParticipant> participant = quizParticipantRepository.findById(participantId);
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        if (!participant.isPresent()) {
            throw new Exception("Participant doesnt exist.");
        }

        if (!currentQuestion.isPresent()) {
            throw new Exception("Question doesnt exist.");
        }

        modelMap.put("content", "question");
        modelMap.put("participant", participant.get());
        modelMap.put("question", currentQuestion.get());
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "site/question";
        } else {
            return "site/index";
        }
    }

    @RequestMapping(value = "/{quizId}/participant/{participantId}/question/{questionId}", method = RequestMethod.POST)
    public @ResponseBody
    Map putAnswer(@PathVariable Long quizId,
                  @PathVariable Long participantId,
                  @PathVariable Long questionId,
                  @ModelAttribute QuestionAnswer questionAnswer) throws Exception {

        Optional<QuestionAnswer> oldAnswer =
                questionAnswerRepository.findByParticipantIdAndQuestionId(
                        questionAnswer.getParticipant().getId(),
                        questionAnswer.getQuestion().getId()
                );

        oldAnswer.ifPresent(questionAnswer1 -> questionAnswerRepository.delete(questionAnswer1));

        questionAnswerRepository.save(questionAnswer);

        List<Question> questions = questionRepository.findAllByQuizId(quizId);

        Optional<QuizParticipant> participant = quizParticipantRepository.findById(participantId);

        if (!participant.isPresent()) {
            throw new Exception("Participant doesnt exist.");
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Long nextQuestionId = -1L;

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(questionId)) {
                if (i < questions.size() - 1) {
                    nextQuestionId = questions.get(i + 1).getId();
                }
            }
        }

        map.put("status", "ok");
        map.put("nextQuestionId", nextQuestionId);

        return map;
    }


    @RequestMapping(value = "/{quizId}/participant/{participantId}/results", method = RequestMethod.GET)
    public String getResults(HttpServletRequest request,
                             ModelMap modelMap,
                             @PathVariable Long participantId,
                             @PathVariable Long quizId) throws Exception {

        Optional<Quiz> quiz = quizRepository.findById(quizId);

        if (!quiz.isPresent()) {
            throw new Exception("Quiz not exists");
        }

        List<QuestionAnswer> questionAnswers = questionAnswerRepository.findByParticipantId(participantId);
        Optional<QuizParticipant> participant = quizParticipantRepository.findById(participantId);

        if (!participant.isPresent()) {
            throw new Exception("Participant doesnt exist.");
        }

        modelMap.put("content", "results");
        modelMap.put("participant", participant.get());
        modelMap.put("answers", questionAnswers);
        modelMap.put("quiz", quiz.get());

        if (Utils.isAjax(request)) {
            return "site/results";
        } else {
            return "site/index";
        }
    }
}
