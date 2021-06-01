package com.university.itis.mapper;

import com.university.itis.dto.QuestionDto;
import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.exceptions.ValidationException;
import com.university.itis.model.Question;
import com.university.itis.model.Quiz;
import com.university.itis.model.QuizPassing;
import com.university.itis.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizMapper {
    private final UserMapper userMapper;
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;

    public Quiz toQuiz(EditQuizForm form) {
        return toQuiz(form, new Quiz());
    }

    public Quiz toQuiz(EditQuizForm form, Quiz quiz) {
        quiz.setTitle(form.getTitle());
        quiz.setDescription(form.getDescription());
        if (form.getQuestions() != null) {
            quiz.setQuestions(new ArrayList<>());
            form.getQuestions().forEach(questionDto -> {
                if (questionDto.getId() != null) {
                    quiz.getQuestions().add(
                            questionRepository.findById(questionDto.getId()).orElseThrow(() -> {
                                throw new NotFoundException("");
                            })
                    );
                }
            });
        }
        if (quiz.getQuestions() == null) {
            quiz.setQuestions(Collections.emptyList());
        }
        quiz.setPublic(form.getIsPublic());
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
                .isPublic(quiz.isPublic())
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
