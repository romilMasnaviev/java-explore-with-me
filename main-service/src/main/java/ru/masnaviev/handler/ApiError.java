package ru.masnaviev.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private String status;
    private String reason;
    private String message;
    private LocalDateTime timestamp;
    private List<String> errors;

    public ApiError(CustomException ex) {
        this.status = ex.getStatus();
        this.message = ex.getMessage();
        this.errors = ex.getErrors();
        this.timestamp = LocalDateTime.now();
        this.reason = ex.getReason();
    }
}
