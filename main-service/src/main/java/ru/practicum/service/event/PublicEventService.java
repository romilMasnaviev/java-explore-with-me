package ru.practicum.service.event;

import ru.practicum.dto.event.EventFullDto;
import ru.practicum.model.enums.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface PublicEventService {
    List<EventFullDto> getEventsPublic(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSort sort, Integer from, Integer size, HttpServletRequest httpServletRequest);

    EventFullDto getEventPublic(Integer eventId, HttpServletRequest httpServletRequest);
}
