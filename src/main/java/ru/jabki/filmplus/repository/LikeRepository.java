package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.model.Like;
import ru.jabki.filmplus.repository.mapper.LikeMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class LikeRepository {

    private static final String CREATE = """
            INSERT INTO filmplus.like(user_id, film_id, created_at)
            VALUES (:user_id, :film_id, NOW())
            RETURNING *;
            """;

    private static final String GET_ALL_LIKES = """
            SELECT *
            FROM filmplus.like;
            """;

    private static final String GET_LIKE_BY_ID = """
            SELECT *
            FROM filmplus.like
            WHERE id = :id;
            """;

    private static final String GET_LIKES_BY_USER_ID = """
            SELECT *
            FROM filmplus.like
            WHERE user_id = :user_id;
            """;

    private static final String GET_LIKES_BY_FILM_ID = """
            SELECT *
            FROM filmplus.like
            WHERE film_id = :film_id;
            """;

    private static final String UPDATE = """
            UPDATE filmplus.like
            SET user_id = :user_id, film_id = :film_id, created_at = :created_at
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.like
            WHERE id = :id;
            """;

    private final LikeMapper likeMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Like insert(final Like like) {
        return jdbcTemplate.queryForObject(CREATE, likeToSql(like), likeMapper);
    }

    public Like update(final Like like) {
        return jdbcTemplate.queryForObject(UPDATE, likeToSql(like), likeMapper);
    }

    public List<Like> findAll() {
        return jdbcTemplate.query(GET_ALL_LIKES, likeMapper);
    }

    public Like findById(final long id) {
        return jdbcTemplate.queryForObject(GET_LIKE_BY_ID, new MapSqlParameterSource("id", id), likeMapper);
    }

    public List<Like> findByUserId(final long userId) {
        return jdbcTemplate.query(GET_LIKES_BY_USER_ID, new MapSqlParameterSource("user_id", userId), likeMapper);
    }

    public List<Like> findByFilmId(final long filmId) {
        return jdbcTemplate.query(GET_LIKES_BY_FILM_ID, new MapSqlParameterSource("film_id", filmId), likeMapper);
    }

    public void delete(final long id) {
        jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
    }

    private MapSqlParameterSource likeToSql(final Like like) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", like.getId());
        params.addValue("user_id", like.getUserId());
        params.addValue("film_id", like.getFilmId());
        params.addValue("created_at", like.getCreatedAt());
        return params;
    }
}
