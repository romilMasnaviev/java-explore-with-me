package ru.masnaviev.explore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.UserConverter;
import ru.masnaviev.explore.dao.UserRepository;
import ru.masnaviev.explore.dto.UserCreateDto;
import ru.masnaviev.explore.dto.request.UserDto;
import ru.masnaviev.explore.model.User;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;//TODO почему-то ругается, хотя работает

    public UserDto createUser(UserCreateDto userCreateDto) {
        log.debug("Создание пользователя {}", userCreateDto);
        User newUser = userConverter.userCreateDtoConvertToUser(userCreateDto);
        User user = userRepository.save(newUser);
        return userConverter.userConvertToUserDto(user);
    }

    public List<UserDto> getUsers(Integer from, Integer size, List<Integer> ids) {
        log.debug("Получение пользователей, from = {}, from = {}, ids = {}", from, size, ids);
        if (ids != null) {
            return userConverter.userConvertToUserDto(userRepository.findAllById(ids));
        } else if (from != null && size != null) {
            return userConverter.userConvertToUserDto(userRepository.findAllUsers(from, size));
        }
        throw new ValidationException();
    }

    public ResponseEntity<HttpStatus> deleteUser(Integer userId) {
        log.debug("Удаление пользователя, id = {}", userId);
        userRepository.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
