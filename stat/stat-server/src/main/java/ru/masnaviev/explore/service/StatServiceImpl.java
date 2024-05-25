package ru.masnaviev.explore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.StatEntityConverter;
import ru.masnaviev.explore.dao.StatEntityRepository;
import ru.masnaviev.explore.dto.StatEntityGetRequest;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;
import ru.masnaviev.explore.model.StatEntity;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatEntityRepository repository;
    private final StatEntityConverter converter;

    @Override
    public StatEntityPostRequest create(StatEntityPostRequest request) {
        log.info("StatServiceImpl. create method, request = {}", request);
        StatEntity newStatEntity = converter.postDtoConvertToStatEntity(request);
        StatEntity savedStatEntity = repository.save(newStatEntity);
        return converter.statEntityConvertToPostDto(savedStatEntity);
    }

    @Override
    public List<StatEntityGetResponse> get(StatEntityGetRequest request) {
        log.info("StatServiceImpl. get method, request = {}", request);
        List<StatEntity> entities = new ArrayList<>();
        if (request.isUnique()) {
            entities = repository.getDistinctByParameters(request.getStart(), request.getEnd(), request.getUris());
        } else {
            entities = repository.getByParameters(request.getStart(), request.getEnd(), request.getUris());
        }
        return converter.statEntityConvertToGetResponse(entities);

    }
}
