package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.LocalDateMin;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data

public class Film {
    //дата релиза — не раньше 28 декабря 1895 года;
    private static final LocalDate minDateRealease = LocalDate.of(1895, 12, 28);
    @EqualsAndHashCode.Exclude
    private int id;
    @NotEmpty(message = "Название не может быть пустым") // название не может быть пустым;
    @EqualsAndHashCode.Include
    private String name;
    @Size(min = 0, max = 200, message = "Количество символов в описании не должно превышать 200")
    @EqualsAndHashCode.Exclude
    private String description; //максимальная длина описания — 200 символов;
    @LocalDateMin(value = "1895-12-28")
    @EqualsAndHashCode.Include
    private LocalDate releaseDate;
    @Min(value = 0,message = "Продолжительность фильма должна быть положительной")
    @EqualsAndHashCode.Exclude
    private int duration; //продолжительность фильма должна быть положительной.
    @EqualsAndHashCode.Exclude
    private final Set<Integer> likes = new HashSet(); // Список Id лайкнувших узеров

    private final Mpa mpa;
    LinkedHashSet<Genre> genres;

    public Film(@NonNull String name, String description, LocalDate releaseDate, int duration, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.mpa = mpa;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}