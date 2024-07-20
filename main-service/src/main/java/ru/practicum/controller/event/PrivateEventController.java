package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.EventUpdateRequest;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.dto.request.RequestStatusUpdateResult;
import ru.practicum.model.Event;
import ru.practicum.service.event.PrivateEventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Приватный Контроллер для {@link Event}
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateEventController {

    private final PrivateEventService service;

    /**
     * Создание события пользователем.
     *
     * @param userId   идентификатор пользователя
     * @param newEvent данные о новом событии
     * @return созданное событие
     */
    @PostMapping("/{userId}/events")
    public ResponseEntity<EventFullDto> createEventPrivate(@PathVariable Integer userId,
                                                           @RequestBody @Valid NewEventDto newEvent) {
        return new ResponseEntity<>(service.createEventPrivate(userId, newEvent), HttpStatus.CREATED);
    }

    /**
     * Получение события по идентификатору пользователя и события.
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @return событие
     */
    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventPrivate(@PathVariable Integer userId,
                                        @PathVariable Integer eventId) {
        return service.getEventPrivate(userId, eventId);
    }

    /**
     * Получение списка событий пользователя.
     *
     * @param userId идентификатор пользователя
     * @param from   индекс начала выборки
     * @param size   размер выборки
     * @return список событий
     */
    @GetMapping("/{userId}/events")
    public List<EventShortDto> getUserEventsPrivate(@PathVariable Integer userId,
                                                    @RequestParam(name = "from", defaultValue = "0") @Min(value = 0) Integer from,
                                                    @RequestParam(name = "size", defaultValue = "10") @Min(value = 1) Integer size) {
        return service.getUserEventsPrivate(userId, from, size);
    }

    /**
     * Обновление события пользователем.
     *
     * @param userId        идентификатор пользователя
     * @param eventId       идентификатор события
     * @param updateRequest данные для обновления события
     * @return обновленное событие
     */
    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEventByUserPrivate(@PathVariable Integer eventId,
                                                 @PathVariable Integer userId,
                                                 @RequestBody @Valid EventUpdateRequest updateRequest) {
        return service.updateEventByUserPrivate(eventId, userId, updateRequest);
    }

    /**
     * Получение запросов на участие в событии.
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @return список запросов на участие
     */
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipantRequestDto> getUserEventRequestsPrivate(@PathVariable Integer eventId,
                                                                   @PathVariable Integer userId) {
        return service.getUserEventRequestsPrivate(eventId, userId);
    }

    /**
     * Обновление статуса запросов на участие в событии.
     *
     * @param userId  идентификатор пользователя
     * @param eventId идентификатор события
     * @param request данные для обновления статуса запросов
     * @return результат обновления статусов запросов
     */
    @PatchMapping("/{userId}/events/{eventId}/requests")
    public RequestStatusUpdateResult changeEventRequestsStatusPrivate(@PathVariable Integer eventId,
                                                                      @PathVariable Integer userId,
                                                                      @RequestBody EventRequestStatusUpdateRequest request) {
        return service.changeEventRequestsStatusPrivate(eventId, userId, request);
    }
}
