package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.model.Review;
import ru.jabki.filmplus.repository.mapper.ReviewMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class ReviewRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.review(user_id, film_id, rate, review, created_at)
            VALUES (:user_id, :film_id, :rate, :review, NOW())
            RETURNING *;
            """;

    private static final String GET_ALL = """
            SELECT *
            FROM filmplus.review;
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM filmplus.review
            WHERE id = :id;
            """;

    private static final String GET_BY_USER_ID = """
            SELECT * 
            FROM filmplus.review
            WHERE user_id = :user_id;
            """;

    private static final String GET_BY_FILM_ID = """
            SELECT * 
            FROM filmplus.review
            WHERE film_id = :film_id;
            """;

    private static final String UPDATE = """
            UPDATE filmplus.review
            SET rate = :rate, review = :review, updated_at = NOW()
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE
            FROM filmplus.review
            WHERE id = :id;
            """;

    private final ReviewMapper reviewMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Review create(final Review review) {
        return jdbcTemplate.queryForObject(INSERT, reviewToSQL(review), reviewMapper);
    }

    public List<Review> getAllReviews() {
        return jdbcTemplate.query(GET_ALL, reviewMapper);
    }

    public Review getReviewById(final long id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), reviewMapper);
    }

    public List<Review> getAllReviewsByUser(final long userId) {
        return jdbcTemplate.query(GET_BY_USER_ID, new MapSqlParameterSource("user_id", userId), reviewMapper);
    }

    public List<Review> getAllReviewsByFilm(final long filmId) {
        return jdbcTemplate.query(GET_BY_FILM_ID, new MapSqlParameterSource("film_id", filmId), reviewMapper);
    }

    public Review update(final Review review) {
        return jdbcTemplate.queryForObject(UPDATE, reviewToSQL(review), reviewMapper);
    }

    public void delete(final long id) {
        jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
    }

    private MapSqlParameterSource reviewToSQL(Review review) {
        return new MapSqlParameterSource()
                .addValue("id", review.getId())
                .addValue("user_id", review.getUserId())
                .addValue("film_id", review.getFilmId())
                .addValue("rate", review.getRate())
                .addValue("review", review.getReview());
    }
}
