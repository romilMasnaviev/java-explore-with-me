package ru.practicum.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.practicum.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PrivateEventController {

    private final EventService service;

    @PostMapping("/{userId}/events")
    public ResponseEntity<EventFullDto> createEvent(@PathVariable(name = "userId") Integer userId,
                                                    @RequestBody @Valid NewEventDto newEvent) {
        return service.createEvent(userId, newEvent);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable @Min(value = 0) Integer userId,
                                 @PathVariable(name = "eventId") Integer eventId) {
        return service.getEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getUserEvents(@PathVariable @Min(value = 0) Integer userId,
                                             @RequestParam(name = "from", defaultValue = "0") @Min(value = 0) Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") @Min(value = 1) Integer size) {
        return service.getUserEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable(name = "eventId") Integer eventId,
                                          @PathVariable(name = "userId") Integer userId,
                                          @RequestBody @Valid EventUpdateRequest updateRequest) {
        return service.updateEventByUser(eventId, userId, updateRequest);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipantRequestDto> getUserEventRequests(@PathVariable(name = "eventId") Integer eventId,
                                                            @PathVariable(name = "userId") Integer userId) {
        return service.getUserEventRequests(eventId, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public RequestStatusUpdateResult changeEventRequestsStatus(@PathVariable(name = "eventId") Integer eventId,
                                                               @PathVariable(name = "userId") Integer userId,
                                                               @RequestBody EventRequestStatusUpdateRequest request) {
        return service.changeEventRequestsStatus(eventId, userId, request);
    }

}
