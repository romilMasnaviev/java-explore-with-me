package ru.masnaviev.explore.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class AppControllerAdvice {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEntityAlreadyExistsException(final EntityAlreadyExistsException exception) {
        log.error(exception.getMessage());
        CustomException customException = new CustomException(HttpStatus.CONFLICT.name(), "Сущность уже существует", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.name(), "Неверные входные данные", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(final ConstraintViolationException exception) {
        log.error(exception.getMessage());
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.name(), "Ограничение БД", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNumberFormatException(final NumberFormatException exception) {
        log.error(exception.getMessage());
        CustomException customException = new CustomException(HttpStatus.BAD_REQUEST.name(), "Неправильный формат числа", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEmptyResultDataAccessException(final EmptyResultDataAccessException exception) {
        log.error(exception.getMessage());
        CustomException customException = new CustomException(HttpStatus.NOT_FOUND.name(), "Сущность с таким id не найдена", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(final DataIntegrityViolationException exception) {
        log.error(exception.getMessage());
        CustomException customException = new CustomException(HttpStatus.CONFLICT.name(), "Ограничение БД", exception.getMessage(),
                List.of(Arrays.toString(exception.getStackTrace())));
        return new ApiError(customException);
    }

}
