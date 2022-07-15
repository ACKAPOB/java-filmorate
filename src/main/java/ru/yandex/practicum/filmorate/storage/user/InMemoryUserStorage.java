package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import org.springframework.stereotype.Component;
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
    int userId = 0;
    @Override
    public User createUser(User user) {
        user.setId(genId());
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

}
