package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenreStorage {

    Optional<Genre> getGenre(int id);

    Collection<Genre> getAllGenres();

    void deleteGenresToFilm(int id);

    void recordGenresToFilm(int film_id, int genre_id);

    List<Genre> getGenresToFilm(int id);
}
