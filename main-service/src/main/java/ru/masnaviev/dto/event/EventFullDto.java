package ru.masnaviev.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.masnaviev.dto.category.CategoryDto;
import ru.masnaviev.dto.location.LocationDto;
import ru.masnaviev.dto.user.UserShortDto;
import ru.masnaviev.model.enums.State;

import java.time.LocalDateTime;

@Data
public class EventFullDto {
    private int id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private int views;
}