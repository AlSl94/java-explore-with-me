package ru.practicum.ewm.error;

public class ConflictException extends RuntimeException {
    public ConflictException(String s) {
        super(s);
    }
}
