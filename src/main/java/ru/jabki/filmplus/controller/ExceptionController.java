package ru.jabki.filmplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.ApiError;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleUserError(final UserException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(false, exception.getMessage()));
    }

    @ExceptionHandler(FilmException.class)
    public ResponseEntity<ApiError> handleFilmError(final FilmException exception) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(false, exception.getMessage()));
    }
}
