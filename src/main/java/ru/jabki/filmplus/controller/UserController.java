package ru.jabki.filmplus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.service.UserService;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
@Tag(name = "Пользователь")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public User create(@RequestBody final User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя")
    public User getById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя")
    public User update(@RequestBody final User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    public void delete(@PathVariable("id") long id) {
        userService.delete(id);
    }

}
