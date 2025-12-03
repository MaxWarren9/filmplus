package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.User;
import ru.jabki.filmplus.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public User create(final User user) {
        validate(user);
        return userRepository.insert(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(final Long id) {
        return userRepository.findById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public User update(final User user) {
        validate(user);
        return userRepository.update(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        userRepository.delete(id);
    }

    private void validate(final User user) {
        if (user == null) {
            throw new UserException("User не заполнен");
        }

        if (!StringUtils.hasText(user.getName())) {
            throw new UserException("Имя пользователя не заполнено");
        }

        if (!StringUtils.hasText(user.getEmail())) {
            throw new UserException("Почта пользователя не заполнена");
        }

        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("Логин пользователя не заполнен");
        }

        if (user.getBirthday() == null) {
            throw new UserException("День рождения пользователя не заполнен");
        }
    }
}
