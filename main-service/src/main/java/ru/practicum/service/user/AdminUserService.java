package ru.practicum.service.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.dto.user.NewUserDto;

import java.util.List;

public interface AdminUserService {
    ResponseEntity<?> createUserByAdmin(NewUserDto user);

    ResponseEntity<?> getUsersByAdmin(Integer from, Integer size, List<Integer> ids);

    ResponseEntity<HttpStatus> deleteUserByAdmin(Integer userId);
}
