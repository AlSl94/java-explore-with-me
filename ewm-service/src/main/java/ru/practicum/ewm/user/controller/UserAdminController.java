package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.UserService;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        List<UserDto> userDtos = userService.getUsers(ids, from, size);
        log.info("Список пользователей получен, size = {}", userDtos);
        return userDtos;
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto dto) {
        UserDto userDto = userService.createUser(dto);
        log.info("Пользователь с id {} создан", userDto.getId());
        return userDto;
    }

    @DeleteMapping(value = "/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        log.info("Пользователь с id {} удален", userId);
    }
}
