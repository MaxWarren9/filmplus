package ru.jabki.filmplus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class Review {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private long userId;
    private long filmId;
    private long rate;
    private String review;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate updatedAt;
}
