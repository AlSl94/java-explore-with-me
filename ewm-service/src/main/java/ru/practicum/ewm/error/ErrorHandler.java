package ru.practicum.ewm.error;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleAlreadyExistsException(final AlreadyExistsException e, HttpServletRequest request) {
        log.error("Already Exists - " + e.getMessage() + "at requested path {}", request.getPathInfo());
        return new ExceptionResponse(e.getMessage(), "CONFLICT", ErrorState.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(final NotFoundException e, HttpServletRequest request) {
        log.error("Not_found " + e.getMessage() + "at requested path {}", request.getPathInfo());
        return new ExceptionResponse(e.getMessage(), "NOT_FOUND", ErrorState.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidateException(final ValidationException e, HttpServletRequest request) {
        log.error("Validate Exception - " + e.getMessage() + "at requested path {}", request.getPathInfo());
        return new ExceptionResponse(e.getMessage(), "BAD_REQUEST", ErrorState.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleForbiddenException(final ForbiddenException e, HttpServletRequest request) {
        log.error("Forbidden - " + e.getMessage() + "at requested path {}", request.getPathInfo());
        return new ExceptionResponse(e.getMessage(), "FORBIDDEN", ErrorState.FORBIDDEN);
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionResponse handleFeignStatusException(FeignException e,
                                                        HttpServletResponse response, HttpServletRequest request) {
        response.setStatus(e.status());
        log.error("Feign Exception - " + e.getMessage() + "at requested path {}", request.getPathInfo());
        return new ExceptionResponse(e.getMessage(), "SERVICE_UNAVAILABLE", ErrorState.SERVICE_UNAVAILABLE);
    }
}