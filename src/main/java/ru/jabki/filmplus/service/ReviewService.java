package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Review;
import ru.jabki.filmplus.repository.ReviewRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(rollbackFor = Exception.class)
    public Review create(final Review review) {
        validateForCreate(review);
        return reviewRepository.create(review);
    }

    @Transactional(readOnly = true)
    public Review getReviewById(final long id) {
        return reviewRepository.getReviewById(id);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByUser(final long id) {
        return reviewRepository.getAllReviewsByUser(id);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewByFilmId(final long id) {
        return reviewRepository.getAllReviewsByFilm(id);
    }

    @Transactional(readOnly = true)
    public List<Review> getAll() {
        return reviewRepository.getAllReviews();
    }

    @Transactional(rollbackFor = Exception.class)
    public Review update(final Review review) {
        validateForUpdate(review);
        return reviewRepository.update(review);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        reviewRepository.delete(id);
    }

    private void validateForCreate(Review review) {
        if (review == null) {
            throw new BadRequestException("Данные отзыва не заполнены");
        }
        if (review.getUserId() <= 0) {
            throw new BadRequestException("Некорректный id пользователя");
        }
        if (review.getFilmId() <= 0) {
            throw new BadRequestException("Некорректный id фильма");
        }
        if (review.getRate() < 1 || review.getRate() > 5) {
            throw new BadRequestException("Рейтинг должен быть от 1 до 5");
        }
        if (!StringUtils.hasText(review.getReview())) {
            throw new BadRequestException("Текст отзыва не заполнен");
        }
    }

    private void validateForUpdate(Review review) {
        if (review == null) {
            throw new BadRequestException("Данные отзыва не заполнены");
        }
        if (review.getId() <= 0) {
            throw new BadRequestException("Некорректный id отзыва");
        }
        if (review.getRate() < 1 || review.getRate() > 5) {
            throw new BadRequestException("Рейтинг должен быть от 1 до 5");
        }
        if (!StringUtils.hasText(review.getReview())) {
            throw new BadRequestException("Текст отзыва не заполнен");
        }
    }
}
