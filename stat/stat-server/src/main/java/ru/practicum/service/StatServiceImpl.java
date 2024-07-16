package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.converter.StatEntityConverter;
import ru.practicum.dao.StatEntityRepository;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;
import ru.practicum.model.StatEntity;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (start != null && end != null && start.isAfter(end)) {
            throw new ValidationException("Некорректные данные");
        }
        return repository.getStatistics(start, end, uris, unique);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
