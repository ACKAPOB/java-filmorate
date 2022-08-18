package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
@RestController
@Slf4j // Логирование с помощью Lombok
@Validated
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Post films id = {} {}", film.getId(), film.getName());
        return filmService.createFilm(film);
    } // ФЗ 11

    @DeleteMapping("/films/{filmId}")
    public void deleteFilm (@PathVariable int filmId) {
        log.info("Get film id={}", filmId);
        filmService.deleteFilm(filmId);
    } // ФЗ 11

    @GetMapping("/films")
    public Collection<Film> findAll() {
        log.info("Get films");
        return filmService.getAllFilms();
    } // ФЗ 11

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Put friends user id = {}", film.getId());
        return filmService.update(film);
    } // ФЗ 11

    //PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
    @PutMapping("/films/{filmId}/like/{userId}")
    public void userAddLikes(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Put like film id = {}, user id = {} ", filmId, userId);
        filmService.userAddLikes(filmId, userId);
    }

    //DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
    @DeleteMapping("/films/{filmId}/like/{userId}")
    public void userDelLikes(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Delete like film id = {}, user id = {} ", filmId, userId);
        filmService.userDelLikes(filmId, userId);
    }

    // GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков.
    // Если значение параметра count не задано, верните первые 10.
    @GetMapping("/films/popular")
    public Collection<Film> findTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Get popular films, count is - {}, defaultValue = \"10\"", count);
        return filmService.findTopFilms(count);
    }

    @GetMapping("/films/{filmId}")
    public Film getFilm (@PathVariable int filmId) {
        log.info("Get film id={}", filmId);
        return filmService.getFilm(filmId);
    }
}
