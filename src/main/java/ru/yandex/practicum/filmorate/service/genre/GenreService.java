package ru.yandex.practicum.filmorate.service.genre;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;

@Service
@Slf4j
@Data
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(@Qualifier("genreDbStorage") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Optional<Genre> getGenre (int id) {
        if (genreStorage.getGenre(id).isEmpty()) {
            throw new NotFoundException("Некорректный запрос id - " + id);
        }
        return genreStorage.getGenre(id);
    }

    public Collection<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

}
