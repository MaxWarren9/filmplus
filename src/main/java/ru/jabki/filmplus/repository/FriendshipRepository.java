package ru.jabki.filmplus.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Friend;
import ru.jabki.filmplus.repository.mapper.FriendshipMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class FriendshipRepository {

    private static final String FIND_BY_ID = """
            SELECT * FROM filmplus.friendship
            WHERE id = :id
            """;

    private static final String REQUEST_FRIENDS = """
            INSERT INTO filmplus.friendship(user_id, friend_id, status, created)
            VALUES (:user_id, :friend_id, 'PENDING', NOW())
            RETURNING *;
            """;

    private static final String UPDATE_STATUS = """
            UPDATE filmplus.friendship
            SET status = :status, updated = NOW()
            WHERE id = :id
            RETURNING *
            """;

    private static final String ACCEPT_FRIEND = """
            UPDATE filmplus.friendship
            SET status = 'CONFIRMED', updated = NOW()
            WHERE friend_id = :friend_id AND user_id = :user_id AND status = 'PENDING'
            RETURNING *; 
            """;

    private static final String REJECT_FRIEND = """
            UPDATE filmplus.friendship
            SET status = 'REJECTED', updated = NOW()
            WHERE friend_id = :friend_id AND user_id = :user_id AND (status = 'PENDING' OR status = 'CONFIRMED')
            RETURNING *; 
            """;

    private static final String GET_ALL_FRIENDS = """
            SELECT * FROM filmplus.friendship
            WHERE (user_id = :user_id OR friend_id = :user_id)
                    AND status = 'CONFIRMED';
            """;

    private static final String GET_ALL_INCOMING = """
            SELECT * FROM filmplus.friendship
            WHERE (user_id = :user_id)
                    AND status = 'PENDING';
            """;

    private static final String GET_ALL_OUTGOING = """
            SELECT * FROM filmplus.friendship
            WHERE (friend_id = :user_id)
                    AND status = 'PENDING';
            """;

    private static final String CHECK_EXISTS = """
            SELECT COUNT(*)
            FROM filmplus.friendship
            WHERE user_id = :user_id AND friend_id = :friend_id
            """;

    private final FriendshipMapper friendshipMapper;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Friend makeFriend(final Friend friend) {
        return jdbcTemplate.queryForObject(REQUEST_FRIENDS, friendToSQL(friend), friendshipMapper);
    }

    public Friend findById(long friendshipId) {
        var params = new MapSqlParameterSource("id", friendshipId);
        var list = jdbcTemplate.query(FIND_BY_ID, params, friendshipMapper);
        if (list.isEmpty()) {
            throw new RuntimeException("Заявка с таким id не найдена");
        }
        return list.get(0);
    }

    public Friend updateStatus(long friendshipId, String status) {
        var params = new MapSqlParameterSource()
                .addValue("id", friendshipId)
                .addValue("status", status);
        return jdbcTemplate.queryForObject(UPDATE_STATUS, params, friendshipMapper);
    }

    public Friend acceptFriend(final Friend friend) {
        return jdbcTemplate.queryForObject(ACCEPT_FRIEND, friendToSQL(friend), friendshipMapper);
    }

    public Friend rejectFriend(final Friend friend) {
        return jdbcTemplate.queryForObject(REJECT_FRIEND, friendToSQL(friend), friendshipMapper);
    }

    public List<Friend> findAllFriends(long userId) {
        try {
            return jdbcTemplate.query(GET_ALL_FRIENDS, new MapSqlParameterSource("user_id", userId), friendshipMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Friends for user with id %d not found", userId));
        }
    }

    public List<Friend> findAllIncoming(long userId) {
        try {
            return jdbcTemplate.query(GET_ALL_INCOMING, new MapSqlParameterSource("user_id", userId), friendshipMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Incoming requests for user with id %d not found", userId));
        }

    }

    public List<Friend> findAllOutgoing(long userId) {
        try {
            return jdbcTemplate.query(GET_ALL_OUTGOING, new MapSqlParameterSource("user_id", userId), friendshipMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Incoming requests for user with id %d not found", userId));
        }
    }

    public boolean exists(long userId, long friendId) {
        var params = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("friend_id", friendId);

        return jdbcTemplate.queryForObject(CHECK_EXISTS, params, Integer.class) > 0;
    }

    private MapSqlParameterSource friendToSQL(final Friend friend) {
        return new MapSqlParameterSource()
                .addValue("user_id", friend.getUserId())
                .addValue("friend_id", friend.getFriendId());
    }
}
