package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.model.enums.EventAction;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.conf.Configuration.LOCAL_DATE_TIME_PATTERN;

@Data
public class EventUpdateAdminRequest {
    @Size(min = 20, max = 2000, message = "Аннотация должна быть от {min} до {max} символов")
    private String annotation;

    private Integer category;

    @Size(min = 20, max = 7000, message = "Описание должно быть от {min} до {max} символов")
    private String description;

    @Future(message = "Дата события должна быть в будущем")
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventAction stateAction;

    @Size(min = 3, max = 120, message = "Заголовок должен быть от {min} до {max} символов")
    private String title;
}