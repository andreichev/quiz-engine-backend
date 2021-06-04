package com.university.itis.mapper;

import com.university.itis.dto.question_option.QuestionOptionDto;
import com.university.itis.dto.question_option.QuestionOptionShortDto;
import com.university.itis.model.QuestionOption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionOptionMapper {
    public QuestionOption toQuestionOption(QuestionOptionDto questionOptionDto) {
        return toQuestionOption(questionOptionDto, new QuestionOption());
    }

    public QuestionOption toQuestionOption(QuestionOptionDto questionOptionDto, QuestionOption questionOption) {
        questionOption.setText(questionOptionDto.getText());
        questionOption.setIsCorrect(questionOptionDto.getIsCorrect());
        return questionOption;
    }

    public QuestionOptionDto toDtoConvert(QuestionOption questionOption) {
        return QuestionOptionDto.builder()
                .id(questionOption.getId())
                .text(questionOption.getText())
                .isCorrect(questionOption.getIsCorrect())
                .build();
    }

    public QuestionOptionShortDto toShortDtoConvert(QuestionOption questionOption) {
        return QuestionOptionShortDto.builder()
                .id(questionOption.getId())
                .text(questionOption.getText())
                .build();
    }

    public List<QuestionOptionDto> toListDtoConvert(List<QuestionOption> questionOptionList) {
        return questionOptionList
                .stream()
                .map(this::toDtoConvert)
                .collect(Collectors.toList());
    }

    public List<QuestionOptionShortDto> toListShortDtoConvert(List<QuestionOption> questionOptionList) {
        return questionOptionList
                .stream()
                .map(this::toShortDtoConvert)
                .collect(Collectors.toList());
    }

    public List<QuestionOption> toListConvert(List<QuestionOptionDto> questionOptionDtoList) {
        return questionOptionDtoList
                .stream()
                .map(this::toQuestionOption)
                .collect(Collectors.toList());
    }
}
