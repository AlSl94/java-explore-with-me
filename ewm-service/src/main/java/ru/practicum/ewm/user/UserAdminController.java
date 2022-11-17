package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    @GetMapping
    public List<UserDto> getUsers(@RequestParam Long ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @PostMapping
    public UserDto createUser(UserShortDto dto) {
        return null;
    }

    @DeleteMapping(value = "/{userId}")
    public void delete(@PathVariable Long userId) {

    }
}
