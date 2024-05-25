package ru.masnaviev.explore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.StatEntityConverter;
import ru.masnaviev.explore.dao.StatEntityRepository;
import ru.masnaviev.explore.dto.StatEntityPostDto;
import ru.masnaviev.explore.model.StatEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatEntityRepository repository;
    private final StatEntityConverter converter;

    @Override
    public StatEntityPostDto create(StatEntityPostDto request) {
        log.info("StatServiceImpl. create request = {}", request);
        StatEntity newStatEntity = converter.postDtoConvertToStatEntity(request);
        StatEntity savedStatEntity = repository.save(newStatEntity);
        return converter.statEntityConvertToPostDto(savedStatEntity);
    }
}
