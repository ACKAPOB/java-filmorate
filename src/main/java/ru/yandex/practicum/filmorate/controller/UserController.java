package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController { //будет проверять корректность запроса и вызывать методы
    private final InMemoryUserStorage inMemoryUserStorage;
    private final UserService userService;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll(); // переделать
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
       return userService.updateUser(user); // переделать
    }

    //PUT /users/{id}/friends/{friendId} — добавление в друзья.
    @PutMapping("/users/{userId}/friends/{friendId}")
    public void userAddFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.userAddFriend(userId, friendId);
    }

    //DELETE /users/{id}/friends/{friendId} - удаление из друзей
    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public void userDelFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.userDelFriend(userId, friendId);
    }

    //GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
    @GetMapping("/users/{id}/friends")
    public List<User> findFriendsUser (@PathVariable int userId) {
        return userService.findFriendsUser(userId);
    }

    //GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.
    @GetMapping("/users/{userId}/friends/common/{otherId}")
    public List<User> findCommonFriends (@PathVariable int userId, @PathVariable int otherId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /feed ещё не реализован.");
    }
}
