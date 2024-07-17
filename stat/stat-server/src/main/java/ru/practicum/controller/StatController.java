package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;
import ru.practicum.model.StatEntity;
import ru.practicum.service.StatService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для {@link StatEntity}
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private static final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private final StatService service;

    /**
     * Создание сущности статистики
     */
    @PostMapping("/hit")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StatEntityPostRequest request) {
        log.debug("StatController. Post request, StatEntityPostDto = {}", request);
        try {
            service.create(request);
        } catch (Exception ex) {
            log.error("StatController. Post request, Exception ={}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Получение сущности статистики
     */
    @GetMapping("/stats")
    List<StatEntityGetResponse> get(@RequestParam("start") @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime start,
                                    @RequestParam("end") @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime end,
                                    @RequestParam(name = "uris", required = false) List<String> uris,
                                    @RequestParam(name = "unique", required = false, defaultValue = "false") boolean unique) {
        log.debug("StatController. Get request, Get method, start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
        if (start.isAfter(end)) {
            throw new ValidationException("Некорректные данные: start date is after end date");
        }
        return service.get(start, end, uris, unique);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
