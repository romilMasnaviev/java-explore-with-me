package ru.masnaviev.converter;

import org.mapstruct.Mapper;
import ru.masnaviev.dto.location.LocationDto;
import ru.masnaviev.model.Location;

@Mapper(componentModel = "spring")
public interface LocationConverter {
    Location locationDtoConvertToLocation(LocationDto locationDto);
}
