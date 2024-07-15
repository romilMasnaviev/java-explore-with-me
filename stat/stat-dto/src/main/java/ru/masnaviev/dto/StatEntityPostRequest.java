package ru.masnaviev.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatEntityPostRequest {
    @NotNull(message = "app must not be null")
    private String app;
    @NotNull(message = "uri must not be null")
    private String uri;
    @NotNull(message = "ip must not be null")
    private String ip;
    @NotNull(message = "timestamp must not be null")
    @PastOrPresent(message = "time must not be future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
