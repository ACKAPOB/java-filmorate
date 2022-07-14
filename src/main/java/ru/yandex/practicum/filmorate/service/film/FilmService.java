package ru.yandex.practicum.filmorate.service.film;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

// отвечать за операции с фильмами, — добавление и удаление лайка, вывод 10 наиболее популярных фильмов
// по количеству лайков. Пусть пока каждый пользователь может поставить лайк фильму только один раз.
@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage; // внедрение зависимостей через конструктор.

    public Film createFilm(Film film) {
        if (filmStorage.getFilmList().values().contains(film)) {
            throw new InvalidNameException("Фильм с такими данными уже существует");
        }
        return filmStorage.create(film);
    }
    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public Film getFilm(int filmId) {
        if (!filmStorage.getFilmList().containsKey(filmId)) {
            throw new NotFoundException("Некорректный запрос" + filmId);
        }
        return filmStorage.getFilmList().get(filmId);
    }

    public Film update(Film film) {
        if (film.getId() < 0) {
            throw new NotFoundException(
                    "Некорректный запрос, id не может быть отрицательным " + film.getId() + " , " + film.getName());
        }
        if (!filmStorage.getFilmList().containsKey(film.getId())) {
            throw new NotFoundException("Некорректный запрос, фильм с такими данными не существует "
                    + film.getId() + " , " + film.getName());
        }
        return filmStorage.update(film);
    }

    public void userAddLikes (int filmId, int userId) { // Принимает id фильма и id пользователя
        if (filmId < 0 || userId < 0 ) {
            throw new NotFoundException("Некорректный запрос,  " + filmId + " , " + userId);
        }
        for (Film out : filmStorage.getFilmList().values()) {
            if (out.getId() == filmId) {
                out.getLikes().add(userId);
            }
        }
    }

    public void userDelLikes(int filmId,  int userId) {
        if (filmId < 0 || userId < 0 ) {
            throw new NotFoundException("Некорректный запрос, " + filmId + " , " + userId);
        }
        filmStorage.getFilmList().get(filmId).getLikes().remove(userId);
    }

    public List<Film> findTopFilms(int count) {
        if (count < 0) {
            throw new IncorrectParameterException("Некорректный параметр count - " + count);
        }
        return filmStorage.getAll().stream().sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }
    private int compare(Film film, Film film1)  {
        return film1.getLikes().size() - film.getLikes().size();
    }
}
