package ru.practicum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RequestStatusUpdateResult {
    private List<ParticipantRequestDto> confirmedRequests;
    private List<ParticipantRequestDto> rejectedRequests;

}