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
import ru.jabki.filmplus.model.Review;
import ru.jabki.filmplus.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
@AllArgsConstructor
@Tag(name = "Отзывы")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Создать отзыв")
    public Review create(@RequestBody final Review review) {
        return reviewService.create(review);
    }

    @GetMapping()
    @Operation(summary = "Получить все отзывы")
    public List<Review> getAll() {
        return reviewService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить отзыв по id")
    public Review getReviewById(@PathVariable("id") long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Получить отзыв по id пользователя")
    public List<Review> getReviewsByUserId(@PathVariable("id") long id) {
        return reviewService.getReviewsByUser(id);
    }

    @GetMapping("/film/{id}")
    @Operation(summary = "Получить отзыв по id фильма")
    public List<Review> getReviewsByFilmId(@PathVariable("id") long id) {
        return reviewService.getReviewByFilmId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить отзыв")
    public Review update(@PathVariable long id, @RequestBody final Review review) {
        review.setId(id);
        return reviewService.update(review);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отзыв")
    public void delete(@PathVariable("id") final long id) {
        reviewService.delete(id);
    }

}
