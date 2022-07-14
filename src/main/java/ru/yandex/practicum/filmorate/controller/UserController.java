package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController { //будет проверять корректность запроса и вызывать методы
    private final InMemoryUserStorage inMemoryUserStorage;
    private final UserService userService;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        log.info("Post user Email = {}", user.getEmail());
        return userService.createUser(user);
    }
    @GetMapping("/users")
    public List<User> findAll() {
        log.info("FindAll user");
        return userService.findAll(); // переделать
    }
    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Put friends user id = {}", user.getId());
        return userService.updateUser(user); // переделать
    }

    //PUT /users/{id}/friends/{friendId} — добавление в друзья.
    @PutMapping("/users/{userId}/friends/{friendId}")
    public void userAddFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Put friends user id = {}, friends = {}", userId, friendId);
        userService.userAddFriend(userId, friendId);
    }

    //DELETE /users/{id}/friends/{friendId} - удаление из друзей
    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public void userDelFriend(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Delete friends user id = {}, friends = {}", userId, friendId);
        userService.userDelFriend(userId, friendId);
    }

    //GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
    @GetMapping("/users/{userId}/friends")
    public List<User> findFriendsUser (@PathVariable("userId") int userId) {
        log.info("Get friends user id = {}", userId);
        return userService.findFriendsUser(userId);
    }

    //GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.
    @GetMapping("/users/{userId}/friends/common/{otherId}")
    public List<User> findCommonFriends (@PathVariable int userId, @PathVariable int otherId) {
       return userService.findCommonFriends(userId,otherId);
    }

    @GetMapping("/users/{userId}")
    public User getUser (@PathVariable int userId) {
        log.info("Get user id = {}", userId);
        return userService.getUser(userId);
        //throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод /feed ещё не реализован."); -- на будущее
    }
}
