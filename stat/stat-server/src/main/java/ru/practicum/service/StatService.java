package ru.practicum.service;


import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    StatEntityPostRequest create(StatEntityPostRequest request);

    List<StatEntityGetResponse> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
