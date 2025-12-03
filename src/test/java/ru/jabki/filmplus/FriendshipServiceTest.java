package ru.jabki.filmplus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.jabki.filmplus.enums.FriendshipStatus;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Friend;
import ru.jabki.filmplus.repository.FriendshipRepository;
import ru.jabki.filmplus.service.FriendshipService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FriendshipServiceTest {

    private FriendshipRepository repository;
    private FriendshipService service;

    @BeforeEach
    void setUp() {
        repository = mock(FriendshipRepository.class);
        service = new FriendshipService(repository);
    }

    @Test
    void testSendRequestValidationAndSuccess() {
        BadRequestException ex1 = assertThrows(BadRequestException.class, () -> service.sendRequest(null));
        assertEquals("Друг пустой", ex1.getMessage());

        Friend invalidUser = Friend.builder()
                                   .userId(0)
                                   .friendId(2)
                                   .build();
        BadRequestException ex2 = assertThrows(BadRequestException.class, () -> service.sendRequest(invalidUser));
        assertEquals("User ID некорректен", ex2.getMessage());

        Friend invalidFriend = Friend.builder()
                                     .userId(1)
                                     .friendId(0)
                                     .build();
        BadRequestException ex3 = assertThrows(BadRequestException.class, () -> service.sendRequest(invalidFriend));
        assertEquals("Friend ID некорректен", ex3.getMessage());

        Friend self = Friend.builder()
                            .userId(1)
                            .friendId(1)
                            .build();
        BadRequestException ex4 = assertThrows(BadRequestException.class, () -> service.sendRequest(self));
        assertEquals("User не может добавить себя", ex4.getMessage());

        Friend valid = Friend.builder()
                             .userId(1)
                             .friendId(2)
                             .build();
        when(repository.exists(1, 2)).thenReturn(true);
        BadRequestException ex5 = assertThrows(BadRequestException.class, () -> service.sendRequest(valid));
        assertEquals("Request already exists", ex5.getMessage());

        when(repository.exists(1, 2)).thenReturn(false);
        Friend saved = Friend.builder()
                             .id(10)
                             .userId(1)
                             .friendId(2)
                             .status(FriendshipStatus.PENDING)
                             .created(LocalDate.now())
                             .updated(LocalDate.now())
                             .build();
        when(repository.makeFriend(valid)).thenReturn(saved);

        Friend result = service.sendRequest(valid);
        assertEquals(10, result.getId());
        assertEquals(FriendshipStatus.PENDING, result.getStatus());
    }

    @Test
    void testAcceptReject() {
        Friend request = Friend.builder()
                               .id(5)
                               .userId(1)
                               .friendId(2)
                               .status(FriendshipStatus.PENDING)
                               .created(LocalDate.now())
                               .updated(LocalDate.now())
                               .build();
        when(repository.findById(5L)).thenReturn(request);
        when(repository.updateStatus(5L, "CONFIRMED"))
                .thenReturn(request.toBuilder()
                                   .status(FriendshipStatus.CONFIRMED)
                                   .build());
        when(repository.updateStatus(5L, "REJECTED"))
                .thenReturn(request.toBuilder()
                                   .status(FriendshipStatus.REJECTED)
                                   .build());

        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.accept(5, 1));
        assertEquals("Вы не можете принять эту заявку", ex.getMessage());

        Friend accepted = service.accept(5, 2);
        assertEquals(FriendshipStatus.CONFIRMED, accepted.getStatus());

        Friend rejected = service.reject(5, 2);
        assertEquals(FriendshipStatus.REJECTED, rejected.getStatus());
    }

    @Test
    void testGetAllLists() {
        when(repository.findAllFriends(1)).thenReturn(List.of());
        when(repository.findAllIncoming(1)).thenReturn(List.of());
        when(repository.findAllOutgoing(1)).thenReturn(List.of());

        assertNotNull(service.getAllFriends(1));
        assertNotNull(service.getAllIncoming(1));
        assertNotNull(service.getAllOutgoing(1));

        verify(repository).findAllFriends(1);
        verify(repository).findAllIncoming(1);
        verify(repository).findAllOutgoing(1);
    }
}
