package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.enums.Genre;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.service.FilmService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/film")
@AllArgsConstructor
@Tag(name = "Фильм")
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    @Operation(summary = "Создать фильм")
    public Film create(@RequestBody final Film film) {
        return filmService.create(film);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить фильм")
    public Film getById(@PathVariable("id") long id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить фильм")
    public Film update (@RequestBody final Film film) {
        return filmService.update(film);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить фильм")
    public void delete(@PathVariable("id") long id){
        filmService.delete(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Найти фильм")
    public List<Film> searchFilms (@RequestParam(name = "name", required = false) @Parameter(example = "Война миров") String name,
                                   @RequestParam(name = "genres", required = false) @Parameter(example = "COMEDY") Set<Genre> genres) {
        return filmService.findByNameAndGenre(name, genres);
    }
}
