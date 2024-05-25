package ru.masnaviev.explore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
public class StatEntityPostRequest {
    @NotNull(message = "app must not be null")
    private String app;
    @NotNull(message = "uri must not be null")
    private String uri;
    @NotNull(message = "ip must not be null")
    private String ip;
    @PastOrPresent(message = "time must not be future")
    private LocalDateTime timestamp;
}
