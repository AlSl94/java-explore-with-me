package ru.practicum.ewm.user;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static UserShortDto toShortUserDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static List<UserDto> toUserDtoList(Iterable<User> users) {
        List<UserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(toUserDto(user));
        }
        return result;
    }
}