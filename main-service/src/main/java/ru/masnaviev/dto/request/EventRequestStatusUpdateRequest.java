package ru.masnaviev.dto.request;

import lombok.Data;
import ru.masnaviev.model.enums.Status;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private Status status;
}
