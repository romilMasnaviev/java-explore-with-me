package ru.practicum.dto.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.location.LocationDto;
import ru.practicum.model.Location;

@Mapper(componentModel = "spring")
@Component("locationConverter")
public interface LocationConverter {
    Location locationDtoConvertToLocation(LocationDto locationDto);
}
