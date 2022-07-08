package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//шщшщщш
@RestController
@Validated
@Slf4j // тут с помощью Lombok
public class FilmController {
    private final Map<Integer, Film> filmList = new HashMap<>();
    private final InMemoryFilmStorage inMemoryFilmStorage;
    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }
    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.create(film);
    }
    @GetMapping("/films")
    public List<Film> findAll() {
        return inMemoryFilmStorage.getAll();
    }
    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        return inMemoryFilmStorage.update(film);
    }
}
