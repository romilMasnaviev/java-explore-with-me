package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.masnaviev.explore.dto.StatEntityPostDto;
import ru.masnaviev.explore.model.StatEntity;

@Component
@Mapper(componentModel = "spring")
public interface StatEntityConverter {
    StatEntityPostDto statEntityConvertToPostDto(StatEntity entity);

    StatEntity postDtoConvertToStatEntity(StatEntityPostDto postEntity);

}
