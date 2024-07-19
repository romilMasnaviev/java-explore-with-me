package ru.practicum.service.event;

import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventUpdateAdminRequest;
import ru.practicum.model.enums.State;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    EventFullDto updateEventInfoAndStatusByAdmin(Integer eventId, EventUpdateAdminRequest updateRequest);

    List<EventFullDto> getEventsByAdmin(List<Integer> users, List<State> states,
                                        List<Integer> categories, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Integer from, Integer size);
}
