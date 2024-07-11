package ru.masnaviev.explore.model;

import lombok.Data;
import ru.masnaviev.explore.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
public class Event {
    private int id;
    private String annotation;
    private Category category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private UserShortDto initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private int views;
}
