package ru.practicum.dto.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentFullDto {
    private int id;
    private int eventId;
    private int authorId;
    private String text;
    private LocalDateTime created;
    private boolean isEdited;
}