package com.university.itis.mapper;

import com.university.itis.dto.QuestionDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.model.Question;
import com.university.itis.model.Quiz;
import com.university.itis.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionMapper {
    QuizRepository quizRepository;
    QuestionOptionMapper questionOptionMapper;

    public Question toQuestion(QuestionDto questionDto) {
        return toQuestion(questionDto, new Question());
    }

    public Question toQuestion(QuestionDto questionDto, Question question) {
        question.setText(questionDto.getText());
        if (question.getQuestionAnswers() == null) {
            question.setQuestionAnswers(Collections.emptyList());
        }
        if(question.getQuestionOptions() == null) {
            question.setQuestionOptions(Collections.emptyList());
        }
        return question;
    }

    public QuestionDto toDtoConvert(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .options(questionOptionMapper.toListDtoConvert(question.getQuestionOptions()))
                .build();
    }

    public List<QuestionDto> toListDtoConvert(List<Question> questionList) {
        return questionList
                .stream()
                .map(this::toDtoConvert)
                .collect(Collectors.toList());
    }

    public List<Question> toListConvert(List<QuestionDto> questionDtoList) {
        return questionDtoList
                .stream()
                .map(this::toQuestion)
                .collect(Collectors.toList());
    }
}
