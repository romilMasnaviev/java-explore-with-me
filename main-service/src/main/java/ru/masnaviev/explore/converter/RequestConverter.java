package ru.masnaviev.explore.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.masnaviev.explore.dto.request.ParticipantRequestDto;
import ru.masnaviev.explore.model.Request;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestConverter {

    @Mapping(target = "requester",source = "userId")
    @Mapping(target = "event",source = "eventId")
    ParticipantRequestDto RequestConvertToParticipantRequestDto(Request request);

    List<ParticipantRequestDto> RequestConvertToParticipantRequestDto(List<Request> requests);

    @Mapping(source = "requester",target = "userId")
    @Mapping(source = "event",target = "eventId")
    Request participantRequestDtoConvertToRequest(ParticipantRequestDto request);

    List<Request> participantRequestDtoConvertToRequest(List<ParticipantRequestDto> requests);
}
