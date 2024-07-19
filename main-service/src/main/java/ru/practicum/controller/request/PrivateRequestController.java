package ru.practicum.controller.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.service.RequestService;

import java.util.List;

/**
 * Приватный Контроллер для {@link ru.practicum.model.Request}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateRequestController {

    private final RequestService service;

    /**
     * Создание запроса на участие в событии.
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @return DTO запроса на участие
     */
    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipantRequestDto> createRequestPrivate(@PathVariable(name = "userId") Integer userId,
                                                                      @RequestParam(name = "eventId") Integer eventId) {
        return service.createRequestPrivate(userId, eventId);
    }

    /**
     * Получение списка запросов на участие пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список DTO запросов на участие
     */
    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipantRequestDto>> getRequestPrivate(@PathVariable(name = "userId") Integer userId) {
        return service.getRequestPrivate(userId);
    }

    /**
     * Отмена запроса на участие.
     *
     * @param userId    идентификатор пользователя
     * @param requestId идентификатор запроса
     * @return DTO отмененного запроса
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipantRequestDto> cancelRequestPrivate(@PathVariable(name = "userId") Integer userId,
                                                                      @PathVariable(name = "requestId") Integer requestId) {
        return service.cancelRequestPrivate(userId, requestId);
    }
}
