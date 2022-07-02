package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.practicum.filmorate.validator.birthDay;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@birthDay(message = "дата рождения не может быть в будущем") // Тут валидатор немного подругому сделан
public class User {
    // В подобных случаях можно воспользоваться одной аннотацией @Data. Она позволяет генерировать геттеры, сеттеры,
    // методы toString(), equals() и hashCode() и конструкторы со всеми final-полями, а значит, объединяет в себе
    // возможности сразу пяти аннотаций.
    private static int userId = 0;
    @NonNull
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
        if (name == null || name.equals("")) { // имя для отображения может быть пустым — в таком случае будет использован логин;
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }
    private int genId () {
        userId++;
        return userId;
    }
}


/*
* Свойства model.User:
целочисленный идентификатор — id;
электронная почта — email;
логин пользователя — login;
имя для отображения — name;
дата рождения — birthday.
* */

//@NotEmpty (message = "Имя не может быть пустым")
//@Size(min = 2, max = 33, message = "Длина имени не может быть короче 2 и длиннее 33 символов")