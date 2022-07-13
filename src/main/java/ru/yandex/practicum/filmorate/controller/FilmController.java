package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@Slf4j // Логирование с помощью Lombok
public class FilmController {
    private final Map<Integer, Film> filmList = new HashMap<>();
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final FilmService filmService;
    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
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

    //PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
    @PutMapping("/films/{filmId}/like/{userId}")
    public void userAddLikes(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.userAddLikes(filmId, userId);
        //throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод ещё не реализован.");
    }

    //DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
    @DeleteMapping("/films/{filmId}/like/{userId}")
    public void userDelLikes(@PathVariable String filmId, @PathVariable String userId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод ещё не реализован.");
    }

    // GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков.
    // Если значение параметра count не задано, верните первые 10.
    @GetMapping("/films/popular")
    public String findTopFilms(
            @RequestParam(defaultValue = "10") Integer count) {
        if (count < 0) {
            throw new IncorrectParameterException("count");
        }
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Метод ещё не реализован.");
    }
}
