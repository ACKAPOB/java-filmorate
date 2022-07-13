package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    //Создайте интерфейсы UserStorage в которых будут определены методы добавления, удаления и модификации объектов.
    User createUser(User user);
    User delete(User user);
    User updateUser(User user);
    List<User> findAll();
    Map<Integer, User> getUserList();
}
