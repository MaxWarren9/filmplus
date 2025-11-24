package ru.jabki.filmplus.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User
                .builder()
                .id(rs.getLong("id"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}
