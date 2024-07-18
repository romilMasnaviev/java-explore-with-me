package ru.practicum.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.dto.StatEntityPostRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;


class ClientParametersValidationTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testCreateStatEntityPostRequest_whenAppIsNull_thenThrowException() {
        StatEntityPostRequest request = new StatEntityPostRequest();

        request.setTimestamp(LocalDateTime.now());
        request.setIp("test");
        request.setUri("test");

        Set<ConstraintViolation<StatEntityPostRequest>> validates = validator.validate(request);
        Assertions.assertFalse(validates.isEmpty());
    }

    @Test
    public void testCreateStatEntityPostRequest_whenIpIsNull_thenThrowException() {
        StatEntityPostRequest request = new StatEntityPostRequest();

        request.setTimestamp(LocalDateTime.now());
        request.setApp("test");
        request.setUri("test");

        Set<ConstraintViolation<StatEntityPostRequest>> validates = validator.validate(request);
        Assertions.assertFalse(validates.isEmpty());
    }

    @Test
    public void testCreateStatEntityPostRequest_whenUriIsNull_thenThrowException() {
        StatEntityPostRequest request = new StatEntityPostRequest();

        request.setTimestamp(LocalDateTime.now());
        request.setApp("test");
        request.setIp("test");

        Set<ConstraintViolation<StatEntityPostRequest>> validates = validator.validate(request);
        Assertions.assertFalse(validates.isEmpty());
    }

    @Test
    public void testCreateStatEntityPostRequest_whenTimestampIsNull_thenThrowException() {
        StatEntityPostRequest request = new StatEntityPostRequest();

        request.setUri("test");
        request.setApp("test");
        request.setIp("test");

        Set<ConstraintViolation<StatEntityPostRequest>> validates = validator.validate(request);
        Assertions.assertFalse(validates.isEmpty());
    }

    @Test
    public void testCreateStatEntityPostRequest_whenTimestampIsFuture_thenThrowException() {
        StatEntityPostRequest request = new StatEntityPostRequest();

        request.setTimestamp(LocalDateTime.now().plusMinutes(1));
        request.setUri("test");
        request.setApp("test");
        request.setIp("test");

        Set<ConstraintViolation<StatEntityPostRequest>> validates = validator.validate(request);
        Assertions.assertFalse(validates.isEmpty());
    }

    @Test
    public void testCreateStatEntityPostRequest_whenValidData_thenValidatesSizeIsZero() {
        StatEntityPostRequest request = new StatEntityPostRequest();

        request.setTimestamp(LocalDateTime.now().minusMinutes(1));
        request.setUri("test");
        request.setApp("test");
        request.setIp("test");

        Set<ConstraintViolation<StatEntityPostRequest>> validates = validator.validate(request);
        Assertions.assertTrue(validates.isEmpty());
    }


}