package ru.practicum.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.UserRepository;
import ru.practicum.dto.converter.UserConverter;
import ru.practicum.dto.user.NewUserDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements AdminUserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public ResponseEntity<?> createUserByAdmin(NewUserDto newUser) {
        log.debug("Создание пользователя {}", newUser);
        User user = userConverter.newUserDtoConvertToUser(newUser);
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(userConverter.userConvertToUserDto(savedUser), HttpStatus.CREATED);
    }

    public ResponseEntity<List<UserDto>> getUsersByAdmin(Integer from, Integer size, List<Integer> ids) {
        log.debug("Получение пользователей, from = {}, from = {}, ids = {}", from, size, ids);
        if (ids != null) {
            return new ResponseEntity<>(userConverter.userConvertToUserDto(userRepository.findAllById(ids)), HttpStatus.OK);
        }
        Pageable pageable = PageRequest.of(from, size);
        return new ResponseEntity<>(userConverter.userConvertToUserDto(userRepository.findAll(pageable).toList()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteUserByAdmin(Integer userId) {
        log.debug("Удаление пользователя, id = {}", userId);
        userRepository.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
