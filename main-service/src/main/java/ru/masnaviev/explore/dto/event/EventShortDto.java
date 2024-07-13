package ru.masnaviev.explore.dto.event;

import lombok.Data;
import ru.masnaviev.explore.dto.category.CategoryDto;
import ru.masnaviev.explore.dto.user.UserShortDto;

import java.time.LocalDateTime;

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
}
