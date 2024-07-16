package ru.masnaviev.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.masnaviev.dto.location.LocationDto;
import ru.masnaviev.model.Location;

@Mapper(componentModel = "spring")
@Component("locationConverter")
public interface LocationConverter {
    Location locationDtoConvertToLocation(LocationDto locationDto);
}
