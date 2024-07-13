package ru.masnaviev.explore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.UserConverter;
import ru.masnaviev.explore.dao.UserRepository;
import ru.masnaviev.explore.dto.user.NewUserDto;
import ru.masnaviev.explore.dto.user.UserDto;
import ru.masnaviev.explore.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;//TODO почему-то ругается, хотя работает

    public ResponseEntity<?> createUser(NewUserDto newUser) {
        log.debug("Создание пользователя {}", newUser);
        User user = userConverter.newUserDtoConvertToUser(newUser);
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(userConverter.userConvertToUserDto(savedUser),HttpStatus.CREATED);
    }

    public ResponseEntity<List<UserDto>> getUsers(Integer from, Integer size, List<Integer> ids) {
        log.debug("Получение пользователей, from = {}, from = {}, ids = {}", from, size, ids);
        if (ids != null) {
            return new ResponseEntity<>(userConverter.userConvertToUserDto(userRepository.findAllById(ids)),HttpStatus.OK);
        }
        return new ResponseEntity<>(userConverter.userConvertToUserDto(userRepository.findAllUsers(from, size)),HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteUser(Integer userId) {
        log.debug("Удаление пользователя, id = {}", userId);
        userRepository.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
