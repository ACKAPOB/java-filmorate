package ru.yandex.practicum.filmorate.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String s) {
        super(s);
    }
}
