package ru.masnaviev.explore.dto.event;

import lombok.Data;
import ru.masnaviev.explore.dto.category.CategoryDto;
import ru.masnaviev.explore.dto.location.LocationDto;
import ru.masnaviev.explore.dto.user.UserShortDto;
import ru.masnaviev.explore.model.Category;
import ru.masnaviev.explore.model.Location;
import ru.masnaviev.explore.model.State;

import java.time.LocalDateTime;

@Data
public class EventFullDto {
    private int id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private int views;
}