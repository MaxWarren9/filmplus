package ru.jabki.filmplus.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter

public class ApiError {
    boolean success;
    String message;

    public ApiError(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
