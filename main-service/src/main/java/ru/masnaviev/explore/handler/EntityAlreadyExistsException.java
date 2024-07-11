package ru.masnaviev.explore.handler;

import org.springframework.http.HttpStatus;

import java.util.List;

public class EntityAlreadyExistsException extends CustomException {
    private static final String REASON = "Сущность уже существует";
    private static final String STATUS = HttpStatus.CONFLICT.name();

    public EntityAlreadyExistsException(List<String> errors, String message) {
        super(STATUS, REASON, message, errors);
    }
}
