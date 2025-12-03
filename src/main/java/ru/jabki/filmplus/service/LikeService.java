package ru.jabki.filmplus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabki.filmplus.exception.BadRequestException;
import ru.jabki.filmplus.model.Like;
import ru.jabki.filmplus.repository.LikeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional(rollbackFor = Exception.class)
    public Like create(final Like like) {
        validate(like);
        return likeRepository.insert(like);
    }

    @Transactional(readOnly = true)
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Like getLikeById(final long id) {
        return likeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Like> getLikesByUserId(final long id) {
        return likeRepository.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public List<Like> getLikesByFilmId(final long id) {
        return likeRepository.findByFilmId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Like update(final Like like) {
        validate(like);
        return likeRepository.update(like);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        likeRepository.delete(id);
    }

    private void validate(Like like) {
        if (like == null) {
            throw new BadRequestException("Данные лайка не заполнены");
        }
        if (like.getId() <= 0) {
            throw new BadRequestException("Некорректно заданы параметры id лайка");
        }
        if (like.getUserId() <= 0) {
            throw new BadRequestException("Некорректно заданы параметры id пользователя");
        }
        if (like.getFilmId() <= 0) {
            throw new BadRequestException("Некорректно заданы параметры id фильма");
        }
    }
}
