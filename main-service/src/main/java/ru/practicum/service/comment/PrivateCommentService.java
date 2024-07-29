package ru.practicum.service.comment;

import org.springframework.http.HttpStatus;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateRequest;
import ru.practicum.dto.comment.NewCommentDto;

public interface PrivateCommentService {
    CommentFullDto createCommentPrivate(Integer userId, Integer eventId, NewCommentDto newComment);

    CommentFullDto updateCommentPrivate(Integer userId, Integer commentId, CommentUpdateRequest commentUpdateRequest);

    HttpStatus deleteCommentPrivate(Integer userId, Integer commentId);
}
