package ru.practicum.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.CommentUpdateAdminRequest;
import ru.practicum.service.CommentService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private static final String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";
    private final CommentService service;

    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "commentId") Integer commentId) {
        return service.delete(commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentFullDto updateComment(@PathVariable(name = "commentId") Integer commentId,
                                        @RequestBody @Valid CommentUpdateAdminRequest commentUpdateRequest) {
        return service.updateComment(commentId, commentUpdateRequest);
    }

    @GetMapping
    public List<CommentFullDto> getComments(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                            @RequestParam(name = "events", required = false) List<Integer> events,
                                            @RequestParam(name = "authors", required = false) List<Integer> authors,
                                            @RequestParam(name = "text", required = false) String text,
                                            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime start,
                                            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = localDateTimePattern) LocalDateTime end,
                                            @RequestParam(name = "isEdited", required = false) Boolean isEdited,
                                            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getComments(ids, events, authors, text, start, end, isEdited, from, size);
    }
}

