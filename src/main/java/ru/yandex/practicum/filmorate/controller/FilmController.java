package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
Для FilmController:
добавление фильма;
обновление фильма;
получение всех фильмов.
 */

@RestController
@Validated
@Slf4j // тут с помощью Lombok
public class FilmController {

    private final Map<Integer, Film> filmList = new HashMap<>();

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.debug("Текущее количество фильмов: {}", filmList.size() + 1);
        for (Film out : filmList.values()) {
            if (out.equals(film)) {
                throw new InvalidNameException("Фильм с такими данными уже существует");
            }
        }
        filmList.put(film.getId(), film);
        return film;
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return new ArrayList<>(filmList.values());
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        if(film.getName() == null || film.getName().isBlank()) {
            throw new InvalidNameException("Имя не может быть пустым.");
        }
        for (Film out : filmList.values()) {
            if (out.getId() == film.getId()) {
                filmList.put(film.getId() ,film);
                break;
            } else {
                throw new InvalidNameException("Изменение не возможно, фильм отсутствует");
            }
        }
        return film;
    }
}
