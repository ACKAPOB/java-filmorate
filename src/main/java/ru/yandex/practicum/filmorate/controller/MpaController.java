package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.Mpa.MpaService;

import java.util.Collection;
import java.util.Optional;

@RestController
@Slf4j // Логирование с помощью Lombok
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Mpa> getMpa(@PathVariable int id) {
        return mpaService.getMpaToId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Mpa> getAllMpa() {
        return mpaService.getAllMpa();
    }
}
