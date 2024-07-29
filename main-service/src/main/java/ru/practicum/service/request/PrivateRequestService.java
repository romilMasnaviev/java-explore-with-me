package ru.practicum.service.request;

import ru.practicum.dto.request.ParticipantRequestDto;

import java.util.List;

public interface PrivateRequestService {
    ParticipantRequestDto createRequestPrivate(Integer userId, Integer eventId);

    List<ParticipantRequestDto> getRequestPrivate(Integer userId);

    ParticipantRequestDto cancelRequestPrivate(Integer userId, Integer requestId);
}
