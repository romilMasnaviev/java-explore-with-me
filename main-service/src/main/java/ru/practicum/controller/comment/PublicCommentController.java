package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.model.Comment;
import ru.practicum.service.CommentService;

import java.util.List;

/**
 * Публичный Контроллер для {@link Comment}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
public class PublicCommentController {

    private final CommentService service;

    /**
     * Получение комментариев для события.
     *
     * @param eventId идентификатор события
     * @param from    индекс начала выборки
     * @param size    размер выборки
     * @return список комментариев
     */
    @GetMapping("/{eventId}/comments")
    public List<CommentFullDto> getEventComments(@PathVariable Integer eventId,
                                                 @RequestParam(required = false, defaultValue = "0") Integer from,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        return service.getEventCommentsPublic(eventId, from, size);
    }
}
