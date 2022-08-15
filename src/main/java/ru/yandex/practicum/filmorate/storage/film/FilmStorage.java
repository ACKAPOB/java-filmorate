package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);
    void deleteFilm(int filmId);
    Film update(Film film);
    Collection<Film> getAll();
    Map<Integer, Film> getFilmList();
    boolean isExists(Film film);

    Film getFilm(int id);

    void userAddLikes(int filmId, int userId);

    void userDelLikes(int filmId, int userId);

    Collection<Film> findTopFilms(int count);
}
