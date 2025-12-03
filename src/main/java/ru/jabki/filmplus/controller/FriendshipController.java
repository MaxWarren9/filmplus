package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.Friend;
import ru.jabki.filmplus.service.FriendshipService;

import java.util.List;

@RestController
@RequestMapping("api/v1/friends")
@AllArgsConstructor
@Tag(name = "Друзья")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @PostMapping
    @Operation(summary = "Отправить запрос на дружбу")
    public Friend create(@RequestBody final Friend friend) {
        return friendshipService.sendRequest(friend);
    }

    @PatchMapping("/{id}/accept")
    @Operation(summary = "Принять запрос")
    public Friend accept(@PathVariable long id,
                         @RequestParam long actingUserId) {
        return friendshipService.accept(id, actingUserId);
    }

    @PatchMapping("/{id}/reject")
    @Operation(summary = "Отклонить запрос")
    public Friend reject(@PathVariable long id,
                         @RequestParam long actingUserId) {
        return friendshipService.reject(id, actingUserId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Посмотреть всех друзей")
    public List<Friend> getFriends(@PathVariable("id") long id) {
        return friendshipService.getAllFriends(id);
    }

    @GetMapping("/{id}/incoming")
    @Operation(summary = "Посмотреть все входящие заявки")
    public List<Friend> getIncomingRequests(@PathVariable("id") long id) {
        return friendshipService.getAllIncoming(id);
    }

    @GetMapping("/{id}/outgoing")
    @Operation(summary = "Посмотреть все исходящие заявки")
    public List<Friend> getOutgoingRequests(@PathVariable("id") long id) {
        return friendshipService.getAllOutgoing(id);
    }
}
