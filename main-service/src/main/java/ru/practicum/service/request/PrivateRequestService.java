package ru.practicum.service.request;

import org.springframework.http.ResponseEntity;
import ru.practicum.dto.request.ParticipantRequestDto;

import java.util.List;

public interface PrivateRequestService {
    ResponseEntity<ParticipantRequestDto> createRequestPrivate(Integer userId, Integer eventId);

    ResponseEntity<List<ParticipantRequestDto>> getRequestPrivate(Integer userId);

    ResponseEntity<ParticipantRequestDto> cancelRequestPrivate(Integer userId, Integer requestId);
}
