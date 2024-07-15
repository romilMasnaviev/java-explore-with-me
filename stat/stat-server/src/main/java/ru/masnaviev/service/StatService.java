package ru.masnaviev.service;

import ru.masnaviev.dto.StatEntityGetResponse;
import ru.masnaviev.dto.StatEntityPostRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    StatEntityPostRequest create(StatEntityPostRequest request);

    List<StatEntityGetResponse> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
