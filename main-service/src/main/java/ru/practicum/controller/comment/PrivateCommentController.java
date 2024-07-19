package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateRequest;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.service.CommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateCommentController {

    private final CommentService service;

    @PostMapping("/{userId}/events/{eventId}/comments")
    public ResponseEntity<CommentFullDto> createComment(@PathVariable(name = "userId") Integer userId,
                                                        @PathVariable(name = "eventId") Integer eventId,
                                                        @RequestBody @Valid NewCommentDto newComment) {
        return service.createComment(userId, eventId, newComment);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentFullDto updateComment(@PathVariable(name = "userId") Integer userId,
                                        @PathVariable(name = "commentId") Integer commentId,
                                        @RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {
        return service.updateComment(userId, commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "userId") Integer userId,
                                             @PathVariable(name = "commentId") Integer commentId) {
        return service.delete(userId, commentId);
    }
}
