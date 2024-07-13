package ru.masnaviev.explore.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.masnaviev.explore.dto.location.LocationDto;
import ru.masnaviev.explore.model.enums.EventAction;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class EventUpdateAdminRequest {
    @Size(min = 20, max = 2000, message = "Аннотация должна быть от {min} до {max} символов")
    private String annotation;

    private Integer category;

    @Size(min = 20, max = 7000, message = "Описание должно быть от {min} до {max} символов")
    private String description;

    @Future(message = "Дата события должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private EventAction stateAction;

    @Size(min = 3, max = 120, message = "Заголовок должен быть от {min} до {max} символов")
    private String title;
}