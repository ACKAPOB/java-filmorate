package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    @Test
    public void createUser() throws Exception {

        String postEndpoint = "http://localhost:8080/users";

        String inputJson = "{ \"login\":\"dolore\", \"name\":\"Nick Name\", \"email\":\"mail@mail.ru\", " +
                "\"birthday\":\"1946-08-20\" }";

        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 200;
        String body = "{\"id\":7,\"email\":\"mail@mail.ru\",\"login\":\"dolore\"," +
                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}";
        Assertions.assertAll(
                () -> assertEquals(body, response.body(), "Получено тело запроса отличное от ожидаемого"),
                () -> assertEquals(status, code, "Получен статус отличный от ожидаемого")
        );
    }

    //электронная почта не может быть пустой и должна содержать символ @
    @Test
    public void createUserFailEmail() throws Exception {

        String postEndpoint = "http://localhost:8080/users";
        String inputJson = "{ \"login\":\"dolore2\", \"name\":\"Nick Name2\", " +
                "\"email\":\"mail2mail.ru\", \"birthday\":\"1946-08-20\" }";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 400;
        assertEquals(status, code, "Получен статус отличный от ожидаемого");
    }

    //электронная почта не может быть пустой и должна содержать символ @
    @Test
    public void createUserFailEmail2() throws Exception {

        String postEndpoint = "http://localhost:8080/users";
        String inputJson = "{ \"login\":\"dolore2\", \"name\":\"Nick Name2\", " +
                "\"email\":\"\", \"birthday\":\"1946-08-20\" }";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 400;
        assertEquals(status, code, "Получен статус отличный от ожидаемого");
    }

    //логин не может быть пустым и содержать пробелы;
    @Test
    public void createUserFailLogin() throws Exception {

        String postEndpoint = "http://localhost:8080/users";
        String inputJson = "{ \"login\":\"\", \"name\":\"Nick Name\", \"email\":\"mail2@mail.ru\", " +
                "\"birthday\":\"1946-08-20\" }";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 400;
        assertEquals(status, code, "Получен статус отличный от ожидаемого");
    }

    //логин не может быть пустым и содержать пробелы;
    @Test
    public void createUserFailLogin2() throws Exception {

        String postEndpoint = "http://localhost:8080/users";
        String inputJson = "{ \"login\":\" \", \"name\":\"Nick Name\", \"email\":\"mail2@mail.ru\", " +
                "\"birthday\":\"1946-08-20\" }";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 400;
        assertEquals(status, code, "Получен статус отличный от ожидаемого");
    }

    //имя для отображения может быть пустым — в таком случае будет использован логин;
    @Test
    public void createUserNameIsEmpty() throws Exception {

        String postEndpoint = "http://localhost:8080/users";
        String inputJson = "{ \"login\":\"dolore3\", \"email\":\"mail3@mail.ru\", " +
                "\"birthday\":\"1946-08-20\" }";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 200;
        String body = "{\"id\":5,\"email\":\"mail3@mail.ru\",\"login\":\"dolore3\",\"name\":\"dolore3\"," +
                "\"birthday\":\"1946-08-20\"}";
        Assertions.assertAll(
                () ->assertEquals(body, response.body(), "Получено тело запроса отличное от ожидаемого"),
                () ->assertEquals(status, code, "Получен статус отличный от ожидаемого")
        );
    }

    //дата рождения не может быть в будущем.
    @Test
    public void createUserbirthdayFail() throws Exception {
        String postEndpoint = "http://localhost:8080/users";

        String inputJson = "{ \"login\":\"dolore4\", \"name\":\"Nick Name4\", \"email\":\"mail4@mail.ru\", " +
                "\"birthday\":\"2220-08-20\" }";

        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 400;
        assertEquals(status, code, "Получен статус отличный от ожидаемого");
    }

    // Список Users
    @Test
    public void allUsers() throws Exception {

        String postEndpoint = "http://localhost:8080/users";
        var request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Accept", "*/*")
                .GET()
                .build();

        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        int status = 200;
        String body = "[{\"id\":5,\"email\":\"mail3@mail.ru\",\"login\":\"dolore3\",\"name\":\"dolore3\"," +
                "\"birthday\":\"1946-08-20\"},{\"id\":7,\"email\":\"mail@mail.ru\",\"login\":\"dolore\"," +
                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}]";

        Assertions.assertAll(
                () -> assertEquals(body, response.body(), "Получено тело запроса отличное от ожидаемого"),
                () -> assertEquals(status, code, "Получен статус отличный от ожидаемого")
        );
    }
}
