package ru.masnaviev.explore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;
import ru.masnaviev.explore.model.StatEntity;
import ru.masnaviev.explore.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Контроллер для {@link StatEntity}
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Stats", description = "Управление статистикой")
public class StatController {

    private final StatService service;

    /**
     * Создание сущности статистики
     */
    @PostMapping("/hit")
    @Operation(summary = "Создание сущности статистики")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid StatEntityPostRequest request) {
        log.debug("StatController. Post request, StatEntityPostDto = {}", request);
        try {
            service.create(request);
        } catch (Exception ex) {
            log.error("StatController. Post request, Exception ={}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Получение сущности статистики
     */
    @GetMapping("/stats")
    @Operation(summary = "Получение сущности статистики")
    List<StatEntityGetResponse> get(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                    @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                    @RequestParam(name = "uris", required = false) List<String> uris,
                                    @RequestParam(name = "unique", required = false, defaultValue = "false") boolean unique) {
        try {
            log.debug("StatController. Get request, Get method, start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
            return service.get(start, end, uris, unique);
        } catch (Exception ex) {
            log.error("StatController. Get request, Exception ={}", ex.getMessage());
            throw new RuntimeException();
        }
    }
}
