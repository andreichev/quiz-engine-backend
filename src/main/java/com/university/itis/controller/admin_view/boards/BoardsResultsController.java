package com.university.itis.controller.admin_view.boards;

import com.university.itis.model.QuestionAnswer;
import com.university.itis.model.Quiz;
import com.university.itis.model.QuizParticipant;
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
import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class BoardsResultsController {

    @Autowired
    private QuizRepository quizRepository;

    @RequestMapping(value = "/boards/rate", method = RequestMethod.GET)
    public String rateSelectQuiz(HttpServletRequest request,
                                 ModelMap modelMap,
                                 Authentication authentication) {
        if (Utils.isAjax(request)) {
            List<Quiz> quizList = quizRepository.findAllByAuthorUsername(authentication.getName());
            modelMap.put("quizzes", quizList);
            return "admin/boards/rate-select-quiz";
        } else {
            return "admin/index";
        }
    }

    @RequestMapping(value = "/boards/rate/quiz/{quizId}", method = RequestMethod.GET)
    public String rateForQuiz(HttpServletRequest request,
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
            Quiz quiz = optionalQuiz.get();
            SortedSet<QuizParticipant> participants = quiz.getParticipants();
            List<String> participantResults = new ArrayList<>();

            Iterator<QuizParticipant> participantIterator = participants.iterator();
            while (participantIterator.hasNext()) {
                QuizParticipant next = participantIterator.next();

                int countOfCorrect = 0;
                Iterator<QuestionAnswer> answerIterator = next.getQuestionAnswers().iterator();
                while (answerIterator.hasNext()) {
                    if(answerIterator.next().getQuestionOption().isCorrect()) {
                        countOfCorrect++;
                    }
                }

                String item = next.getName() + ": " + String.valueOf(countOfCorrect);
                participantResults.add(item);
            }

            modelMap.put("quiz", quiz);
            modelMap.put("participantResults", participantResults);

            return "admin/boards/rate-quiz";
        } else {
            return "admin/index";
        }
    }
}
