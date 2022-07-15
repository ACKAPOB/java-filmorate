package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {
    Film create(Film film);
    void deleteFilm(int filmId);
    Film update(Film film);
    List<Film> getAll();
    Map<Integer, Film> getFilmList();

    boolean isExists(Film film);

}
