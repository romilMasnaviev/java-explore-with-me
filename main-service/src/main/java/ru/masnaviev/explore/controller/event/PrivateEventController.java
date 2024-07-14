package ru.masnaviev.explore.controller.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.event.EventFullDto;
import ru.masnaviev.explore.dto.event.EventShortDto;
import ru.masnaviev.explore.dto.event.EventUpdateRequest;
import ru.masnaviev.explore.dto.event.NewEventDto;
import ru.masnaviev.explore.dto.request.EventRequestStatusUpdateRequest;
import ru.masnaviev.explore.dto.request.ParticipantRequestDto;
import ru.masnaviev.explore.dto.request.RequestStatusUpdateResult;
import ru.masnaviev.explore.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
@Validated
public class PrivateEventController {

    private EventService service;

    @PostMapping("/{userId}/events")
    public ResponseEntity<EventFullDto> createEvent(@PathVariable(name = "userId") @Min(value = 0) Integer userId,
                                                    @RequestBody @Valid NewEventDto newEvent) {
        return service.createEvent(userId, newEvent);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable @Min(value = 0) Integer userId,
                                 @PathVariable(name = "eventId") @Min(value = 0) Integer eventId) {
        return service.getEventPublic(userId, eventId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getUserEvents(@PathVariable @Min(value = 0) Integer userId,
                                             @RequestParam(name = "from", defaultValue = "0") @Min(value = 0) Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") @Min(value = 1) Integer size) {
        return service.getUserEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable(name = "eventId") @Min(value = 0) Integer eventId,
                                          @PathVariable(name = "userId") @Min(value = 0) Integer userId,
                                          @RequestBody @Valid EventUpdateRequest updateRequest) {
        return service.updateEventByUser(eventId, userId, updateRequest);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipantRequestDto> getUserEventRequests(@PathVariable(name = "eventId") @Min(value = 0) Integer eventId,
                                                            @PathVariable(name = "userId") @Min(value = 0) Integer userId) {
        return service.getUserEventRequests(eventId, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public RequestStatusUpdateResult changeEventRequestsStatus(@PathVariable(name = "eventId") @Min(value = 0) Integer eventId,
                                                               @PathVariable(name = "userId") @Min(value = 0) Integer userId,
                                                               @RequestBody EventRequestStatusUpdateRequest request) {
        return service.changeEventRequestsStatus(eventId, userId, request);
    }

}
