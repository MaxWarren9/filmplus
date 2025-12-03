package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.mapper.UserMapper;

@Repository
@AllArgsConstructor
public class UserRepository {

    private static final String INSERT = """
                INSERT INTO filmplus.user(login, name, email, birthday)
                VALUES (:login, :name, :email, :birthday)
                RETURNING *;
            """;

    private static final String UPDATE = """
            UPDATE filmplus.user
            SET login = :login, name = :name, email = :email, birthday = :birthday
            WHERE id = :id
            RETURNING *;
            """;

    private static final String DELETE = """
            DELETE FROM filmplus.user
            WHERE id = :id;
            """;
    private static final String GET_BY_ID = """
            SELECT * 
            FROM filmplus.user
            WHERE id = :id;
            """;

    private final UserMapper userMapper;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public User insert(final User user) {
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    public User update(final User user) {
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    public void delete(final long id) {
        try {
            jdbcTemplate.update(DELETE, new MapSqlParameterSource("id", id));
        } catch (Exception e) {
            throw new BadRequestException(String.format("User with id %d not found", id));
        }
    }

    public User findById(final long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("User with id %d not found", id));
        }
    }

    private MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", user.getId());
        parameterSource.addValue("login", user.getLogin());
        parameterSource.addValue("name", user.getName());
        parameterSource.addValue("email", user.getEmail());
        parameterSource.addValue("birthday", user.getBirthday());
        return parameterSource;
    }
}
