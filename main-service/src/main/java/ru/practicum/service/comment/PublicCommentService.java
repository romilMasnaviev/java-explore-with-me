package ru.practicum.service.comment;

import ru.practicum.dto.comment.CommentFullDto;

import java.util.List;

public interface PublicCommentService {
    List<CommentFullDto> getEventCommentsPublic(Integer eventId, Integer from, Integer size);
}
