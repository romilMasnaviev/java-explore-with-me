package ru.masnaviev.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.masnaviev.dto.StatEntityGetResponse;
import ru.masnaviev.dto.StatEntityPostRequest;
import ru.masnaviev.model.StatEntity;
import ru.masnaviev.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для {@link StatEntity}
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {

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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получение сущности статистики
     */
    @GetMapping("/stats")
    List<StatEntityGetResponse> get(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                    @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                    @RequestParam(name = "uris", required = false) List<String> uris,
                                    @RequestParam(name = "unique", required = false, defaultValue = "false") boolean unique) {
        log.debug("StatController. Get request, Get method, start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
        return service.get(start, end, uris, unique);
    }
}
