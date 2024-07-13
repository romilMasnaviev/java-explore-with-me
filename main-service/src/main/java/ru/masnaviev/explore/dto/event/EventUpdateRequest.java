package ru.masnaviev.explore.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.masnaviev.explore.dto.location.LocationDto;
import ru.masnaviev.explore.model.Location;
import ru.masnaviev.explore.model.StateAction;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class EventUpdateRequest {
    private String annotation;
    private Integer category;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    @Positive
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    private String title;
}
