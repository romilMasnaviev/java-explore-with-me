package ru.practicum.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.StatEntityPostRequest;
import ru.practicum.model.StatEntity;

@Component("statEntityConverter")
@Mapper(componentModel = "spring")
public interface StatEntityConverter {
    StatEntityPostRequest statEntityConvertToPostDto(StatEntity entity);

    StatEntity postDtoConvertToStatEntity(StatEntityPostRequest postEntity);

}
