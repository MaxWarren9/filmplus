package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.enums.Genre;
import ru.jabki.filmplus.exception.FilmException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.repository.FilmRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    @Transactional(rollbackFor = Exception.class)
    public Film create(final Film film) {
        validate(film);
        return filmRepository.insert(film);
    }

    @Transactional(readOnly = true)
    public Film getFilmById(final long id) {
        return filmRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Film update(final Film film) {
        validate(film);
        return filmRepository.update(film);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        filmRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<Film> findByNameAndGenre(String name, Set<Genre> genres) {
        return filmRepository.findAll()
                             .stream()
                             .filter(film -> (name == null || name.isBlank() || film.getName()
                                                                                    .toLowerCase()
                                                                                    .contains(name)))
                             .filter(film -> (genres == null || film.getGenres()
                                                                    .stream()
                                                                    .anyMatch(genres::contains)))
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
        if (film.getRelease() == null) {
            throw new FilmException("Film release date is null");
        }
        if (film.getGenres()
                .isEmpty()) {
            throw new FilmException("Film genres are empty");
        }
    }
}
