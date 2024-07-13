package ru.masnaviev.explore.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.masnaviev.explore.client.Client;
import ru.masnaviev.explore.converter.EventConverter;
import ru.masnaviev.explore.converter.LocationConverter;
import ru.masnaviev.explore.converter.RequestConverter;
import ru.masnaviev.explore.dao.*;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;
import ru.masnaviev.explore.dto.event.*;
import ru.masnaviev.explore.dto.request.EventRequestStatusUpdateRequest;
import ru.masnaviev.explore.dto.request.ParticipantRequestDto;
import ru.masnaviev.explore.dto.request.RequestStatusUpdateResult;
import ru.masnaviev.explore.handler.CustomException;
import ru.masnaviev.explore.model.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EventService {

    private EventRepository repository;
    private EventConverter converter;

    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private RequestRepository requestRepository;

    private LocationConverter locationConverter;
    private RequestConverter requestConverter;

    private Client statClient;

    public ResponseEntity<EventFullDto> createEvent(Integer userId, NewEventDto newEvent) {
        log.debug("Создание события {}, userId = {}", newEvent, userId);
        checkNewEventValidData(newEvent, userId);
        Event event = buildEvent(newEvent, userId);

        locationRepository.save(event.getLocation());
        Event savedEvent = repository.save(event);
        return new ResponseEntity<>(converter.eventConvertToEventFullDto(savedEvent),HttpStatus.CREATED);
    }

    public EventFullDto getEvent(Integer userId, Integer eventId) {
        log.debug("Получение события, userId = {}, eventId ={}", userId, eventId);
        Event event = repository.getReferenceById(eventId);
        if (event.getInitiator().getId() != userId) {
            throw new EntityNotFoundException("Событие с id = " + eventId + "не найдено или недоступно");
        }
        return converter.eventConvertToEventFullDto(repository.getReferenceById(eventId));
    }

    public List<EventShortDto> getUserEvents(Integer userId, Integer from, Integer size) {
        log.debug("Получение событий, добавленных пользователем, user id = {}, from = {}, size = {}", userId, from, size);
        return converter.eventConvertToEventShortDto(repository.getUserEvents(from, size, userId));
    }

    public EventFullDto updateEventByUser(Integer eventId, Integer userId, EventUpdateRequest updateRequest) { //ЕЩВЩ
        log.debug("Обновление события пользователем, eventId = {}, userId = {}, request = {}", eventId, userId, updateRequest);
        Event oldEvent = repository.getReferenceById(eventId);

        checkEventUpdateRequest(oldEvent, updateRequest, userId);

        copyUpdateRequestToEvent(updateRequest, oldEvent);

        return converter.eventConvertToEventFullDto(repository.save(oldEvent));
    }

    public List<ParticipantRequestDto> getUserEventRequests(Integer eventId, Integer userId) {
        log.debug("Получение информации о запросах на участие в событии текущего пользователя," +
                "eventId = {}, userId = {}", eventId, userId);
        if (repository.getReferenceById(eventId).getInitiator().getId() != userId) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Невозможно просматривать заявки",
                    "Вы не являетесь владельцем данного события",
                    Collections.emptyList());
        }
        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return requestConverter.RequestConvertToParticipantRequestDto(requests);
    }

    public RequestStatusUpdateResult changeEventRequestsStatus(Integer eventId, Integer userId,
                                                               EventRequestStatusUpdateRequest updateRequest) {
        log.debug("Получение информации о запросах на участие в событии текущего пользователя," +
                "eventId = {}, userId = {}, updateRequest = {}", eventId, userId, updateRequest);

        if (repository.getReferenceById(eventId).getInitiator().getId() != userId) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Невозможно принимать/отклонять заявки",
                    "Вы не являетесь владельцем данного события",
                    Collections.emptyList());
        }

        Event event = repository.getReferenceById(eventId);

        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно принимать/отклонять заявки",
                    "Достигнут лимит количества заявок", Collections.emptyList());
        }

        List<ParticipantRequestDto> requests = requestConverter.RequestConvertToParticipantRequestDto(
                requestRepository.findAllByIdInAndEventId(updateRequest.getRequestIds(), eventId));

        Status newStatus = updateRequest.getStatus();
        List<ParticipantRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipantRequestDto> rejectedRequests = new ArrayList<>();

        for (ParticipantRequestDto request : requests) {
            if (!request.getStatus().equals(Status.PENDING)) {
                throw new IllegalStateException("Запрос должен иметь статус PENDING");
            }

            if (newStatus.equals(Status.CONFIRMED) &&
                    event.getParticipantLimit() != 0 &&
                    event.getConfirmedRequests() >= event.getParticipantLimit()) {
                request.setStatus(Status.REJECTED);
                rejectedRequests.add(request);
            } else {
                request.setStatus(newStatus);
                if (newStatus.equals(Status.CONFIRMED)) {
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    confirmedRequests.add(request);
                } else {
                    rejectedRequests.add(request);
                }
            }
        }

        requestRepository.saveAll(requestConverter.participantRequestDtoConvertToRequest(requests));

        if (newStatus.equals(Status.CONFIRMED) &&
                event.getConfirmedRequests() >= event.getParticipantLimit()) {
            List<ParticipantRequestDto> pendingRequests = requestConverter.RequestConvertToParticipantRequestDto
                    (requestRepository.findAllByEventIdAndStatus(eventId, Status.PENDING));
            for (ParticipantRequestDto pendingRequest : pendingRequests) {
                pendingRequest.setStatus(Status.REJECTED);
                rejectedRequests.add(pendingRequest);
            }
            requestRepository.saveAll(requestConverter.participantRequestDtoConvertToRequest(pendingRequests));
        }

        return new RequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    public EventFullDto updateEventInfoAndStatus(Integer eventId, EventUpdateAdminRequest updateRequest) {
        log.debug("Обновление события пользователем, eventId = {}, request = {}", eventId, updateRequest);
        Event oldEvent = repository.getReferenceById(eventId);

        checkEventUpdateAdminRequest(oldEvent, updateRequest);
        copyUpdateAdminRequestToEvent(updateRequest, oldEvent);

        return converter.eventConvertToEventFullDto(repository.save(oldEvent));
    }

    public List<EventFullDto> getEvents(List<Integer> users, List<State> states,
                                        List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Integer from, Integer size) {
        log.debug("Получение события по заданным фильтрам, users ={} , states = {}, categories = {}, " +
                        "rangeStart = {}, rangeEnd ={}, from = {}, size = {}", users, states, categories, rangeStart, rangeEnd,
                from, size);

        Pageable pageable = PageRequest.of(from, size);
        return converter.eventConvertToEventFullDto(repository.getAllByParameters(users, states, categories, rangeStart, rangeEnd, pageable));
    }

    public List<EventFullDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Boolean onlyAvailable, Sort sort, Integer from, Integer size, HttpServletRequest httpServletRequest) {
        log.debug("Получение события по заданным фильтрам, text = {}, categories = {}, paid = {}, " +
                        "rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        saveEndpointHit(httpServletRequest);
        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }

        if (rangeEnd.isBefore(rangeStart)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(), "Время конца меньше времени начала", "Некорректные данные", Collections.emptyList());
        }

        Pageable pageable = PageRequest.of(from, size);
        List<Event> events = repository.getAllByTextAndParameters(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);
        List<EventFullDto> eventFullDtos = converter.eventConvertToEventFullDto(events);


        return eventFullDtos;
    }

    public EventFullDto getEvent(Integer id, HttpServletRequest httpServletRequest) {
        log.debug("Получение события по id, id = {}", id);
        saveEndpointHit(httpServletRequest);
        if (!repository.existsByIdAndState(id, State.PUBLISHED)) {
            throw new EntityNotFoundException("Сущности с id = " + id + " не существует");
        }
        Event event = repository.getReferenceById(id);
        ///TODO сильно сомневаюсь что так надо
        List<StatEntityGetResponse> responses = statClient
                .get(LocalDateTime.now().minusYears(100),
                        LocalDateTime.now(),
                        List.of(httpServletRequest.getRequestURI()),
                        true);
        if(responses != null){
            event.setViews((int) responses.get(0).getHits());
        }
        ///TODO сильно сомневаюсь что так надо
        return converter.eventConvertToEventFullDto(event);

    }

    private void checkNewEventValidData(NewEventDto newEvent, Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Пользователя с id = " + userId + " не существует.",
                    "Некорректные данные",
                    Collections.emptyList());
        }
        if (!categoryRepository.existsById(newEvent.getCategory())) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Категории с id = " + newEvent.getCategory() + " не существует.",
                    "Некорректные данные",
                    Collections.emptyList());
        }
        if (newEvent.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new CustomException(HttpStatus.FORBIDDEN.name(), //TODO по swagger FORBIDDEN высвечивает 409 кодом, хотя он 403
                    "Время проведения менее двух часов от текущего времени.",
                    "Некорректные данные",
                    Collections.emptyList());
        }
    }

    private void checkEventUpdateAdminRequest(Event oldEvent, EventUpdateAdminRequest updateRequest) {
        if (updateRequest.getEventDate() != null && updateRequest.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Некорректные данные",
                    "Время проведения менее часа от текущего времени.",
                    Collections.emptyList());
        }
        if (updateRequest.getStateAction() != null && updateRequest.getStateAction() == StateAction.PUBLISH_EVENT &&
                oldEvent.getState() != State.PENDING) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно редактировать событие",
                    "Событие не в состоянии ожидания публикации",
                    Collections.emptyList());
        }
        if (updateRequest.getStateAction() != null && updateRequest.getStateAction() == StateAction.REJECT_EVENT &&
                oldEvent.getState() == State.PUBLISHED) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно отменить событие",
                    "Событие уже опубликовано",
                    Collections.emptyList());
        }
    }

    private void checkEventUpdateRequest(Event oldEvent, EventUpdateRequest updateRequest, Integer userId) {

        if (oldEvent.getInitiator().getId() != userId) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Невозможно редактировать событие",
                    "Вы не являетесь владельцем данного события",
                    Collections.emptyList());
        }
        if (oldEvent.getState() == State.PUBLISHED) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно редактировать событие",
                    "Событие уже опубликовано",
                    Collections.emptyList());
        }
        if (updateRequest.getEventDate() != null && updateRequest.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Время проведения менее двух часов от текущего времени.",
                    "Некорректные данные",
                    Collections.emptyList());
        }
        if (updateRequest.getCategory() != null && !categoryRepository.existsById(updateRequest.getCategory())) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Категории с id = " + updateRequest.getCategory() + " не существует.",
                    "Некорректные данные",
                    Collections.emptyList());
        }
    }

    private Event buildEvent(NewEventDto newEvent, Integer userId) {
        Category category = categoryRepository.getReferenceById(newEvent.getCategory());
        State state = State.PENDING;
        User initiator = userRepository.getReferenceById(userId);
        LocalDateTime createdOn = LocalDateTime.now();

        Event event = converter.newEventDtoConvertToEvent(newEvent);
        event.setCategory(category);
        event.setState(state);
        event.setInitiator(initiator);
        event.setCreatedOn(createdOn);

        return event;
    }

    private void copyUpdateRequestToEvent(EventUpdateRequest request, Event event) {
        if (request.getAnnotation() != null) event.setAnnotation(request.getAnnotation());
        if (request.getDescription() != null) event.setDescription(request.getDescription());
        if (request.getRequestModeration() != null) event.setRequestModeration(request.getRequestModeration());
        if (request.getTitle() != null) event.setTitle(request.getTitle());
        if (request.getPaid() != null) event.setPaid(request.getPaid());
        if (request.getLocation() != null)
            event.setLocation(locationConverter.locationDtoConvertToLocation(request.getLocation()));
        if (request.getParticipantLimit() != null) event.setParticipantLimit(request.getParticipantLimit());
        if (request.getCategory() != null)
            event.setCategory(categoryRepository.getReferenceById(request.getCategory()));
        if (request.getStateAction() != null && request.getStateAction().equals(StateAction.SEND_TO_REVIEW))
            event.setState(State.PENDING);
        if (request.getStateAction() != null && request.getStateAction().equals(StateAction.CANCEL_REVIEW))
            event.setState(State.CANCELED);
    }

    private void copyUpdateAdminRequestToEvent(EventUpdateAdminRequest request, Event event) {
        if (request.getAnnotation() != null) event.setAnnotation(request.getAnnotation());
        if (request.getDescription() != null) event.setDescription(request.getDescription());
        if (request.getRequestModeration() != null) event.setRequestModeration(request.getRequestModeration());
        if (request.getTitle() != null) event.setTitle(request.getTitle());
        if (request.getPaid() != null) event.setPaid(request.getPaid());
        if (request.getLocation() != null)
            event.setLocation(locationConverter.locationDtoConvertToLocation(request.getLocation()));
        if (request.getParticipantLimit() != null) event.setParticipantLimit(request.getParticipantLimit());
        if (request.getCategory() != null)
            event.setCategory(categoryRepository.getReferenceById(request.getCategory()));
        if (request.getStateAction() != null && request.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
            event.setState(State.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        }
        if (request.getStateAction() != null && request.getStateAction().equals(StateAction.REJECT_EVENT))
            event.setState(State.CANCELED);
    }

    void saveEndpointHit(HttpServletRequest request) {
        StatEntityPostRequest newRequest = new StatEntityPostRequest(
                request.getServerName(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now());
        statClient.create(newRequest);
    }
}
