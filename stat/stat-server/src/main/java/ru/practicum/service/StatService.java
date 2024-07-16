package ru.practicum.service;


import org.springframework.http.ResponseEntity;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    ResponseEntity<StatEntityPostRequest> create(StatEntityPostRequest request);

    List<StatEntityGetResponse> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
