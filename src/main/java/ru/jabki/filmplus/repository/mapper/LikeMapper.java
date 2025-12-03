package ru.jabki.filmplus.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikeMapper implements RowMapper<Like> {

    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Like
                .builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .filmId(rs.getLong("film_id"))
                .createdAt(rs.getDate("created_at")
                             .toLocalDate())
                .build();
    }
}
