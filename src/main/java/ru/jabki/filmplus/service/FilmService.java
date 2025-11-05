package ru.jabki.filmplus.service;

import org.springframework.stereotype.Service;
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
        film.setId(films.size() + 1);
        films.add(film);
        return film;
    }

    public Film getFilmById(final long id) {
        final Film film = films.
                stream().
                filter(u -> u.getId() == id).
                findFirst().
                orElse(null);
        if (film == null) {
            throw new FilmException("Film not found");
        }
        return film;
    }

    public Film update(final Film film) {
        final Film existFilm = getFilmById(film.getId());
        existFilm.setFilmName(film.getFilmName());
        existFilm.setFilmDescription(film.getFilmDescription());
        existFilm.setFilmReleaseDate(film.getFilmReleaseDate());
        existFilm.setFilmDuration(film.getFilmDuration());
        existFilm.setFilmGenres(film.getFilmGenres());
        return film;
    }

    public void delete(long id) {
        films.remove(getFilmById(id));
    }

    public List<Film> findByNameAndGenre(String name, Set<Genre> genres) {
        return films.stream()
                .filter(film -> (name == null || name.isBlank() || film.getFilmName().toLowerCase().contains(name)))
                .filter(film -> (genres == null || film.getFilmGenres().stream().anyMatch(genres :: contains)))
                .toList();
    }

}
