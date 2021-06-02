package com.university.itis.mapper;

import com.university.itis.dto.QuestionDto;
import com.university.itis.dto.QuestionOptionDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.model.Question;
import com.university.itis.model.QuestionOption;
import com.university.itis.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (questionDto.getOptions() != null) {
            question.getOptions().removeIf(option -> questionDto.getOptions().stream()
                    .anyMatch(item -> item.getId().equals(option.getId())) == false);
            for (QuestionOptionDto optionDto : questionDto.getOptions()) {
                if (optionDto.getId() != null) {
                    QuestionOption option = question.getOptions().stream()
                            .filter(item -> item.getId().equals(optionDto.getId()))
                            .findAny()
                            .orElseThrow(() -> new NotFoundException("QuestionOption with id " + optionDto.getId() + " not found"));
                    questionOptionMapper.toQuestionOption(optionDto, option);
                } else {
                    QuestionOption option = questionOptionMapper.toQuestionOption(optionDto);
                    option.setQuestion(question);
                    question.getOptions().add(option);
                }
            }
        }
        return question;
    }

    public QuestionDto toDtoConvert(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .options(questionOptionMapper.toListDtoConvert(question.getOptions()))
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
