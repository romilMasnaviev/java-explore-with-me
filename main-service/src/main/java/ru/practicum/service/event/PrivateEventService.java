package ru.practicum.service.event;

import org.springframework.http.ResponseEntity;
import ru.practicum.dto.event.EventFullDto;
import ru.practicum.dto.event.EventShortDto;
import ru.practicum.dto.event.EventUpdateRequest;
import ru.practicum.dto.event.NewEventDto;
import ru.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.dto.request.RequestStatusUpdateResult;

import java.util.List;

public interface PrivateEventService {
    ResponseEntity<EventFullDto> createEventPrivate(Integer userId, NewEventDto newEvent);

    EventFullDto getEventPrivate(Integer userId, Integer eventId);

    List<EventShortDto> getUserEventsPrivate(Integer userId, Integer from, Integer size);

    EventFullDto updateEventByUserPrivate(Integer eventId, Integer userId, EventUpdateRequest updateRequest);

    List<ParticipantRequestDto> getUserEventRequestsPrivate(Integer eventId, Integer userId);

    RequestStatusUpdateResult changeEventRequestsStatusPrivate(Integer eventId, Integer userId, EventRequestStatusUpdateRequest request);
}
