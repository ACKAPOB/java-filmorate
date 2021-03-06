package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.validator.LocalDateMin;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class Film {
    private static int filmId = 0;
    //дата релиза — не раньше 28 декабря 1895 года;
    private static final LocalDate minDateRealease = LocalDate.of(1895, 12, 28);
    @EqualsAndHashCode.Exclude
    private int id;
    @NotEmpty(message = "Название не может быть пустым") // название не может быть пустым;
    private String name;
    @Size(min = 0, max = 200, message = "Количество символов в описании не должно превышать 200")
    private String description; //максимальная длина описания — 200 символов;
    @LocalDateMin(value = "1895-12-28")
    private LocalDate releaseDate;
    @Min(value = 0,message = "Продолжительность фильма должна быть положительной")
    private int duration; //продолжительность фильма должна быть положительной.
    public Film(@NonNull String name, String description, LocalDate releaseDate, int duration) {
        this.id = genId();
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }
    private int genId () {
        filmId++;
        return filmId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}