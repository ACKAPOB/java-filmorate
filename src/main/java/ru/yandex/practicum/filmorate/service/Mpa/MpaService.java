package ru.yandex.practicum.filmorate.service.Mpa;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class MpaService {
    private final MpaStorage mpaStorage;

    public MpaService(@Qualifier("mpaDbStorage") MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Optional<Mpa> getMpaToId (int id) {
        if (id < 0){
            throw new NotFoundException("Некорректный запрос id - " + id);
        }
        return mpaStorage.getMpaToId(id);
    }

    public Collection<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }
}
