package ru.practicum.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.NewUserDto;
import ru.practicum.dto.user.UserDto;
import ru.practicum.model.User;
import ru.practicum.service.user.AdminUserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Админ Контроллер для {@link User}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Validated
public class AdminUserController {

    private final AdminUserService service;

    /**
     * Создание нового пользователя.
     *
     * @param user информация о новом пользователе
     * @return ответ с созданным пользователем
     */
    @PostMapping
    public ResponseEntity<UserDto> createUserByAdmin(@RequestBody @Valid NewUserDto user) {
        return new ResponseEntity<>(service.createUserByAdmin(user), HttpStatus.CREATED);
    }

    /**
     * Получение списка пользователей с возможностью фильтрации и пагинации.
     *
     * @param from начальный индекс для пагинации
     * @param size количество пользователей на странице
     * @param ids  список идентификаторов пользователей для фильтрации
     * @return список пользователей
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsersByAdmin(@RequestParam(name = "from", required = false, defaultValue = "0") @Min(value = 0) Integer from,
                                                         @RequestParam(name = "size", required = false, defaultValue = "10") @Min(value = 1) Integer size,
                                                         @RequestParam(name = "ids", required = false) List<Integer> ids) {
        return new ResponseEntity<>(service.getUsersByAdmin(from, size, ids), HttpStatus.OK);
    }

    /**
     * Удаление пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return ответ с кодом статуса
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUserByAdmin(@PathVariable Integer userId) {
        return new ResponseEntity<>(service.deleteUserByAdmin(userId));
    }
}
