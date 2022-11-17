package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.user.dao.UserDao;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utility.FromSizeRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;


    public List<UserDto> getUsers(List<Long> ids, int from, int size) {

        Pageable pageable = FromSizeRequest.of(from, size);

        List<User> users = userDao.findByIdIn(ids, pageable);

        return UserMapper.toUserDtoList(users);
    }

    @Transactional
    public UserDto createUser(UserDto dto) {
        User user = UserMapper.toUser(dto);
        user = userDao.save(user);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userDao.deleteById(userId);
    }
}
