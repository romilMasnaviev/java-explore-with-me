package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateAdminRequest;
import ru.practicum.model.Comment;
import ru.practicum.service.comment.AdminCommentService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.conf.Configuration.LOCAL_DATE_TIME_PATTERN;

/**
 * Админ Контроллер для {@link Comment}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final AdminCommentService service;

    /**
     * Удаление комментария.
     *
     * @param commentId идентификатор комментария
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "commentId") Integer commentId) {
        return service.deleteCommentByAdmin(commentId);
    }

    /**
     * Редактирование комментария.
     *
     * @param commentId            идентификатор комментария
     * @param commentUpdateRequest данные для обновления комментария
     * @return обновленный комментарий
     */
    @PatchMapping("/{commentId}")
    public CommentFullDto updateCommentByAdmin(@PathVariable(name = "commentId") Integer commentId,
                                               @RequestBody @Valid CommentUpdateAdminRequest commentUpdateRequest) {
        return service.updateCommentByAdmin(commentId, commentUpdateRequest);
    }

    /**
     * Получение комментариев.
     *
     * @param ids      список идентификаторов комментариев
     * @param events   список идентификаторов событий
     * @param authors  список идентификаторов авторов
     * @param text     текст комментария
     * @param start    дата и время начала фильтрации
     * @param end      дата и время конца фильтрации
     * @param isEdited отредактирован ли комментарий
     * @param from     индекс начала выборки
     * @param size     размер выборки
     * @return список комментариев
     */
    @GetMapping
    public List<CommentFullDto> getCommentsByAdmin(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                                   @RequestParam(name = "events", required = false) List<Integer> events,
                                                   @RequestParam(name = "authors", required = false) List<Integer> authors,
                                                   @RequestParam(name = "text", required = false) String text,
                                                   @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime start,
                                                   @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = LOCAL_DATE_TIME_PATTERN) LocalDateTime end,
                                                   @RequestParam(name = "isEdited", required = false) Boolean isEdited,
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getCommentsByAdmin(ids, events, authors, text, start, end, isEdited, from, size);
    }
}
