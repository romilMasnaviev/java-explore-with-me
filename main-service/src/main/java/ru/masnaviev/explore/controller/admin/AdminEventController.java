package ru.masnaviev.explore.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.event.EventFullDto;
import ru.masnaviev.explore.dto.event.EventUpdateAdminRequest;
import ru.masnaviev.explore.model.State;
import ru.masnaviev.explore.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/events")
@Validated
public class AdminEventController {

    private static final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    private EventService service;

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventInfoAndStatus(@PathVariable @Min(0) Integer eventId,
                                                 @RequestBody @Valid EventUpdateAdminRequest updateRequest) {
        return service.updateEventInfoAndStatus(eventId, updateRequest);
    }

    @GetMapping()
    public List<EventFullDto> getEvents(@RequestParam(name = "users", required = false) List<Integer> users,
                                        @RequestParam(name = "states", required = false) List<State> states,
                                        @RequestParam(name = "categories", required = false) List<Integer> categories,
                                        @RequestParam(name = "rangeStart", required = false)
                                        @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false)
                                        @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime rangeEnd,
                                        @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size) {
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

}
