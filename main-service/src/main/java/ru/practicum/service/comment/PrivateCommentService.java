package ru.practicum.service.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateRequest;
import ru.practicum.dto.comment.NewCommentDto;

public interface PrivateCommentService {
    ResponseEntity<CommentFullDto> createCommentPrivate(Integer userId, Integer eventId, NewCommentDto newComment);

    CommentFullDto updateCommentPrivate(Integer userId, Integer commentId, CommentUpdateRequest commentUpdateRequest);

    ResponseEntity<HttpStatus> deleteCommentPrivate(Integer userId, Integer commentId);
}
