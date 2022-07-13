package ru.yandex.practicum.filmorate.service.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
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
    //будет отвечать за такие операции с пользователями, как добавление в друзья, удаление из друзей, вывод списка
    // общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу. То есть если Лена стала
    // другом Саши, то это значит, что Саша теперь друг Лены.

    private final UserStorage userStorage;

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User updateUser (User user) {
        for (User out : userStorage.getUserList().values()) {
            if (out.getId() == user.getId()) {
                user.setId(out.getId());
                userStorage.updateUser(user);
                return user;
            }
        }
        throw new UserNotFoundException("Изменение не возможно, " +
                "Пользователь с такими данными не существует " + user.getEmail());
    }

    public List<User> findFriendsUser (int userId) {
        List<User> users = new ArrayList<>();
        for (User out : userStorage.getUserList().values()) {
            if (out.getId() == userId) {
                for (int outint : out.getFriends()) {
                    users.add(userStorage.getUserList().get(outint));
                }
            }
        } return users;
    }

    public List<User> findCommonFriends (int userId, int otherId) {
        List<User> a = new LinkedList<>(findFriendsUser(userId));
        a.retainAll(findFriendsUser(otherId));
        return a;
    }

    public void userAddFriend(int userId, int friendId) {
        User user = userStorage.getUserList().get(userId);
        User friend = userStorage.getUserList().get(friendId);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь (User) c id - %s не найден",userId));
        }
        if (friend == null) {
            throw new UserNotFoundException(String.format("Пользователь (Friend) c id - %s не найден",friendId));
        }
        userStorage.getUserList().get(userId).getFriends().add(friendId);
        userStorage.getUserList().get(friendId).getFriends().add(userId);
    }
    public void userDelFriend(int userId, int friendId) {
    }
}
