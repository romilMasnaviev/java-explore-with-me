package ru.masnaviev.explore.controller.priv;

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
    public ResponseEntity<EventFullDto> createEvent(@PathVariable Integer userId, @RequestBody @Valid NewEventDto newEvent) {
        return service.createEvent(userId, newEvent);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable @Min(0) Integer userId, @PathVariable @Min(0) Integer eventId) {
        return service.getEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getUserEvents(@PathVariable @Min(0) Integer userId,
                                             @RequestParam(name = "from", defaultValue = "0") @Min(0) Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") @Min(1) Integer size) {
        return service.getUserEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    //TODO протестить
    //TODO что изменить можно только отмененные события или события в состоянии ожидания модерации (Ожидается код ошибки 409)
    public EventFullDto updateEventByUser(@PathVariable @Min(0) Integer eventId,
                                          @PathVariable @Min(0) Integer userId,
                                          @RequestBody @Valid EventUpdateRequest updateRequest) {
        return service.updateEventByUser(eventId, userId, updateRequest);
    }

    @GetMapping("/{userId}/events/{eventId}/requests") //TODO проверить работоспособность
    public List<ParticipantRequestDto> getUserEventRequests(@PathVariable @Min(0) Integer eventId,
                                                            @PathVariable @Min(0) Integer userId) {
        return service.getUserEventRequests(eventId, userId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests") //TODO проверить работоспособность
    public RequestStatusUpdateResult changeEventRequestsStatus(@PathVariable @Min(0) Integer eventId,
                                                               @PathVariable @Min(0) Integer userId,
                                                               @RequestBody EventRequestStatusUpdateRequest request) {
        return service.changeEventRequestsStatus(eventId, userId, request);
    }

}
