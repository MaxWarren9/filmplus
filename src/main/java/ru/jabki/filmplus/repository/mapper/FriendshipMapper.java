package ru.jabki.filmplus.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabki.filmplus.enums.FriendshipStatus;
import ru.jabki.filmplus.model.Friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class FriendshipMapper implements RowMapper<Friend> {

    @Override
    public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDate created = null;
        if (rs.getDate("created") != null) {
            created = rs.getDate("created")
                        .toLocalDate();
        }

        LocalDate updated = null;
        if (rs.getDate("updated") != null) {
            updated = rs.getDate("updated")
                        .toLocalDate();
        }
        return Friend
                .builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .friendId(rs.getLong("friend_id"))
                .status(FriendshipStatus.valueOf(rs.getString("status")))
                .created(created)
                .updated(updated)
                .build();
    }
}
