package ru.masnaviev.dto.request;

import lombok.Data;
import ru.masnaviev.model.enums.Status;

import java.time.LocalDateTime;

@Data
public class ParticipantRequestDto {
    private int id;
    private int event;
    private int requester;
    private Status status;
    private LocalDateTime created;
}
