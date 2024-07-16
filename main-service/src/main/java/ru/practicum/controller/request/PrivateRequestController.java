package ru.practicum.controller.request;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.request.ParticipantRequestDto;
import ru.practicum.service.RequestService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateRequestController {

    private RequestService service;

    @PostMapping("/{userId}/requests")
    public ResponseEntity<ParticipantRequestDto> createRequest(@PathVariable(name = "userId") @Min(value = 0) Integer userId,
                                                               @RequestParam(name = "eventId") Integer eventId) {
        return service.createRequest(userId, eventId);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<ParticipantRequestDto>> getRequest(@PathVariable(name = "userId") @Min(value = 0) Integer userId) {
        return service.getRequest(userId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<ParticipantRequestDto> cancelRequest(@PathVariable(name = "userId") @Min(value = 0) Integer userId,
                                                               @PathVariable(name = "requestId") @Min(value = 0) Integer requestId) {
        return service.cancelRequest(userId, requestId);
    }
}
