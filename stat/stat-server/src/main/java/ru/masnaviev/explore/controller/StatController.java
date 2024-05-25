package ru.masnaviev.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.masnaviev.explore.dto.StatEntityPostDto;
import ru.masnaviev.explore.service.StatService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {

    private final StatService service;
    @PostMapping
    public StatEntityPostDto create(@RequestBody StatEntityPostDto request){
        log.info("StatController. Create request, StatEntityPostDto = {}",request);
        return service.create(request);
    }

}
