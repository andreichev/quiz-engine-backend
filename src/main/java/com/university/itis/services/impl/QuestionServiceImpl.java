package com.university.itis.services.impl;

import com.university.itis.dto.QuestionDto;
import com.university.itis.mapper.QuestionMapper;
import com.university.itis.model.Question;
import com.university.itis.model.User;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.services.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;

    @Override
    public QuestionDto saveQuestion(QuestionDto form, User user) {
        Question questionToSave = questionMapper.toQuestion(form);
        Question savedQuestion = questionRepository.save(questionToSave);
        return questionMapper.toDtoConvert(savedQuestion);
    }
}
