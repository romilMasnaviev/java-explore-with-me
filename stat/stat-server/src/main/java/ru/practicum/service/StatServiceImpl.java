package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.converter.StatEntityConverter;
import ru.practicum.dao.StatEntityRepository;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;
import ru.practicum.model.StatEntity;

import java.time.LocalDateTime;
import java.util.List;

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
    @Transactional
    public ResponseEntity<StatEntityPostRequest> create(StatEntityPostRequest request) {
        log.debug("StatServiceImpl. Create method, request = {}", request);
        StatEntity newStatEntity = converter.postDtoConvertToStatEntity(request);
        StatEntity savedStatEntity = repository.save(newStatEntity);
        return new ResponseEntity<>(converter.statEntityConvertToPostDto(savedStatEntity), HttpStatus.CREATED);
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
