package ru.practicum.service.comment;

import org.springframework.http.HttpStatus;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminCommentService {
    HttpStatus deleteCommentByAdmin(Integer commentId);

    CommentFullDto updateCommentByAdmin(Integer commentId, CommentUpdateAdminRequest commentUpdateRequest);

    List<CommentFullDto> getCommentsByAdmin(List<Integer> ids, List<Integer> events,
                                            List<Integer> authors, String text, LocalDateTime start,
                                            LocalDateTime end, Boolean isEdited, Integer from, Integer size);
}
