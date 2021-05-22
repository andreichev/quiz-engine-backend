package com.university.itis.mapper;

import com.university.itis.dto.UserDto;
import com.university.itis.model.Role;
import com.university.itis.model.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public UserDto toViewDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail( user.getEmail() );
        Set<Role> set = user.getRoles();
        if ( set != null ) {
            userDto.setRoles(new HashSet<>(set) );
        }
        userDto.setPhone( user.getPhone() );
        userDto.setActive( user.isActive() );
        return userDto;
    }

    public List<UserDto> toListDtoConvert(List<User> users) {
        return users
                .stream()
                .map(this::toViewDto)
                .collect(Collectors.toList());
    }
}
