package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
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
    public void delete(long id){
        userRepository.delete(id);
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
