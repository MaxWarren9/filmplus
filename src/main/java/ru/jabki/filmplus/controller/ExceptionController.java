package ru.jabki.filmplus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.exception.FriendException;
import ru.jabki.filmplus.exception.LikeException;
import ru.jabki.filmplus.exception.ReviewException;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.ApiError;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiError> handleUserError(final UserException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(false, e.getMessage()));
    }

    @ExceptionHandler(FilmException.class)
    public ResponseEntity<ApiError> handleFilmError(final FilmException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(false, e.getMessage()));
    }

    @ExceptionHandler(FriendException.class)
    public ResponseEntity<ApiError> handleFriendError(final FriendException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(false, e.getMessage()));
    }

    @ExceptionHandler(LikeException.class)
    public ResponseEntity<ApiError> handleLikeError(final LikeException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(false, e.getMessage()));
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ApiError> handleReviewError(final ReviewException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiError(false, e.getMessage()));
    }
}
