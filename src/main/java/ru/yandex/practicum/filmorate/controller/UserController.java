package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@Slf4j
public class UserController {
    private final InMemoryUserStorage inMemoryUserStorage;
    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        inMemoryUserStorage.createUser(user);
        return user;
    }
    @GetMapping("/users")
    public List<User> findAll() {
        return inMemoryUserStorage.getAll();
    }
    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
       return inMemoryUserStorage.update(user);
    }
}
