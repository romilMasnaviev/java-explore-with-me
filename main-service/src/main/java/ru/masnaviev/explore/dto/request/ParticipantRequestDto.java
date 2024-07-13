package ru.masnaviev.explore.dto.request;

import lombok.Data;
import ru.masnaviev.explore.model.Status;

import java.time.LocalDateTime;

@Data
public class ParticipantRequestDto {
    private int id;
    private int event;
    private int requester;
    private Status status;
    private LocalDateTime created;
}
