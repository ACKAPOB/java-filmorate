package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.validator.birthDay;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
@Data
@birthDay(message = "дата рождения не может быть в будущем") // Тут валидатор немного подругому сделан
public class User {
    private static int userId = 0;
    @EqualsAndHashCode.Exclude
    private int id;
    @Email
    @NotEmpty
    private String email; //электронная почта не может быть пустой и должна содержать символ @;
    @NotEmpty(message = "Логин не может быть пустым")
    @NotBlank (message = "Логин должен содержать хотя бы один непробельный символ")
    private String login; //логин не может быть пустым и содержать пробелы;
    @NotEmpty
    private String name; //имя для отображения может быть пустым — в таком случае будет использован логин;
    private LocalDate birthday;//дата рождения не может быть в будущем.
    public User(@NonNull String email, String login, String name, LocalDate birthday) {
        this.id = genId();
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
    private int genId () {
        userId++;
        return userId;
    }
    public String getName() {
        if (name == null || name.equals("")) {
            return getLogin();
        }
        return name;
    }
}