package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.enums.Genre;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.model.Film;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {
    private static final HashSet<Film> films = new HashSet<>();

    public Film create(final Film film) {
        validate(film);
        film.setId(films.size() + 1);
        films.add(film);
        return film;
    }

    public Film getFilmById(final long id) {
        return films.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new FilmException("Film not found"));
    }

    public Film update(final Film film) {
        validate(film);
        final Film existFilm = getFilmById(film.getId());
        existFilm.setName(film.getName());
        existFilm.setDescription(film.getDescription());
        existFilm.setReleaseDate(film.getReleaseDate());
        existFilm.setDuration(film.getDuration());
        existFilm.setGenres(film.getGenres());
        return film;
    }

    public void delete(long id) {
        films.remove(getFilmById(id));
    }

    public List<Film> findByNameAndGenre(String name, Set<Genre> genres) {
        return films.stream()
                .filter(film -> (name == null || name.isBlank() || film.getName().toLowerCase().contains(name)))
                .filter(film -> (genres == null || film.getGenres().stream().anyMatch(genres :: contains)))
                .toList();
    }

    private void validate(final Film film) {
        if (film == null) {
            throw new FilmException("Film is null");
        }
        if (!StringUtils.hasText(film.getName())) {
            throw new FilmException("Film name is empty");
        }
        if (!StringUtils.hasText(film.getDescription())) {
            throw new FilmException("Film description is empty");
        }
        if (film.getDuration() == null) {
            throw new FilmException("Film duration is null");
        }
        if (film.getReleaseDate() == null) {
            throw new FilmException("Film release date is null");
        }
        if (film.getGenres().isEmpty()) {
            throw new FilmException("Film genres are empty");
        }

    }

}
