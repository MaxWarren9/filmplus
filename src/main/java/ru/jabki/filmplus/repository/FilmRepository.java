package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Film;
import ru.jabki.filmplus.repository.mapper.FilmMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class FilmRepository {

    private static final String INSERT = """
            INSERT INTO filmplus.film(name, description, duration, release, genres)
            VALUES (:name, :description, :duration, :release, :genres)
            RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE filmplus.film
            SET name = :name, description = :description, duration = :duration, release = :release, genres = :genres
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.film
            WHERE id = :id;
            """;

    private static final String GET_BY_ID = """
            SELECT * 
            FROM filmplus.film
            WHERE id = :id;
            """;

    private static final String FIND_ALL = """
            SELECT * 
            FROM filmplus.film;
            """;

    private final FilmMapper filmMapper;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Film insert(final Film film) {
        return jdbcTemplate.queryForObject(INSERT, filmToSql(film), filmMapper);
    }

    public Film update(final Film film) {
        return jdbcTemplate.queryForObject(UPDATE, filmToSql(film), filmMapper);
    }

    public void delete(final long id) {
        try {
            jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
        } catch (Exception e) {
            throw new BadRequestException(String.format("Film with id %d not found", id));
        }
    }

    public Film findById(final long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), filmMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Film with id %d not found", id));
        }
    }

    public List<Film> findAll() {
        return jdbcTemplate.query(FIND_ALL, filmMapper);
    }

    private MapSqlParameterSource filmToSql(final Film film) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", film.getId());
        params.addValue("name", film.getName());
        params.addValue("description", film.getDescription());
        params.addValue("release", film.getRelease());
        params.addValue("duration", film.getDuration());
        params.addValue(
                "genres",
                film.getGenres()
                    .stream()
                    .map(Enum::name)
                    .toArray(String[]::new)
        );
        return params;
    }
}
