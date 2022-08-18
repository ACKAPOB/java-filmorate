package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {
    //Создайте интерфейсы UserStorage в которых будут определены методы добавления, удаления и модификации объектов.
    User createUser(User user);
    void delete(int userId);
    User updateUser(User user);
    Collection<User> findAll();
    Map<Integer, User> getUserList();
    boolean isExists(User user);

    void userAddFriend(int userId, int friendId);

    Optional<User> getUser(int userId);

    Collection<User> findFriendsUser(int userId);

    void userDelFriend(int userId, int friendId);

    Collection<User> findCommonFriends(int userId, int otherId);
}
