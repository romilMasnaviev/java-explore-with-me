package ru.practicum.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Client;
import ru.practicum.dao.*;
import ru.practicum.dto.StatEntityGetResponse;
import ru.practicum.dto.StatEntityPostRequest;
import ru.practicum.dto.converter.EventConverter;
import ru.practicum.dto.converter.LocationConverter;
import ru.practicum.dto.converter.RequestConverter;
import ru.practicum.dto.event.*;
import ru.practicum.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.dto.request.RequestStatusUpdateResult;
import ru.practicum.handler.CustomException;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.model.enums.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService implements AdminEventService, PrivateEventService, PublicEventService {

    private final EventRepository repository;
    private final EventConverter converter;

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;

    private final LocationConverter locationConverter;
    private final RequestConverter requestConverter;

    private final Client statClient;

    @Override
    public EventFullDto getEventPublic(Integer id, HttpServletRequest httpServletRequest) {
        log.debug("Получение события по id, id = {}", id);
        saveEndpointHit(httpServletRequest);
        if (!repository.existsByIdAndState(id, State.PUBLISHED)) {
            throw new EntityNotFoundException("Сущности с id = " + id + " не существует. Возможно событие еще не опубликовано");
        }

        Event event = repository.getReferenceById(id);
        setEventViews(event, httpServletRequest);
        repository.save(event);

        return converter.eventConvertToEventFullDto(event);
    }

    @Override
    public List<EventFullDto> getEventsPublic(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd, Boolean onlyAvailable, EventSort sort, Integer from, Integer size, HttpServletRequest httpServletRequest) {
        log.debug("Получение событий по заданным фильтрам, text = {}, categories = {}, paid = {}, " +
                        "rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

        saveEndpointHit(httpServletRequest);
        String sorting = determineSorting(sort);

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }
        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }

        if (rangeEnd.isBefore(rangeStart)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(), "Время конца меньше времени начала", "Некорректные данные", Collections.emptyList());
        }

        Pageable pageable = PageRequest.of(from, size, Sort.by(sorting));
        List<Event> events = repository.getAllByTextAndParameters(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);

        for (Event event : events) {
            setEventViews(event, httpServletRequest);
        }
        repository.saveAll(events);
        return converter.eventConvertToEventFullDto(events);
    }

    @Transactional
    @Override
    public EventFullDto createEventPrivate(Integer userId, NewEventDto newEvent) {
        log.debug("Создание события {}, userId = {}", newEvent, userId);
        checkNewEventValidData(newEvent);
        Event event = buildEvent(newEvent, userId);

        locationRepository.save(event.getLocation());
        Event savedEvent = repository.save(event);
        return converter.eventConvertToEventFullDto(savedEvent);
    }

    @Transactional
    @Override
    public EventFullDto getEventPrivate(Integer userId, Integer eventId) {
        log.debug("Получение события, userId = {}, eventId ={}", userId, eventId);
        Event event = repository.getReferenceById(eventId);
        if (event.getInitiator().getId() != userId) {
            throw new EntityNotFoundException("Событие с id = " + eventId + " не найдено или недоступно");
        }
        return converter.eventConvertToEventFullDto(repository.getReferenceById(eventId));
    }

    @Transactional
    @Override
    public List<EventShortDto> getUserEventsPrivate(Integer userId, Integer from, Integer size) {
        log.debug("Получение событий, добавленных пользователем, user id = {}, from = {}, size = {}", userId, from, size);
        return converter.eventConvertToEventShortDto(repository.getUserEvents(from, size, userId));
    }

    @Transactional
    @Override
    public EventFullDto updateEventByUserPrivate(Integer eventId, Integer userId, EventUpdateRequest updateRequest) {
        log.debug("Обновление события пользователем, eventId = {}, userId = {}, request = {}", eventId, userId, updateRequest);
        Event oldEvent = repository.getReferenceById(eventId);

        checkEventUpdateRequest(oldEvent, updateRequest, userId);

        copyUpdateRequestToEvent(updateRequest, oldEvent);

        return converter.eventConvertToEventFullDto(repository.save(oldEvent));
    }

    @Override
    public List<ParticipantRequestDto> getUserEventRequestsPrivate(Integer eventId, Integer userId) {
        log.debug("Получение информации о запросах на участие в событии текущего пользователя," +
                "eventId = {}, userId = {}", eventId, userId);
        validateUserAccessToEvent(eventId, userId);
        List<Request> requests = requestRepository.findAllByEventId(eventId);
        return requestConverter.requestConvertToParticipantRequestDto(requests);
    }

    @Transactional
    @Override
    public RequestStatusUpdateResult changeEventRequestsStatusPrivate(Integer eventId, Integer userId,
                                                                      EventRequestStatusUpdateRequest updateRequest) {
        log.debug("Получение информации о запросах на участие в событии текущего пользователя," +
                "eventId = {}, userId = {}, updateRequest = {}", eventId, userId, updateRequest);

        validateUserAccessToEvent(eventId, userId);

        Event event = repository.getReferenceById(eventId);

        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно принимать/отклонять заявки",
                    "Достигнут лимит количества заявок", Collections.emptyList());
        }

        List<ParticipantRequestDto> requests = requestConverter.requestConvertToParticipantRequestDto(
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
            List<ParticipantRequestDto> pendingRequests = requestConverter.requestConvertToParticipantRequestDto(
                    requestRepository.findAllByEventIdAndStatus(eventId, Status.PENDING));
            for (ParticipantRequestDto pendingRequest : pendingRequests) {
                pendingRequest.setStatus(Status.REJECTED);
                rejectedRequests.add(pendingRequest);
            }
            requestRepository.saveAll(requestConverter.participantRequestDtoConvertToRequest(pendingRequests));
        }

        return new RequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    @Transactional
    @Override
    public EventFullDto updateEventInfoAndStatusByAdmin(Integer eventId, EventUpdateAdminRequest updateRequest) {
        log.debug("Обновление события пользователем, eventId = {}, request = {}", eventId, updateRequest);
        Event oldEvent = repository.getReferenceById(eventId);

        checkEventUpdateAdminRequest(oldEvent, updateRequest);
        copyUpdateAdminRequestToEvent(updateRequest, oldEvent);

        return converter.eventConvertToEventFullDto(repository.save(oldEvent));
    }

    @Override
    public List<EventFullDto> getEventsByAdmin(List<Integer> users, List<State> states,
                                               List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                               Integer from, Integer size) {
        log.debug("Получение события по заданным фильтрам, users ={} , states = {}, categories = {}, " +
                        "rangeStart = {}, rangeEnd ={}, from = {}, size = {}", users, states, categories, rangeStart, rangeEnd,
                from, size);

        Pageable pageable = PageRequest.of(from, size);
        return converter.eventConvertToEventFullDto(repository.getAllByParameters(users, states, categories, rangeStart, rangeEnd, pageable));
    }

    private void setEventViews(Event event, HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        List<ru.practicum.dto.StatEntityGetResponse> responses = statClient
                .get(LocalDateTime.now().minusYears(100),
                        LocalDateTime.now(),
                        List.of(httpServletRequest.getRequestURI()),
                        true);
        int hitCount = 0;
        if (responses != null) {
            for (StatEntityGetResponse response : responses) {
                if (response.getUri().equals(uri)) {
                    hitCount = (int) response.getHits();
                    break;
                }
            }
            event.setViews(hitCount);
        }
    }

    private void validateUserAccessToEvent(Integer eventId, Integer userId) {
        Event event = repository.getReferenceById(eventId);
        if (event.getInitiator().getId() != userId) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Невозможно просматривать заявки",
                    "Вы не являетесь владельцем данного события",
                    Collections.emptyList());
        }
    }

    private String determineSorting(EventSort sort) {
        if (sort == null) {
            return "id";
        } else if (sort == EventSort.EVENT_DATE) {
            return "eventDate";
        } else if (sort == EventSort.VIEWS) {
            return "views";
        } else {
            return "id";
        }
    }

    private void checkNewEventValidData(NewEventDto newEvent) {
        if (newEvent.getEventDate().minusHours(2).isBefore(LocalDateTime.now())) {
            throw new CustomException(HttpStatus.FORBIDDEN.name(),
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
        if (updateRequest.getStateAction() != null && updateRequest.getStateAction() == EventAction.PUBLISH_EVENT &&
                oldEvent.getState() != State.PENDING) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно редактировать событие",
                    "Событие не в состоянии ожидания публикации",
                    Collections.emptyList());
        }
        if (updateRequest.getStateAction() != null && updateRequest.getStateAction() == EventAction.REJECT_EVENT &&
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
        if (request.getStateAction() != null && request.getStateAction().equals(EventAction.PUBLISH_EVENT)) {
            event.setState(State.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        }
        if (request.getStateAction() != null && request.getStateAction().equals(EventAction.REJECT_EVENT))
            event.setState(State.CANCELED);
    }

    private void saveEndpointHit(HttpServletRequest request) {
        StatEntityPostRequest newRequest = new StatEntityPostRequest(
                request.getServerName(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now());
        statClient.create(newRequest);
    }
}
