package com.university.itis.mapper;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.model.Quiz;
import com.university.itis.model.QuizPassing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizMapper {
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
                                .map(QuizPassing::getUser)
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
