package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import ru.masnaviev.explore.dto.location.LocationDto;
import ru.masnaviev.explore.model.Location;

@Mapper(componentModel = "spring")
public interface LocationConverter {
    Location locationDtoConvertToLocation(LocationDto locationDto);
    LocationDto locationConvertToLocationDto(Location location);
}
