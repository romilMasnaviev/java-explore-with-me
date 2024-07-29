package ru.practicum.service.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dao.CommentRepository;
import ru.practicum.dao.EventRepository;
import ru.practicum.dao.UserRepository;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateAdminRequest;
import ru.practicum.dto.comment.CommentUpdateRequest;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.dto.converter.CommentConverter;
import ru.practicum.handler.CustomException;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.model.enums.State;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService implements AdminCommentService, PrivateCommentService, PublicCommentService {

    private final CommentRepository repository;
    private final CommentConverter converter;

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentFullDto createCommentPrivate(Integer userId, Integer eventId, NewCommentDto newComment) {
        log.debug("Создание комментария = {}, userId = {}, eventId = {}", newComment, userId, eventId);
        Comment comment = buildNewComment(userId, eventId, newComment);

        return converter.commentConvertToFullCommentDto(repository.save(comment));
    }

    @Transactional
    @Override
    public CommentFullDto updateCommentPrivate(Integer userId, Integer commentId, CommentUpdateRequest commentUpdateRequest) {
        log.debug("Обновление комментария, commentUpdateRequest = {}, userId = {}, commentId = {}",
                commentUpdateRequest, userId, commentId);

        Comment comment = repository.getReferenceById(commentId);
        checkUpdateCommentValidData(userId, comment);

        comment.setText(commentUpdateRequest.getText());
        comment.setEdited(true);

        return converter.commentConvertToFullCommentDto(repository.save(comment));
    }

    @Transactional
    @Override
    public HttpStatus deleteCommentPrivate(Integer userId, Integer commentId) {
        log.debug("Удаление комментария с id = {}, userId = {}", commentId, userId);

        checkDeleteCommentValidData(commentId, userId);
        repository.deleteById(commentId);

        return HttpStatus.NO_CONTENT;
    }

    @Transactional
    @Override
    public HttpStatus deleteCommentByAdmin(Integer commentId) {
        log.debug("Удаление комментария с id = {}", commentId);
        repository.deleteById(commentId);
        return HttpStatus.NO_CONTENT;
    }

    @Transactional
    @Override
    public CommentFullDto updateCommentByAdmin(Integer commentId, CommentUpdateAdminRequest commentUpdateRequest) {
        log.debug("Обновление комментария, commentUpdateRequest = {}, commentId = {}",
                commentUpdateRequest, commentId);

        Comment comment = repository.getReferenceById(commentId);

        comment.setText(commentUpdateRequest.getText());
        comment.setEdited(commentUpdateRequest.isEdited());

        return converter.commentConvertToFullCommentDto(repository.save(comment));
    }

    @Override
    public List<CommentFullDto> getCommentsByAdmin(List<Integer> ids, List<Integer> events, List<Integer> authors,
                                                   String text, LocalDateTime start, LocalDateTime end, Boolean isEdited,
                                                   Integer from, Integer size) {
        log.debug("Получение комментариев с параметрами: " +
                        "ids = {}, events = {}, авторы = {}, " +
                        "текст = {}, начало = {}, конец = {}, " +
                        "изменено = {}, от = {}, размер = {}",
                ids, events, authors, text, start, end, isEdited, from, size);
        Pageable pageable = PageRequest.of(from, size);
        return converter.commentConvertToFullCommentDto(repository.findByParameters(ids, events, authors,
                text, start, end, isEdited, pageable));
    }

    @Override
    public List<CommentFullDto> getEventCommentsPublic(Integer eventId, Integer from, Integer size) {
        log.debug("Получение комментариев дл события с id = {}, from = {}, size ={}", eventId, from, size);

        checkEventPublished(eventId);
        Pageable pageable = PageRequest.of(from, size);
        return converter.commentConvertToFullCommentDto(repository.findByEventId(eventId, pageable));
    }

    private Comment buildNewComment(Integer userId, Integer eventId, NewCommentDto newComment) {
        checkEventPublished(eventId);


        Event event = checkEventPublished(eventId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id = " + userId + " не найден"));
        Comment comment = converter.newCommentDtoConvertToComment(newComment);
        comment.setAuthor(user);
        comment.setEvent(event);

        return comment;
    }

    private Event checkEventPublished(Integer eventId) {
        return eventRepository.findById(eventId)
                .filter(event -> event.getState().equals(State.PUBLISHED))
                .orElseThrow(() -> new EntityNotFoundException("Событие с id = " + eventId + " не найдено или не опубликовано"));
    }

    private void checkUpdateCommentValidData(Integer userId, Comment comment) {
        if (comment.getAuthor().getId() != userId) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Невозможно редактировать комментарий",
                    "Вы не являетесь владельцем данного комментария",
                    Collections.emptyList());
        }
        if (comment.getCreated().minusDays(1).isAfter(LocalDateTime.now())) {
            throw new CustomException(HttpStatus.CONFLICT.name(),
                    "Невозможно редактировать комментарий",
                    "C момента публикации комментария прошло более суток",
                    Collections.emptyList());
        }
    }

    private void checkDeleteCommentValidData(Integer commentId, Integer userId) {
        if (repository.getReferenceById(commentId).getAuthor().getId() != userId) {
            throw new CustomException(HttpStatus.BAD_REQUEST.name(),
                    "Невозможно удалить комментарий",
                    "Вы не являетесь владельцем данного комментария",
                    Collections.emptyList());
        }
    }
}
