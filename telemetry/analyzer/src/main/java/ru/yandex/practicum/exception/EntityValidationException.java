package ru.yandex.practicum.exception;

public class EntityValidationException extends RuntimeException {

    public EntityValidationException(final String message) {
        super(message);
    }
}