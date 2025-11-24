package ru.jabki.filmplus.model;

import lombok.Builder;
import lombok.Data;
import ru.jabki.filmplus.enums.Genre;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    private long id;
    private String name;
    private String description;
    private LocalDate release;
    private Long duration;
    private Set<Genre> genres;
}
