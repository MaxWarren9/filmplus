package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Friend;
import ru.jabki.filmplus.repository.FriendshipRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    @Transactional(rollbackFor = Exception.class)
    public Friend sendRequest(final Friend friend) {
        validate(friend);
        if (friendshipRepository.exists(friend.getUserId(), friend.getFriendId())) {
            throw new BadRequestException("Request already exists");
        }
        return friendshipRepository.makeFriend(friend);
    }

    @Transactional(readOnly = true)
    public List<Friend> getAllFriends(final long userId) {
        return friendshipRepository.findAllFriends(userId);
    }

    @Transactional(readOnly = true)
    public List<Friend> getAllIncoming(final long userId) {
        return friendshipRepository.findAllIncoming(userId);
    }

    @Transactional(readOnly = true)
    public List<Friend> getAllOutgoing(final long userId) {
        return friendshipRepository.findAllOutgoing(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Friend accept(long friendshipId, long actingUserId) {
        Friend f = friendshipRepository.findById(friendshipId);
        if (f.getFriendId() != actingUserId) {
            throw new BadRequestException("Вы не можете принять эту заявку");
        }
        return friendshipRepository.updateStatus(friendshipId, "CONFIRMED");
    }

    @Transactional(rollbackFor = Exception.class)
    public Friend reject(long friendshipId, long actingUserId) {
        Friend f = friendshipRepository.findById(friendshipId);
        if (f.getFriendId() != actingUserId) {
            throw new BadRequestException("Вы не можете отклонить эту заявку");
        }
        return friendshipRepository.updateStatus(friendshipId, "REJECTED");
    }

    private void validate(Friend friend) {
        if (friend == null) {
            throw new BadRequestException("Друг пустой");
        }
        if (friend.getUserId() <= 0) {
            throw new BadRequestException("User ID некорректен");
        }
        if (friend.getFriendId() <= 0) {
            throw new BadRequestException("Friend ID некорректен");
        }
        if (friend.getUserId() == friend.getFriendId()) {
            throw new BadRequestException("User не может добавить себя");
        }
    }
}
