package com.university.itis.mapper;

import com.university.itis.dto.quiz.QuizParticipantsDto;
import com.university.itis.model.QuizPassing;
import com.university.itis.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizParticipantsMapper {
    private final UserMapper userMapper;
    private final QuizPassingMapper quizPassingMapper;

    public QuizParticipantsDto toDtoConvert(User user, List<QuizPassing> results) {
        return QuizParticipantsDto.builder()
                .user(userMapper.toShortDto(user))
                .results(quizPassingMapper.toListSummaryDtoConvert(results))
                .build();
    }
}
