package ru.jabki.filmplus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Like {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private long userId;
    private long filmId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createdAt;
}
