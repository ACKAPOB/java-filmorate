package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validator.LocalDateMin;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Slf4j
public class Film {
    // В подобных случаях можно воспользоваться одной аннотацией @Data. Она позволяет генерировать геттеры, сеттеры,
    // методы toString(), equals() и hashCode() и конструкторы со всеми final-полями, а значит, объединяет в себе
    // возможности сразу пяти аннотаций.
    private static int filmId = 0;
    //дата релиза — не раньше 28 декабря 1895 года;
    private static final LocalDate minDateRealease = LocalDate.of(1895, 12, 28);

    @NonNull
    @EqualsAndHashCode.Exclude
    private int id;
    @NotEmpty(message = "Название не может быть пустым") // название не может быть пустым;
    private String name;
    @Size(min = 0, max = 200, message = "Количество символов в описании не должно превышать 200")
    private String description; //максимальная длина описания — 200 символов;
    @LocalDateMin(value = "1895-12-28")
    //@DateTimeFormat(pattern = "MM.dd.yyyy") // чета не разобрался еще
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
}

/*У model.Film должны быть следующие свойства:
целочисленный идентификатор — id;
название — name;
описание — description;
дата релиза — releaseDate;
продолжительность фильма — duration.*/