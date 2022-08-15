package ru.yandex.practicum.filmorate.service.film;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

// отвечать за операции с фильмами, — добавление и удаление лайка, вывод 10 наиболее популярных фильмов
// по количеству лайков. Пусть пока каждый пользователь может поставить лайк фильму только один раз.
@Service
@Slf4j
@Data
public class FilmService {
    private final FilmStorage filmStorage; // внедрение зависимостей через конструктор.
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       @Qualifier("genreDbStorage")GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreStorage = genreStorage;
    }
    public Film createFilm(Film film) {
        Film newFilm = filmStorage.create(film);
        film.setId(newFilm.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genreStorage.recordGenresToFilm(film.getId(), genre.getId());
            }
        }
        return film;
    }

    public void deleteFilm(int filmId) {
        filmStorage.deleteFilm(filmId);
    }
    public Collection<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public Film getFilm(int filmId) {
        if (filmId < 0) {
            throw new NotFoundException(
                    "Некорректный запрос, id не может быть отрицательным " + filmId);
        }
        return filmStorage.getFilm(filmId);
    }

    public Film update(Film film) {
        if (film.getId() < 0) {
            throw new NotFoundException("Некорректный запрос, фильм с такими данными не существует "
                    + film.getId() + " , " + film.getName());
        }
        genreStorage.deleteGenresToFilm(film.getId());
        filmStorage.update(film);
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genreStorage.recordGenresToFilm(film.getId(), genre.getId());
            }
        }
        return getFilm(film.getId());
    }

    public void userAddLikes (int filmId, int userId) { // Принимает id фильма и id пользователя
        filmStorage.userAddLikes(filmId, userId);
    }

    public void userDelLikes(int filmId,  int userId) {
        if (filmStorage.getFilm(filmId) == null || userStorage.getUser(userId).isEmpty()) {
            throw new NotFoundException("Некорректный запрос,  " + filmId + " , " + userId);
        }
        filmStorage.userDelLikes(filmId, userId);
    }

    public Collection<Film> findTopFilms(int count) {
        if (count < 0) {
            throw new IncorrectParameterException("Некорректный параметр count - " + count);
        }
        return filmStorage.findTopFilms(count);
    }
    private int compare(Film film, Film film1)  {
        return film1.getLikes().size() - film.getLikes().size();
    }
}
