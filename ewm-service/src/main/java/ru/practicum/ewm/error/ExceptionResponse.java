package ru.practicum.ewm.error;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private final String error;
    private final String description;
    private final ErrorState status;

    public ExceptionResponse(String error, String description, ErrorState status) {
        this.error = error;
        this.description = description;
        this.status = status;
    }
}