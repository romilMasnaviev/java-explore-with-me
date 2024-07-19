package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.model.Event;
import ru.practicum.model.enums.EventSort;
import ru.practicum.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.conf.Configuration.LOCAL_DATE_TIME_PATTERN;

/**
 * Публичный Контроллер для {@link Event}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class PublicEventController {

    private final EventService service;

    /**
     * Получение списка событий с возможностью фильтрации и сортировки.
     *
     * @param text               текст для поиска в названии и описании
     * @param categories         список идентификаторов категорий для фильтрации
     * @param paid               фильтрация по статусу оплаты
     * @param rangeStart         начало диапазона дат
     * @param rangeEnd           конец диапазона дат
     * @param onlyAvailable      фильтрация только доступных событий
     * @param sort               способ сортировки событий
     * @param from               индекс начала страницы
     * @param size               размер страницы
     * @param httpServletRequest HTTP запрос для сбора информации о запросе
     * @return список событий, соответствующих критериям
     */
    @GetMapping
    public List<EventFullDto> getEventsPublic(@RequestParam(name = "text", required = false) String text,
                                              @RequestParam(name = "categories", required = false) List<Integer> categories,
                                              @RequestParam(name = "paid", required = false) Boolean paid,
                                              @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                              @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                              @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                                              @RequestParam(name = "sort", required = false) EventSort sort,
                                              @RequestParam(name = "from", defaultValue = "0") @Min(value = 0) Integer from,
                                              @RequestParam(name = "size", defaultValue = "10") @Min(value = 1) Integer size,
                                              HttpServletRequest httpServletRequest) {
        return service.getEventsPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, httpServletRequest);
    }

    /**
     * Получение детальной информации о событии по его идентификатору.
     *
     * @param eventId            идентификатор события
     * @param httpServletRequest HTTP запрос для сбора информации о запросе
     * @return информация о событии
     */
    @GetMapping("/{id}")
    public EventFullDto getEventPublic(@PathVariable(name = "id") Integer eventId,
                                       HttpServletRequest httpServletRequest) {
        return service.getEventPublic(eventId, httpServletRequest);
    }
}
