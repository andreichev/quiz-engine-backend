package com.university.itis.services.impl;

import com.university.itis.dto.QuestionDto;
import com.university.itis.dto.QuestionOptionDto;
import com.university.itis.mapper.QuestionOptionMapper;
import com.university.itis.model.User;
import com.university.itis.repository.QuestionOptionRepository;
import com.university.itis.services.QuestionOptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionOptionServiceImpl implements QuestionOptionService {
    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionOptionMapper questionOptionMapper;

    @Override
    public List<QuestionOptionDto> getAllByQuestionId(Long questionId) {
        return questionOptionMapper.toListDtoConvert(questionOptionRepository.findAllByQuestionId(questionId));
    }

    @Override
    public QuestionOptionDto save(Long questionId, QuestionOptionDto form, User user) {
        return null;
    }

    @Override
    public QuestionOptionDto update(Long questionId, Long questionOptionId, QuestionOptionDto form, User user) {
        return null;
    }

    @Override
    public QuestionDto getById(Long questionId, Long questionOptionId) {
        return null;
    }

    @Override
    public void delete(Long questionId, Long questionOptionId, User user) {

    }
}
