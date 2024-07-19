package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateRequest;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.model.Comment;
import ru.practicum.service.comment.PrivateCommentService;

import javax.validation.Valid;

/**
 * Приватный Контроллер для {@link Comment}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateCommentController {

    private final PrivateCommentService service;

    /**
     * Создание комментария.
     *
     * @param userId     идентификатор пользователя
     * @param eventId    идентификатор события
     * @param newComment данные нового комментария
     * @return созданный комментарий
     */
    @PostMapping("/{userId}/events/{eventId}/comments")
    public ResponseEntity<CommentFullDto> createComment(@PathVariable(name = "userId") Integer userId,
                                                        @PathVariable(name = "eventId") Integer eventId,
                                                        @RequestBody @Valid NewCommentDto newComment) {
        return service.createCommentPrivate(userId, eventId, newComment);
    }

    /**
     * Редактирование комментария.
     *
     * @param userId               идентификатор пользователя
     * @param commentId            идентификатор комментария
     * @param commentUpdateRequest данные для обновления комментария
     * @return обновленный комментарий
     */
    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentFullDto updateCommentPrivate(@PathVariable(name = "userId") Integer userId,
                                               @PathVariable(name = "commentId") Integer commentId,
                                               @RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {
        return service.updateCommentPrivate(userId, commentId, commentUpdateRequest);
    }

    /**
     * Удаление комментария.
     *
     * @param userId    идентификатор пользователя
     * @param commentId идентификатор комментария
     * @return статус удаления
     */
    @DeleteMapping("/{userId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteCommentPrivate(@PathVariable(name = "userId") Integer userId,
                                                           @PathVariable(name = "commentId") Integer commentId) {
        return service.deleteCommentPrivate(userId, commentId);
    }
}
