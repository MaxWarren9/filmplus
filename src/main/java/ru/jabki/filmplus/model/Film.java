package ru.jabki.filmplus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.jabki.filmplus.enums.Genre;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String name;
    private String description;
    private LocalDate release;
    private Long duration;
    private Genre genre;
}
