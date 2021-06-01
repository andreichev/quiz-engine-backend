package com.university.itis.mapper;

import com.university.itis.dto.QuestionDto;
import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.model.Question;
import com.university.itis.model.Quiz;
import com.university.itis.model.QuizPassing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        quiz.setPublic(form.getIsPublic());
        quiz.setAnyOrder(form.getIsAnyOrder());
        if (quiz.getQuestions() == null) {
            quiz.setQuestions(new ArrayList<>());
        }
        if (form.getQuestions() != null) {
            for (QuestionDto questionDto : form.getQuestions()) {
                if (questionDto.getId() != null) {
                    Question question = quiz.getQuestions().stream()
                            .filter(item -> item.getId().equals(questionDto.getId()))
                            .findAny()
                            .orElseThrow(() -> new NotFoundException("Question with id " + questionDto.getId() + " not found"));
                    questionMapper.toQuestion(questionDto, question);
                } else {
                    Question question = questionMapper.toQuestion(questionDto);
                    question.setQuiz(quiz);
                    quiz.getQuestions().add(question);
                }
            }
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
