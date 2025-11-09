package ru.jabki.filmplus.model;

import lombok.Getter;

@Getter
public class ApiError {
    private final boolean success;
    private final String message;

    public ApiError(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
