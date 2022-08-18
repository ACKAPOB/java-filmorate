package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j // Логирование с помощью Lombok
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Genre> getGerne(@PathVariable int id) {
        return genreService.getGenre(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Genre> getAllGernes() {
        return genreService.getAllGenres();
    }

}
