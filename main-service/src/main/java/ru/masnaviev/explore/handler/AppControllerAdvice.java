package ru.masnaviev.explore.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class AppControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final MethodArgumentNotValidException exception) {
        log.error(HttpStatus.BAD_REQUEST.toString());
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.name(), "Неверные входные данные", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(final ConstraintViolationException exception) {
        log.error(HttpStatus.BAD_REQUEST.name());
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.name(), "Неверные входные данные", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNumberFormatException(final NumberFormatException exception) {
        log.error(HttpStatus.BAD_REQUEST.name());
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.name(), "Неправильный формат числа", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEmptyResultDataAccessException(final EmptyResultDataAccessException exception) {
        log.error(HttpStatus.NOT_FOUND.name());
        CustomException customException = new CustomException(HttpStatus.NOT_FOUND.name(), "Сущность с таким id не найдена", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(final DataIntegrityViolationException exception) {
        log.error(HttpStatus.CONFLICT.name());
        CustomException customException = new CustomException(HttpStatus.CONFLICT.name(), "Ограничение БД", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        log.error(HttpStatus.BAD_REQUEST.name());
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.name(), "Нечитаемое тело запроса", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotFoundException(final EntityNotFoundException exception) {
        log.error(HttpStatus.NOT_FOUND.name());
        CustomException customException = new CustomException(HttpStatus.NOT_FOUND.name(), "Сущность с таким id не найдена", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(final CustomException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ApiError(exception), HttpStatus.valueOf(exception.getStatus()));
    }
}
