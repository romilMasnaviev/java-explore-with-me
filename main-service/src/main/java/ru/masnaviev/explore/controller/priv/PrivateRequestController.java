package ru.masnaviev.explore.controller.priv;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.masnaviev.explore.dto.request.ParticipantRequestDto;
import ru.masnaviev.explore.service.RequestService;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Validated
public class PrivateRequestController {

    private RequestService service;

    @PostMapping("/{userId}/requests")
    public ParticipantRequestDto createRequest(@PathVariable Integer userId,
                                               @RequestParam Integer eventId){
        return service.createRequest(userId,eventId);
    }
}
