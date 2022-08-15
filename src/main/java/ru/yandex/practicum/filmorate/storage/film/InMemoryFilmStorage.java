package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Validated
@Data
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer, Film> filmList = new HashMap<>();
    int filmId = 0;

    @Override
    public Film create(Film film) {
        film.setId(genId());
        filmList.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteFilm(int filmId)  {
        filmList.remove(filmId);
    }

    @Override
    public Film update(Film film) {
        filmList.replace(film.getId(),film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(filmList.values());
    }

    private int genId () {
        filmId++;
        return filmId;
    }
    @Override
    public boolean isExists(Film film) {
        for (Film out : filmList.values()) {
            if (out.getName().equals(film.getName()) && out.getReleaseDate().equals(film.getReleaseDate()))
                return true;
        }
        return false;
    }

    @Override
    public Film getFilm(int id) {
        return null;
    }

    @Override
    public void userAddLikes(int filmId, int userId) {

    }

    @Override
    public void userDelLikes(int filmId, int userId) {

    }

    @Override
    public Collection<Film> findTopFilms(int count) {
        return null;
    }

}
