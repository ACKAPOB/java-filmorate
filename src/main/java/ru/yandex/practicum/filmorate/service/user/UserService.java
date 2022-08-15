package ru.yandex.practicum.filmorate.service.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@Slf4j
@Data
public class UserService {
    // будет отвечать за такие операции с пользователями, как добавление в друзья, удаление из друзей, вывод списка
    // общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу. То есть если Лена стала
    // другом Саши, то это значит, что Саша теперь друг Лены.

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public void deleteUser(int userId) {
        userStorage.delete(userId);
    }
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public Optional<User> getUser(int userId) {
        if (userStorage.getUser(userId).isEmpty()) {
            throw new NotFoundException("Некорректный запрос" + userId);
        }
        return userStorage.getUser(userId);
    }

    public User updateUser (User user) {
        if (user.getId() < 0) {
            throw new NotFoundException(
                    "Некорректный запрос, id не может быть отрицательным " + user.getId() + " , " + user.getEmail());
        }
        userStorage.updateUser(user);
        return user;
    }

    public Collection<User> findFriendsUser (int userId) {
        return userStorage.findFriendsUser(userId);
    }

    public Collection<User> findCommonFriends (int userId, int otherId) {
        return  userStorage.findCommonFriends(userId, otherId);
    }

    public void userAddFriend(int userId, int friendId) {
        if (userStorage.getUser(userId).isEmpty() || userStorage.getUser(friendId).isEmpty()) {
            throw new NotFoundException("Некорректный запрос" + userId);
        }
        userStorage.userAddFriend(userId, friendId);
    }
    public void userDelFriend(int userId, int friendId) {

        userStorage.userDelFriend(userId, friendId);
    }
}
