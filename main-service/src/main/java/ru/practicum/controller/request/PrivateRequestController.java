package ru.practicum.controller.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.model.Request;
import ru.practicum.service.request.PrivateRequestService;

import java.util.List;

/**
 * Приватный Контроллер для {@link Request}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateRequestController {

    private final PrivateRequestService service;

    /**
     * Создание запроса на участие в событии.
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @return DTO запроса на участие
     */
    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipantRequestDto> createRequestPrivate(@PathVariable Integer userId,
                                                                      @RequestParam Integer eventId) {
        return new ResponseEntity<>(service.createRequestPrivate(userId, eventId), HttpStatus.CREATED);
    }

    /**
     * Получение списка запросов на участие пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список DTO запросов на участие
     */
    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipantRequestDto>> getRequestPrivate(@PathVariable Integer userId) {
        return new ResponseEntity<>(service.getRequestPrivate(userId), HttpStatus.OK);
    }

    /**
     * Отмена запроса на участие.
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса
     * @return DTO отмененного запроса
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipantRequestDto> cancelRequestPrivate(@PathVariable Integer userId,
                                                                      @PathVariable Integer requestId) {
        return new ResponseEntity<>(service.cancelRequestPrivate(userId, requestId), HttpStatus.OK);
    }
}
