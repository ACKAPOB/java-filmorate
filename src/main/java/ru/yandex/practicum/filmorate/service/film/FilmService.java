package ru.yandex.practicum.filmorate.service.film;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

// отвечать за операции с фильмами, — добавление и удаление лайка, вывод 10 наиболее популярных фильмов
// по количеству лайков. Пусть пока каждый пользователь может поставить лайк фильму только один раз.
@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class FilmService {
    private static final Set<Film> topFilmLikes = new TreeSet<>(new Comparator<Film>() {
        @Override
        public int compare(Film f1, Film f2) {
            return f1.getLikes().size() - f2.getLikes().size();
        }
    });
    private final InMemoryFilmStorage inMemoryFilmStorage; // внедрение зависимостей через конструктор.


    public void userAddLikes (Integer filmId, Integer userId) { // Принимает id фильма и id пользователя
        inMemoryFilmStorage.getFilmList().get(filmId).getLikes().add(userId);
    }
}
