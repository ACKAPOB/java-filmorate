package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    //Создайте интерфейсы UserStorage в которых будут определены методы добавления, удаления и модификации объектов.
    User createUser(User user);
    User delete(User user);
    User update(User user);

    List<User> getAll();
}
