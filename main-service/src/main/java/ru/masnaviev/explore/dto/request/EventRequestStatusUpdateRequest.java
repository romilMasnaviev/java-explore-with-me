package ru.masnaviev.explore.dto.request;

import lombok.Data;
import ru.masnaviev.explore.model.Status;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private Status status;
}
