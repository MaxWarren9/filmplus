package ru.jabki.filmplus.exception;

import java.util.function.Supplier;

public class FilmException extends RuntimeException {
    public FilmException(String message) {
        super(message);
    }
}
