package com.university.itis.mapper;

import com.university.itis.dto.QuizDto;
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

    public Quiz toQuiz(QuizDto quizDto) {
        return toQuiz(quizDto, new Quiz());
    }

    public Quiz toQuiz(QuizDto quizDto, Quiz quiz) {
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setActive(quizDto.getIsActive());
        quiz.setAnyOrder(quizDto.getIsAnyOrder());
        quiz.setStartDate(quizDto.getStartDate() != null ? quizDto.getStartDate() : new Date());
        if (quizDto.getAuthor() != null && quizDto.getAuthor().getId() != null) {
            Optional<User> optionalUser = userService.findOneById(quizDto.getAuthor().getId());
            if(optionalUser.isPresent()) {
                quiz.setAuthor(optionalUser.get());
            } else {
                throw new NotFoundException("User with id " + quizDto.getAuthor().getId() + " not found");
            }
        }
        quiz.setParticipants(Collections.emptySet());
        return quiz;
    }

    public QuizDto toDtoConvert(Quiz quiz) {
        return QuizDto.builder()
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
                .build();
    }

    public List<QuizDto> toListDtoConvert(List<Quiz> quizList) {
        return quizList
                .stream()
                .map(this::toDtoConvert)
                .collect(Collectors.toList());
    }

    public List<Quiz> toListConvert(List<QuizDto> quizDtoList) {
        return quizDtoList
                .stream()
                .map(this::toQuiz)
                .collect(Collectors.toList());
    }
}
