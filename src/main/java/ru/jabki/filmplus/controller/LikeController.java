package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.Like;
import ru.jabki.filmplus.service.LikeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/likes")
@AllArgsConstructor
@Tag(name = "Лайки")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    @Operation(summary = "Поставить лайк")
    public Like create(@RequestBody final Like like) {
        return likeService.create(like);
    }

    @GetMapping()
    @Operation(summary = "Получить все лайки")
    public List<Like> getAllLikes() {
        return likeService.getAllLikes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить лайк по id")
    public Like getLikeByLikeId(@PathVariable("id") long id) {
        return likeService.getLikeById(id);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Получить лайки по id пользователя")
    public List<Like> getLikeByUserId(@PathVariable("id") long id) {
        return likeService.getLikesByUserId(id);
    }

    @GetMapping("/film/{id}")
    @Operation(summary = "Получить лайки по id фильма")
    public List<Like> getLikeByFilmId(@PathVariable("id") long id) {
        return likeService.getLikesByFilmId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить лайк")
    public Like update(@RequestBody final Like like) {
        return likeService.update(like);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить лайк")
    public void delete(@PathVariable("id") long id) {
        likeService.delete(id);
    }

}
