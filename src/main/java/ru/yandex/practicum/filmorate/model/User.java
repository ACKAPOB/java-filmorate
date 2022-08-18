package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.validator.birthDay;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@birthDay(message = "дата рождения не может быть в будущем") // Тут валидатор немного подругому сделан
public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    @Email
    @NotEmpty
    private String email; //электронная почта не может быть пустой и должна содержать символ @;
    @NotEmpty(message = "Логин не может быть пустым")
    @NotBlank (message = "Логин должен содержать хотя бы один непробельный символ")
    private String login; //логин не может быть пустым и содержать пробелы;
    private String name; //имя для отображения может быть пустым — в таком случае будет использован логин;
    private LocalDate birthday;//дата рождения не может быть в будущем.

    private final Set<Integer> friends = new HashSet(); //https://java-blog.ru/collections/interfeys-java-set

    public User(@NonNull String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
    public String getName() {
        if (name == null || name.equals("")) {
            return getLogin();
        }
        return name;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap <>();
        values.put("EMAIL", email);
        values.put("LOGIN", login);
        values.put("NAME", name);
        values.put("BIRTHDAY", birthday);
        return values;
    }
}
