package com.university.itis.mapper;

import com.university.itis.dto.user.UserDto;
import com.university.itis.dto.user.UserShortDto;
import com.university.itis.model.Role;
import com.university.itis.model.User;
import com.university.itis.repository.QuizPassingRepository;
import com.university.itis.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMapper {
    private final ImageMapper imageMapper;
    private final QuizRepository quizRepository;
    private final QuizPassingRepository quizPassingRepository;

    public UserDto toViewDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId( user.getId() );
        userDto.setFullName( user.getFullName() );
        userDto.setEmail( user.getEmail() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userDto.setRoles(new HashSet<>(set) );
        }
        if (user.getImage() != null) {
            userDto.setAvatar(imageMapper.toDtoConvert(user.getImage()));
        }
        userDto.setIsActive( user.isActive() );
        userDto.setRegistrationDate( user.getRegistrationDate() );
        userDto.setQuizzesCount( quizRepository.countByAuthor(user) );
        userDto.setQuizzesPassedCount( quizPassingRepository.countByUser(user) );
        return userDto;
    }

    public UserShortDto toShortDto(User user) {
        UserShortDto userDto = new UserShortDto();
        userDto.setId( user.getId() );
        userDto.setFullName( user.getFullName() );
        userDto.setEmail( user.getEmail() );
        if (user.getImage() != null) {
            userDto.setAvatar(imageMapper.toDtoConvert(user.getImage()));
        }
        return userDto;
    }

    public List<UserShortDto> toListShortDtoConvert(List<User> users) {
        return users
                .stream()
                .map(this::toShortDto)
                .collect(Collectors.toList());
    }
}
