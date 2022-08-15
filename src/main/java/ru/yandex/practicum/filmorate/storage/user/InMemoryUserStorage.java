package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Data
public class InMemoryUserStorage implements UserStorage{
    //перенесите туда всю логику хранения, обновления и поиска объектов.
    private final Map<Integer, User> userList = new HashMap<>();
    int userId = 0;
    @Override
    public User createUser(User user) {
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(int userId) {
        userList.remove(userId);
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

    private int genId () {
        userId++;
        return userId;
    }

    @Override
    public boolean isExists(User user) {
        for (User out : userList.values()) {
            if (out.getEmail().equals(user.getEmail()))
                return true;
        }
        return false;
    }

    @Override
    public void userAddFriend(int userId, int friendId) {
        return;
    }

    @Override
    public Optional<User> getUser(int userId) {
        return null;
    }

    @Override
    public Collection<User> findFriendsUser(int userId) {
        return null;
    }

    @Override
    public void userDelFriend(int userId, int friendId) {

    }

    @Override
    public Collection<User> findCommonFriends(int userId, int otherId) {
        return null;
    }

}
