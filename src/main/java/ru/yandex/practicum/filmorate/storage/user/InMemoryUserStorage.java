package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class InMemoryUserStorage implements UserStorage{
    //перенесите туда всю логику хранения, обновления и поиска объектов.
    private final Map<Integer, User> userList = new HashMap<>();
    @Override
    public User createUser(User user) {
        for (User out : userList.values()) {
            if (out.equals(user)) {
                throw new InvalidNameException("Пользователь с такими данными уже существует" + user.getEmail());
            }
        }
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userList.values());
    }

}
