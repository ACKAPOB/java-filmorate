package ru.yandex.practicum.filmorate.service.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class UserService {
    // будет отвечать за такие операции с пользователями, как добавление в друзья, удаление из друзей, вывод списка
    // общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу. То есть если Лена стала
    // другом Саши, то это значит, что Саша теперь друг Лены.

    private final UserStorage userStorage;

    public User createUser(User user) {
        if (userStorage.getUserList().containsValue(user)) {
            throw new InvalidNameException("Пользователь с такими данными уже существует" + user.getEmail());
        }
        return userStorage.createUser(user);
    }

    public void deleteFilm(int filmId) {
        if (!userStorage.isExists(userStorage.getUserList().get(filmId))) {
            throw new InvalidNameException("Фильм с такими данными не существует");
        }
        userStorage.delete(filmId);
    }
    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User getUser(int userId) {
        if (!userStorage.getUserList().containsKey(userId)) {
            throw new NotFoundException("Некорректный запрос" + userId);
        }
        return userStorage.getUserList().get(userId);
    }

    public User updateUser (User user) {
        if (user.getId() < 0) {
            throw new NotFoundException(
                    "Некорректный запрос, id не может быть отрицательным " + user.getId() + " , " + user.getEmail());
        }
        if (!userStorage.getUserList().containsKey(user.getId())) {
            throw new NotFoundException("Некорректный запрос, пользователь с такими данными не существует "
                    + user.getId() + " , " + user.getEmail());
        }
        userStorage.getUserList().replace(user.getId(), user);
        return user;
    }

    public List<User> findFriendsUser (int userId) {
        List<User> users = new ArrayList<>();
        for (int out : userStorage.getUserList().get(userId).getFriends()) {
            users.add(userStorage.getUserList().get(out));
        }
        return users;
    }

    public List<User> findCommonFriends (int userId, int otherId) {
        List<User> out = new LinkedList<>(findFriendsUser(userId));
        out.retainAll(findFriendsUser(otherId));
        return out;
    }

    public void userAddFriend(int userId, int friendId) {
        if (userStorage.getUserList().get(userId) == null) {
            throw new NotFoundException(String.format("Пользователь (User) c id - %s не найден",userId));
        }
        if (userStorage.getUserList().get(friendId) == null) {
            throw new NotFoundException(String.format("Пользователь (Friend) c id - %s не найден",friendId));
        }
        userStorage.getUserList().get(userId).getFriends().add(friendId);
        userStorage.getUserList().get(friendId).getFriends().add(userId);
    }
    public void userDelFriend(int userId, int friendId) {
        if (userStorage.getUserList().get(userId) == null) {
            throw new NotFoundException(String.format("Пользователь (User) c id - %s не найден",userId));
        }
        if (userStorage.getUserList().get(friendId) == null) {
            throw new NotFoundException(String.format("Пользователь (Friend) c id - %s не найден",friendId));
        }
        userStorage.getUserList().get(userId).getFriends().remove(friendId);
        userStorage.getUserList().get(friendId).getFriends().remove(userId);
    }
}
