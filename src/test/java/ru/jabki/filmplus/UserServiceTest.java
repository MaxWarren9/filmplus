package ru.jabki.filmplus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.UserRepository;
import ru.jabki.filmplus.service.UserService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_valid() {
        final User user = getUser();
        when(userRepository.insert(user)).thenReturn(user);
        User result = userService.create(user);
        assertThat(result).isEqualTo(user);
        verify(userRepository).insert(user);
    }

    @Test
    void updateUser_valid() {
        final User user = getUser();
        User updatedFromDb = User.builder()
                .id(user.getId())
                .name("newName")
                .login("NewLogin")
                .email("newemail@me.ru")
                .birthday(LocalDate.of(2003, 2, 15))
                .build();

        when(userRepository.update(user)).thenReturn(updatedFromDb);
        User result = userService.update(user);
        assertThat(result.getName()).isEqualTo("newName");
        assertThat(result.getLogin()).isEqualTo("NewLogin");
        assertThat(result.getEmail()).isEqualTo("newemail@me.ru");
        assertThat(result.getBirthday()).isEqualTo(LocalDate.of(2003, 2, 15));
        verify(userRepository).update(user);
    }

    @Test
    void getUser_valid() {
        final User user = getUser();
        when(userRepository.findById(user.getId())).thenReturn(user);
        User result = userService.getUserById(user.getId());
        assertThat(result).isEqualTo(user);
        verify(userRepository).findById(user.getId());
    }

    @Test
    void deleteUser_valid() {
        long id = 1L;
        doNothing().when(userRepository).delete(id);

        userService.delete(id);

        verify(userRepository).delete(id);
    }

    private User getUser() {
        return User
                .builder()
                .id(1L)
                .name("me")
                .login("Me")
                .email("me@me.ru")
                .birthday(LocalDate.of(2002, 1, 8))
                .build();
    }
}
