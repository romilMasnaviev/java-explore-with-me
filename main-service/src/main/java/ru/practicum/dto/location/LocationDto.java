package ru.practicum.dto.location;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LocationDto {
    @NotNull(message = "Широта не должна быть пустой")
    private float lat;

    @NotNull(message = "Долгота не должна быть пустой")
    private float lon;
}