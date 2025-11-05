package ru.jabki.filmplus.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.jabki.filmplus.enums.Genre;

import java.time.LocalDate;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)

public class Film {
    long id;
    String filmName;
    String filmDescription;
    LocalDate filmReleaseDate;
    long filmDuration;
    Set<Genre> filmGenres;

    public Film(String filmName, String filmDescription, LocalDate filmReleaseDate, long filmDuration, Set<Genre> filmGenres) {
        this.filmName = filmName;
        this.filmDescription = filmDescription;
        this.filmReleaseDate = filmReleaseDate;
        this.filmDuration = filmDuration;
        this.filmGenres = filmGenres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    public LocalDate getFilmReleaseDate() {
        return filmReleaseDate;
    }

    public void setFilmReleaseDate(LocalDate filmReleaseDate) {
        this.filmReleaseDate = filmReleaseDate;
    }

    public long getFilmDuration() {
        return filmDuration;
    }

    public void setFilmDuration(long filmDuration) {
        this.filmDuration = filmDuration;
    }

    public Set<Genre> getFilmGenres() {
        return filmGenres;
    }

    public void setFilmGenres(Set<Genre> filmGenres) {
        this.filmGenres = filmGenres;
    }
}
