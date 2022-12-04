package ru.practicum.ewm.error;

public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String s) {
        super(s);
    }
}