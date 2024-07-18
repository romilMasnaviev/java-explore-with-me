package ru.practicum.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.model.Request;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("requestConverter")
public interface RequestConverter {

    @Mapping(target = "requester", source = "userId")
    @Mapping(target = "event", source = "eventId")
    ParticipantRequestDto requestConvertToParticipantRequestDto(Request request);

    List<ParticipantRequestDto> requestConvertToParticipantRequestDto(List<Request> requests);

    @Mapping(source = "requester", target = "userId")
    @Mapping(source = "event", target = "eventId")
    Request participantRequestDtoConvertToRequest(ParticipantRequestDto request);

    List<Request> participantRequestDtoConvertToRequest(List<ParticipantRequestDto> requests);
}
