package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.InvalidNameException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Validated
public class InMemoryFilmStorage implements FilmStorage{

    private final Map<Integer, Film> filmList = new HashMap<>();

    @Override
    public Film create(Film film) {
        for (Film out : filmList.values()) {
            if (out.equals(film)) {
                throw new InvalidNameException("Фильм с такими данными уже существует");
            }
        }
        filmList.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
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

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(filmList.values());
    }
}
