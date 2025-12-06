package ru.jabki.filmplus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.filmplus.enums.Genre;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.repository.FilmRepository;
import ru.jabki.filmplus.service.FilmService;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {
    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @Test
    void createFilm_valid() {
        final Film film = getFilm();
        when(filmRepository.insert(film)).thenReturn(film);
        Film result = filmService.create(film);
        assertThat(result).isEqualTo(film);
        verify(filmRepository).insert(film);
    }

    @Test
    void updateFilm_valid() {
        final Film film = getFilm();
        Film updatedFromDb = Film.builder()
                .id(film.getId())
                .name("Clash")
                .description("clashFilm")
                .release(LocalDate.of(2009,3,1))
                .duration(150L)
                .genres(Set.of(Genre.HORROR, Genre.COMEDY, Genre.ACTION))
                .build();

        when(filmRepository.update(film)).thenReturn(updatedFromDb);
        Film result = filmService.update(film);
        assertThat(result.getName()).isEqualTo("Clash");
        assertThat(result.getDescription()).isEqualTo("clashFilm");
        assertThat(result.getRelease()).isEqualTo(LocalDate.of(2009, 3, 1));
        assertThat(result.getDuration()).isEqualTo(150L);
        assertThat(result.getGenres()).isEqualTo(Set.of(Genre.HORROR, Genre.COMEDY, Genre.ACTION));
        verify(filmRepository).update(film);
    }

    @Test
    void getFilm_valid() {
        final Film film = getFilm();
        when(filmRepository.findById(film.getId())).thenReturn(film);
        Film result = filmService.getFilmById(film.getId());
        assertThat(result).isEqualTo(film);
        verify(filmRepository).findById(film.getId());
    }

    @Test
    void deleteFilm_valid() {
        long id = 1L;
        doNothing().when(filmRepository).delete(id);
        filmService.delete(id);
        verify(filmRepository).delete(id);
    }

    private Film getFilm() {
        return Film
                .builder()
                .id(1L)
                .name("Titanic")
                .description("Me")
                .release(LocalDate.of(2008,1,1))
                .duration(123L)
                .genres(Set.of(Genre.HORROR, Genre.ACTION))
                .build();
    }
}
