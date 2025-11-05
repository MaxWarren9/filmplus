package ru.jabki.filmplus.service;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import ru.jabki.filmplus.exception.UserException;
import ru.jabki.filmplus.model.User;

import java.util.HashSet;

@Service
public class UserService {
    private static final HashSet<User> users = new HashSet<>();

    public User create(final User user) {
        validate(user);
        user.setId(users.size() + 1);
        users.add(user);
        return user;
    }

    public User getUserById(final Long id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserException("User not found"));
    }

    public User update(final User user) {
        validate(user);
        final User existUser = getUserById(user.getId());
        existUser.setLogin(user.getLogin());
        existUser.setName(user.getName());
        existUser.setEmail(user.getEmail());
        existUser.setBirthday(user.getBirthday());
        return user;
    }

    public void delete(long id){
        users.remove(getUserById(id));
    }

    private void validate(final User user) {
        if (user == null) {
            throw new UserException("User is null");
        }

        if (!StringUtils.hasText(user.getName())) {
            throw new UserException("User name is empty");
        }

        if (!StringUtils.hasText(user.getEmail())) {
            throw new UserException("User mail is empty");
        }

        if (!StringUtils.hasText(user.getLogin())) {
            throw new UserException("User login is empty");
        }

        if (user.getBirthday() == null) {
            throw new UserException("User birthday not found");
        }
    }
}
