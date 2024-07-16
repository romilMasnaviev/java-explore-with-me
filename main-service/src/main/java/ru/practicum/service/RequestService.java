package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.converter.RequestConverter;
import ru.practicum.dao.EventRepository;
import ru.practicum.dao.RequestRepository;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.handler.CustomException;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.enums.State;
import ru.practicum.model.enums.Status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RequestService {

    private RequestRepository repository;
    private RequestConverter converter;

    private EventRepository eventRepository;

    public ResponseEntity<ParticipantRequestDto> createRequest(Integer userId, Integer eventId) {
        log.debug("Добавление запроса от текущего пользователя на участие в событии, userId = {}, eventId = {}", userId, eventId);
        Event event = eventRepository.getReferenceById(eventId);

        checkNewRequestValidData(userId, eventId, event);

        Request request = new Request();
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            request.setStatus(Status.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else {
            request.setStatus(Status.PENDING);
        }
        request.setCreated(LocalDateTime.now());
        request.setEventId(eventId);
        request.setUserId(userId);

        Request savedRequest = repository.save(request);

        eventRepository.save(event);

        ParticipantRequestDto requestDto = converter.requestConvertToParticipantRequestDto(savedRequest);

        return new ResponseEntity<>(requestDto, HttpStatus.CREATED);
    }

    public ResponseEntity<List<ParticipantRequestDto>> getRequest(Integer userId) {
        log.debug("Получение информации о заявках текущего пользователя на участие в чужих событиях, userId = {}", userId);
        List<Request> requests = repository.findByUserId(userId);
        return new ResponseEntity<>(converter.requestConvertToParticipantRequestDto(requests), HttpStatus.OK);
    }

    public ResponseEntity<ParticipantRequestDto> cancelRequest(Integer userId, Integer requestId) {
        log.debug("Отмена своего запроса на участие в событии, userId = {}, requestId = {}", userId, requestId);
        Request request = repository.findByIdAndUserId(requestId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Запрос не найден"));

        if (request.getStatus() == Status.CONFIRMED) {
            Event event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Событие не найдено"));
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }

        request.setStatus(Status.CANCELED);
        repository.save(request);

        return new ResponseEntity<>(converter.requestConvertToParticipantRequestDto(request), HttpStatus.OK);
    }

    private void checkNewRequestValidData(Integer userId, Integer eventId, Event event) {

        if (repository.existsByUserIdAndEventId(userId, eventId)) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно добавить запрос",
                    "Запрос уже существует",
                    Collections.emptyList());
        }
        if (userId == event.getInitiator().getId()) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно добавить запрос",
                    "Вы являетесь организатором события",
                    Collections.emptyList());
        }
        if (event.getState() != State.PUBLISHED) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно добавить запрос",
                    "Событие еще не опубликовано",
                    Collections.emptyList());
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно добавить запрос",
                    "Достигнут лимит запросов на участие",
                    Collections.emptyList());
        }
    }
}
