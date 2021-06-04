package com.university.itis.mapper;

import com.university.itis.dto.QuestionAnswerDto;
import com.university.itis.model.QuestionAnswer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionAnswerMapper {
    private final QuestionMapper questionMapper;
    private final QuestionOptionMapper questionOptionMapper;

    public QuestionAnswerDto toDtoConvert(QuestionAnswer questionAnswer) {
        return QuestionAnswerDto.builder()
                .id(questionAnswer.getId())
                .question(questionMapper.toDtoConvert(questionAnswer.getQuestion()))
                .option(questionOptionMapper.toDtoConvert(questionAnswer.getOption()))
                .build();
    }

    public List<QuestionAnswerDto> toListDtoConvert(List<QuestionAnswer> questionAnswerList) {
        return questionAnswerList
                .stream()
                .map(this::toDtoConvert)
                .collect(Collectors.toList());
    }
}
