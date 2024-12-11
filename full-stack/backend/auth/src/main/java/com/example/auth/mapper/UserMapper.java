package com.example.auth.mapper;

import com.example.auth.dto.UserDto;
import com.example.auth.entity.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getFirstName(),
                user.getRole()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getRole()
        );
    }
}
