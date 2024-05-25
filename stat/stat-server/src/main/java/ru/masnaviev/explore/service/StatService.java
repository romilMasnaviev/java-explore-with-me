package ru.masnaviev.explore.service;

import ru.masnaviev.explore.dto.StatEntityGetRequest;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;

import java.util.List;

public interface StatService {
    StatEntityPostRequest create(StatEntityPostRequest request);

    List<StatEntityGetResponse> get(StatEntityGetRequest request);
}
