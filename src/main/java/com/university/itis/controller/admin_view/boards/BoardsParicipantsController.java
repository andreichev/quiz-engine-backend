package com.university.itis.controller.admin_view.boards;

import com.university.itis.model.Quiz;
import com.university.itis.model.QuizParticipant;
import com.university.itis.repository.QuizParticipantRepository;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
@AllArgsConstructor
public class BoardsParicipantsController {

    private final QuizRepository quizRepository;
    private final QuizParticipantRepository quizParticipantRepository;

    @RequestMapping(value = "/boards/users", method = RequestMethod.GET)
    public String usersSelectQuiz(HttpServletRequest request,
                                  ModelMap modelMap,
                                  Authentication authentication) {
        if (Utils.isAjax(request)) {
            List<Quiz> quizList = quizRepository.findAllByAuthorUsername(authentication.getName());
            modelMap.put("quizzes", quizList);
            return "admin/boards/users-select-quiz";
        } else {
            return "admin/index";
        }
    }

    @RequestMapping(value = "/boards/participants-results/quiz/{quizId}", method = RequestMethod.GET)
    public String selectParticipantForQuiz(HttpServletRequest request,
                                           ModelMap modelMap,
                                           Authentication authentication,
                                           @PathVariable Long quizId) throws Exception {
        if (Utils.isAjax(request)) {
            Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
            if (!optionalQuiz.isPresent()) {
                throw new Exception("Quiz not exists");
            }
            if (!optionalQuiz.get().getAuthor().getUsername().equals(authentication.getName())) {
                throw new Exception("Access is denied");
            }
            modelMap.put("quiz", optionalQuiz.get());
            return "admin/boards/quiz-participants";
        } else {
            return "admin/index";
        }
    }

    @RequestMapping(value = "/boards/quiz/{quizId}/participant/{participantId}", method = RequestMethod.GET)
    public String rateForParticipant(HttpServletRequest request,
                                     ModelMap modelMap,
                                     Authentication authentication,
                                     @PathVariable Long quizId,
                                     @PathVariable Long participantId) throws Exception {
        if (Utils.isAjax(request)) {
            Optional<Quiz> quiz = quizRepository.findById(quizId);
            if (!quiz.isPresent()) {
                throw new Exception("Quiz not exists");
            }
            if (!quiz.get().getAuthor().getUsername().equals(authentication.getName())) {
                throw new Exception("Access is denied");
            }
            Optional<QuizParticipant> quizParticipant = quizParticipantRepository.findById(participantId);
            if (!quizParticipant.isPresent()) {
                throw new Exception("Participant not exists");
            }
            modelMap.put("quiz", quiz.get());
            modelMap.put("participant", quizParticipant.get());
            return "admin/boards/user-results";
        } else {
            return "admin/index";
        }
    }
}
