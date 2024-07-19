package ru.practicum.dto.event;

import lombok.Data;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.user.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime eventDate;
    private int id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private int views;
    private List<CommentDto> comments;
}
