package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class UserController {

    private final Map<Integer, User> userList = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class); // тут без Lombok

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        log.debug("Текущее количество пользователей: {}", userList.size() + 1);
        for (User out : userList.values()) {
            if (out.equals(user)) {
                throw new InvalidNameException("Пользователь с такими данными уже существует");
            }
        }
        userList.put(user.getId(), user);
        return user;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return new ArrayList<>(userList.values());
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        for (User out : userList.values()) {
            if (out.getId() == user.getId()) {
                user.setId(out.getId());
                userList.put(out.getId(), user);
                return user;
            }
        }
        throw new InvalidNameException("Изменение не возможно, пользователь отсутствует");
    }
}
