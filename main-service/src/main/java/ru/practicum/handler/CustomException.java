package ru.practicum.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {
    private final String status;
    private final String reason;
    private final String message;
    private final List<String> errors;

    public CustomException(String status, String reason, String message, List<String> errors) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.errors = errors;
    }
}
