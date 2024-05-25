package ru.masnaviev.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.masnaviev.explore.dto.StatEntityGetRequest;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;
import ru.masnaviev.explore.service.StatService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private final StatService service;

    @PostMapping
    public StatEntityPostRequest create(@RequestBody @Valid StatEntityPostRequest request) { //TODO change response to http status
        log.info("StatController. Post request, StatEntityPostDto = {}", request);
        return service.create(request);
    }

    @GetMapping
    List<StatEntityGetResponse> get(@RequestBody @Valid StatEntityGetRequest request) {
        log.info("StatController. Get request, StatEntityGetRequest = {}", request);
        return service.get(request);
    }

}
