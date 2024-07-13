package ru.masnaviev.explore.controller.pub;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.event.EventFullDto;
import ru.masnaviev.explore.model.Sort;
import ru.masnaviev.explore.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
@Validated
public class PublicEventController {

    private static final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    private EventService service;

    @GetMapping()
    public List<EventFullDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                        @RequestParam(name = "categories", required = false) List<Integer> categories,
                                        @RequestParam(name = "paid", required = false) Boolean paid,
                                        @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime rangeEnd,
                                        @RequestParam(name = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
                                        @RequestParam(name = "sort", required = false) Sort sort,
                                        @RequestParam(name = "from", required = false, defaultValue = "0") @Min(0) Integer from,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") @Min(1) Integer size,
                                        HttpServletRequest httpServletRequest) {
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, httpServletRequest);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable @Positive Integer id,
                                 HttpServletRequest httpServletRequest) {
        return service.getEvent(id, httpServletRequest);
    }
}
