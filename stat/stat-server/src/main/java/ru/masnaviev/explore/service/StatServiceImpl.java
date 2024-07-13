package ru.masnaviev.explore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.StatEntityConverter;
import ru.masnaviev.explore.dao.StatEntityRepository;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;
import ru.masnaviev.explore.model.StatEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Сервис для {@link StatEntity}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatEntityRepository repository;
    private final StatEntityConverter converter;

    /**
     * Создание сущности статистики
     *
     * @param request - объект запроса на создание статистики
     */
    @Override
    public StatEntityPostRequest create(StatEntityPostRequest request) {
        log.debug("StatServiceImpl. Create method, request = {}", request);
        StatEntity newStatEntity = converter.postDtoConvertToStatEntity(request);
        StatEntity savedStatEntity = repository.save(newStatEntity);
        return converter.statEntityConvertToPostDto(savedStatEntity);
    }

    /**
     * Получение сущности статистики
     *
     * @param start  - время, с которого считать статистику
     * @param end    - время, до которого считать статистику
     * @param uris   - список uri по которым необходима статистика
     * @param unique - считать ли только уникальные ip
     */
    @Override
    public List<StatEntityGetResponse> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.debug("StatServiceImpl. Get method, start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);
        return repository.getStatistics(start, end, uris, unique);
    }
}
