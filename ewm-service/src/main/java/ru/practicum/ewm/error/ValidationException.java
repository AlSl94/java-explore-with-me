package ru.practicum.ewm.error;

public class ValidationException extends RuntimeException {
    public ValidationException(String s) {
        super(s);
    }
}