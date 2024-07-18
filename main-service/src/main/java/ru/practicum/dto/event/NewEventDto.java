package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.dto.location.LocationDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
public class NewEventDto {
    @NotNull(message = "Аннотация не должна быть пустой")
    @NotBlank(message = "Аннотация не должна содержать только пробельные символы")
    @Size(min = 20, max = 2000, message = "Аннотация должна быть от {min} до {max} символов")
    private String annotation;

    @NotNull(message = "Категория не должна быть пустой")
    private int category;

    @NotNull(message = "Описание не должно быть пустым")
    @NotBlank(message = "Описание не должно содержать только пробельные символы")
    @Size(min = 20, max = 7000, message = "Описание должно быть от {min} до {max} символов")
    private String description;

    @NotNull(message = "Дата события не должна быть пустой")
    @Future(message = "Дата события должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "Место проведения события не должно быть пустым")
    private LocationDto location;

    private boolean paid = false;

    @Min(value = 0, message = "Лимит участников должен быть не меньше {value}")
    private int participantLimit = 0;

    private boolean requestModeration = true;

    @NotNull(message = "Заголовок не должен быть пустым")
    @Size(min = 3, max = 120, message = "Заголовок должен быть от {min} до {max} символов")
    private String title;
}
