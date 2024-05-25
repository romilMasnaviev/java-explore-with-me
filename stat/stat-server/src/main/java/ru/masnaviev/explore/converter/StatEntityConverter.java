package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.masnaviev.explore.dto.StatEntityGetResponse;
import ru.masnaviev.explore.dto.StatEntityPostRequest;
import ru.masnaviev.explore.model.StatEntity;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface StatEntityConverter {
    StatEntityPostRequest statEntityConvertToPostDto(StatEntity entity);

    StatEntity postDtoConvertToStatEntity(StatEntityPostRequest postEntity);

    List<StatEntityGetResponse> statEntityConvertToGetResponse(List<StatEntity> entities);

}
