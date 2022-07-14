package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {

    //https://sysout.ru/testirovanie-spring-boot-prilozheniya-s-testresttemplate/
    // Разобраться с проверкой невалидных запросов

    @Autowired
    //MockMvc mockMvc;
    private TestRestTemplate restTemplate;
    @DisplayName("Создание user")
    @Test
    public void createUser() throws Exception {
        User user = new User("mail@mail.ru", "dolore", "Nick Name",
                LocalDate.of(1946,8,20));
        var response = restTemplate.postForEntity("/users", user, User.class);
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode(), "Получен статус " +
                        "отличный от ожидаемого"),
                () -> assertEquals(user, response.getBody(), "Получено тело " +
                        "запроса отличное от ожидаемого")
        );
    }

    @DisplayName("Электронная почта не может быть пустой и должна содержать символ @")
    @Test
    public void createUserFailEmail() throws Exception {
        var response = restTemplate.postForEntity("/users", new User("mail2@mail.ru",
                "dolore2", "Nick Name2", LocalDate.of(1946,8,20)), User.class);
        Assertions.assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }

    @DisplayName("Список Users")
    @Test
    public void allUsers() throws Exception {
        User user1 = new User("mail11@mail.ru", "dolore11", "Nick Name11",
                LocalDate.of(1946,8,20));
        User user2 = new User("mail22@mail.ru", "dolore22", "Nick Name22",
                LocalDate.of(1946,8,20));
        var response1 = restTemplate.postForEntity("/users", user1, User.class);
        var response2 = restTemplate.postForEntity("/users", user2, User.class);
        // Тут костыль какой-то, сначала делаю два Post, может можно это в один response как то уложить?
        ResponseEntity<List<User>> response = restTemplate.exchange("/users", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<User>>() {
                    });
            List<User> persons = response.getBody();
        System.out.println(persons);

        Assertions.assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode(), "Получен статус " +
                        "отличный от ожидаемого"),
                () -> assertEquals(persons.size(), 4, "Количество записей " +
                        "запроса отличное от ожидаемого"),
                () -> assertEquals(persons.get(3).getEmail(), user2.getEmail(), "Получено тело " +
                        "запроса отличное от ожидаемого")
        );
    }

}
