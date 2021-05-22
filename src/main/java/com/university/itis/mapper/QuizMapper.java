package com.university.itis.mapper;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.model.Quiz;
import com.university.itis.model.QuizParticipant;
import com.university.itis.model.User;
import com.university.itis.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizMapper {
    private final UserService userService;
    private final UserMapper userMapper;
    private final QuestionMapper questionMapper;

    public Quiz toQuiz(EditQuizForm form) {
        return toQuiz(form, new Quiz());
    }

    public Quiz toQuiz(EditQuizForm form, Quiz quiz) {
        quiz.setTitle(form.getTitle());
        quiz.setDescription(form.getDescription());
        quiz.setActive(form.getIsActive());
        quiz.setAnyOrder(form.getIsAnyOrder());
        quiz.setStartDate(quiz.getStartDate() != null ? quiz.getStartDate() : new Date());
        if (form.getAuthor() != null && form.getAuthor().getId() != null) {
            Optional<User> optionalUser = userService.findOneById(form.getAuthor().getId());
            if(optionalUser.isPresent()) {
                quiz.setAuthor(optionalUser.get());
            } else {
                throw new NotFoundException("User with id " + form.getAuthor().getId() + " not found");
            }
        }
        if (quiz.getParticipants() == null) {
            quiz.setParticipants(Collections.emptyList());
        }
        if (quiz.getQuestions() == null) {
            quiz.setQuestions(Collections.emptyList());
        }
        return quiz;
    }

    public QuizShortDto toShortDtoConvert(Quiz quiz) {
        return QuizShortDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .build();
    }

    public QuizFullDto toFullDtoConvert(Quiz quiz) {
        return QuizFullDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .author(userMapper.toViewDto(quiz.getAuthor()))
                .description(quiz.getDescription())
                .startDate(quiz.getStartDate())
                .isAnyOrder(quiz.isAnyOrder())
                .isActive(quiz.isActive())
                .participants(userMapper.toListDtoConvert(
                        quiz.getParticipants().stream()
                                .map(QuizParticipant::getUser)
                                .collect(Collectors.toList())
                ))
                .questions(questionMapper.toListDtoConvert(quiz.getQuestions()))
                .build();
    }

    public List<QuizShortDto> toListDtoConvert(List<Quiz> quizList) {
        return quizList
                .stream()
                .map(this::toShortDtoConvert)
                .collect(Collectors.toList());
    }
}
