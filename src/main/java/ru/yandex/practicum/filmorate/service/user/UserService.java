package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

@Service
@Slf4j
public class UserService {
    //будет отвечать за такие операции с пользователями, как добавление в друзья, удаление из друзей, вывод списка
    // общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу. То есть если Лена стала
    // другом Саши, то это значит, что Саша теперь друг Лены.

    private final InMemoryUserStorage inMemoryUserStorage;
    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void userAddFriend(int userId, int friendId) {
        inMemoryUserStorage.getUserList().get(userId).getFriends().add(friendId);
        System.out.println( inMemoryUserStorage.getUserList().get(userId).getFriends() + "Добавили в друзья");
        inMemoryUserStorage.getUserList().get(friendId).getFriends().add(userId);
        System.out.println( inMemoryUserStorage.getUserList().get(friendId).getFriends() + " Добавились в друзья");
    }
}
