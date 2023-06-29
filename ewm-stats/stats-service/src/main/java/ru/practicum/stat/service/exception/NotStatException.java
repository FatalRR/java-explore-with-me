package ru.practicum.stat.service.exception;

public class NotStatException extends RuntimeException {
    public NotStatException(String message) {
        super(message);
    }
}