package ru.masnaviev.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.masnaviev.dto.StatEntityPostRequest;
import ru.masnaviev.model.StatEntity;

@Component
@Mapper(componentModel = "spring")
public interface StatEntityConverter {
    StatEntityPostRequest statEntityConvertToPostDto(StatEntity entity);

    StatEntity postDtoConvertToStatEntity(StatEntityPostRequest postEntity);

}
