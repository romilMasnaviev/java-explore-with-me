package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventUpdateAdminRequest;
import ru.practicum.model.Event;
import ru.practicum.model.enums.State;
import ru.practicum.service.event.AdminEventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.conf.Configuration.LOCAL_DATE_TIME_PATTERN;

/**
 * Админ Контроллер для {@link Event}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {

    private final AdminEventService service;

    /**
     * Обновление информации и статуса события.
     *
     * @param eventId       идентификатор события
     * @param updateRequest данные для обновления события
     * @return обновленное событие
     */
    @PatchMapping("/{eventId}")
    public EventFullDto updateEventInfoAndStatusByAdmin(@PathVariable Integer eventId,
                                                        @RequestBody @Valid EventUpdateAdminRequest updateRequest) {
        return service.updateEventInfoAndStatusByAdmin(eventId, updateRequest);
    }

    /**
     * Получение списка событий с фильтрацией.
     *
     * @param users      список идентификаторов пользователей
     * @param states     список состояний событий
     * @param categories список идентификаторов категорий
     * @param rangeStart начальная дата и время диапазона
     * @param rangeEnd   конечная дата и время диапазона
     * @param from       индекс начала выборки
     * @param size       размер выборки
     * @return список событий
     */
    @GetMapping
    public List<EventFullDto> getEventsByAdmin(@RequestParam(name = "users", required = false) List<Integer> users,
                                               @RequestParam(name = "states", required = false) List<State> states,
                                               @RequestParam(name = "categories", required = false) List<Integer> categories,
                                               @RequestParam(name = "rangeStart", required = false)
                                               @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                               @RequestParam(name = "rangeEnd", required = false)
                                               @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                               @RequestParam(name = "from", required = false, defaultValue = "0") @Min(value = 0) Integer from,
                                               @RequestParam(name = "size", required = false, defaultValue = "10") @Min(value = 1) Integer size) {
        return service.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}
