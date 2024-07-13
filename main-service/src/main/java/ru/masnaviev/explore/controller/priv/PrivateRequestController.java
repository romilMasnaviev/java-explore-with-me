package ru.masnaviev.explore.controller.priv;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.request.ParticipantRequestDto;
import ru.masnaviev.explore.service.RequestService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateRequestController {

    private RequestService service;

    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipantRequestDto> createRequest(@PathVariable Integer userId,
                                                               @RequestParam Integer eventId) {
        return service.createRequest(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipantRequestDto>> getRequest(@PathVariable Integer userId) {
        return service.getRequest(userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipantRequestDto> cancelRequest(@PathVariable Integer userId,
                                                               @PathVariable Integer requestId) {
        return service.cancelRequest(userId, requestId);
    }
}
