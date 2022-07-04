package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.yandex.practicum.filmorate.controller.UserController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\":\"dolore\", \"name\":\"Nick Name\", \"email\":\"mail@mail.ru\", " +
                                "\"birthday\":\"1946-08-20\" }"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        String body = "{\"id\":7,\"email\":\"mail@mail.ru\",\"login\":\"dolore\"," +
                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}";

        Assertions.assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого"),
                () -> assertEquals(body, mvcResult.getResponse().getContentAsString(), "Получено тело " +
                        "запроса отличное от ожидаемого")
        );
    }

    @Test //электронная почта не может быть пустой и должна содержать символ @
    public void createUserFailEmail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\":\"dolore2\", \"name\":\"Nick Name2\", " +
                                "\"email\":\"mail2mail.ru\", \"birthday\":\"1946-08-20\" }"))
                .andDo(MockMvcResultHandlers.print())
                //.andExpect(status().isOk())
                .andExpect(status().is4xxClientError())
                .andReturn();
        int status = 400;
        Assertions.assertAll(
                () -> assertEquals(status, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }

    @Test //электронная почта не может быть пустой и должна содержать символ @
    public void createUserFailEmail2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\":\"dolore2\", \"name\":\"Nick Name2\", " +
                                "\"email\":\"\", \"birthday\":\"1946-08-20\" }"))
                .andDo(MockMvcResultHandlers.print())
                //.andExpect(status().isOk())
                .andExpect(status().is4xxClientError())
                .andReturn();
        int status = 400;
        Assertions.assertAll(
                () -> assertEquals(status, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }

    @Test //логин не может быть пустым и содержать пробелы;
    public void createUserFailLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"login\":\"\", \"name\":\"Nick Name\", \"email\":\"mail2@mail.ru\", " +
                                "\"birthday\":\"1946-08-20\" }"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        int status = 400;
        Assertions.assertAll(
                () -> assertEquals(status, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }

    @Test //логин не может быть пустым и содержать пробелы;
    public void createUserFailLogin2() throws Exception {
        String str = "{ \"login\":\" \", \"name\":\"Nick Name\", \"email\":\"mail2@mail.ru\", " +
                "\"birthday\":\"1946-08-20\" }";
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(str))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        int status = 400;
        Assertions.assertAll(
                () -> assertEquals(status, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }

    @Test //имя для отображения может быть пустым — в таком случае будет использован логин;
    public void createUserNameIsEmpty() throws Exception {
        String str = "{ \"login\":\"dolore3\", \"email\":\"mail3@mail.ru\", " +
                "\"birthday\":\"1946-08-20\" }";
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(str))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        String body = "{\"id\":5,\"email\":\"mail3@mail.ru\",\"login\":\"dolore3\",\"name\":\"dolore3\"," +
                "\"birthday\":\"1946-08-20\"}";

        Assertions.assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого"),
                () -> assertEquals(body, mvcResult.getResponse().getContentAsString(), "Получено тело " +
                        "запроса отличное от ожидаемого")
        );
    }

    @Test //дата рождения не может быть в будущем.
    public void createUserbirthdayFail() throws Exception {
        String str = "{ \"login\":\"dolore4\", \"name\":\"Nick Name4\", \"email\":\"mail4@mail.ru\", " +
                "\"birthday\":\"2220-08-20\" }";
        MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(str))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        int status = 400;
        Assertions.assertAll(
                () -> assertEquals(status, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }

    @Test // Список Users
    public void allUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        String body = "[{\"id\":5,\"email\":\"mail3@mail.ru\",\"login\":\"dolore3\",\"name\":\"dolore3\"," +
                "\"birthday\":\"1946-08-20\"},{\"id\":7,\"email\":\"mail@mail.ru\",\"login\":\"dolore\"," +
                "\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}]";

        Assertions.assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого"),
                () -> assertEquals(body, mvcResult.getResponse().getContentAsString(), "Получено тело " +
                        "запроса отличное от ожидаемого")
        );
    }
}
