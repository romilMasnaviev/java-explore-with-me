package ru.masnaviev.explore.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.converter.RequestConverter;
import ru.masnaviev.explore.dao.EventRepository;
import ru.masnaviev.explore.dao.RequestRepository;
import ru.masnaviev.explore.dto.request.ParticipantRequestDto;
import ru.masnaviev.explore.handler.CustomException;
import ru.masnaviev.explore.model.Event;
import ru.masnaviev.explore.model.Request;
import ru.masnaviev.explore.model.State;
import ru.masnaviev.explore.model.Status;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class RequestService {

    private RequestRepository repository;
    private RequestConverter converter;

    private EventRepository eventRepository;

    public ParticipantRequestDto createRequest(Integer userId, Integer eventId) {
        log.debug("Добавление запроса от текущего пользователя на участие в событии, userId = {}, eventId = {}", userId, eventId);
        Event event = eventRepository.getReferenceById(eventId);

        checkNewRequestValidData(userId, eventId, event);
        Request request = new Request();
        if (!event.isRequestModeration()) {
            request.setStatus(Status.CONFIRMED);
        } else {
            request.setStatus(Status.PENDING);
        }
        request.setCreated(LocalDateTime.now());
        request.setEventId(eventId);
        request.setUserId(userId);

        return converter.RequestConvertToParticipantRequestDto(repository.save(request));
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
        if (event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно добавить запрос",
                    "Достигнут лимит запросов на участие",
                    Collections.emptyList());
        }
    }
}
