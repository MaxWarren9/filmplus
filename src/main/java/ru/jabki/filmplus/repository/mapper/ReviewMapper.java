package ru.jabki.filmplus.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReviewMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {

        LocalDateTime createdAt = rs.getTimestamp("created_at") != null
                ? rs.getTimestamp("created_at")
                    .toLocalDateTime()
                : null;
        LocalDate updatedAt = rs.getDate("updated_at") != null
                ? rs.getDate("updated_at")
                    .toLocalDate()
                : null;

        return Review.builder()
                     .id(rs.getLong("id"))
                     .userId(rs.getLong("user_id"))
                     .filmId(rs.getLong("film_id"))
                     .rate(rs.getLong("rate"))
                     .review(rs.getString("review"))
                     .createdAt(createdAt)
                     .updatedAt(updatedAt)
                     .build();
    }
}
