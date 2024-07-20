package ru.practicum.service.user;

import org.springframework.http.HttpStatus;
import ru.practicum.dto.user.NewUserDto;
import ru.practicum.dto.user.UserDto;

import java.util.List;

public interface AdminUserService {
    UserDto createUserByAdmin(NewUserDto user);

    List<UserDto> getUsersByAdmin(Integer from, Integer size, List<Integer> ids);

    HttpStatus deleteUserByAdmin(Integer userId);
}
