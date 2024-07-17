package ru.practicum.controller.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateRequestController {

    private final RequestService service;

    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipantRequestDto> createRequest(@PathVariable(name = "userId") Integer userId,
                                                               @RequestParam(name = "eventId") Integer eventId) {
        return service.createRequest(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipantRequestDto>> getRequest(@PathVariable(name = "userId") Integer userId) {
        return service.getRequest(userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipantRequestDto> cancelRequest(@PathVariable(name = "userId") Integer userId,
                                                               @PathVariable(name = "requestId") Integer requestId) {
        return service.cancelRequest(userId, requestId);
    }
}
