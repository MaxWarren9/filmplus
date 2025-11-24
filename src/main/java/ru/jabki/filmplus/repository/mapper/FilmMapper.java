package ru.jabki.filmplus.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.enums.Genre;
import ru.jabki.filmplus.model.Film;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Array pgArray = rs.getArray("genres");

        Set<Genre> genres = pgArray == null
                ? Set.of()
                : Arrays.stream((String[]) pgArray.getArray())
                .map(Genre::valueOf)
                .collect(Collectors.toSet());

        return Film
                .builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .release(rs.getDate("release").toLocalDate())
                .duration(rs.getLong("duration"))
                .genres(genres)
                .build();
    }
}
