package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerIT {
    //Spring Boot Integration Testing With @SpringBootTest
    //https://www.arhohuttunen.com/spring-boot-integration-testing/
    @Autowired
    MockMvc mockMvc;

    @Test
    public void createFilms () throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\":\"nisi eiusmod\", \"description\":\"adipisicing\", " +
                        "\"releaseDate\":\"1967-03-25\", \"duration\":\"100\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        String body = "{\"id\":1,\"name\":\"nisi eiusmod\",\"description\":\"adipisicing\"," +
                "\"releaseDate\":\"1967-03-25\",\"duration\":100,\"likes\":[]}";
        Assertions.assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого"),
                () -> assertEquals(body, mvcResult.getResponse().getContentAsString(), "Получено тело " +
                        "запроса отличное от ожидаемого")
        );
    }
    @DisplayName("Название не может быть пустым")
    @Test
    public void createFilmsNameFail () throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"\", \"description\":\"adipisicing - 2\", " +
                                "\"releaseDate\":\"1967-03-25\", \"duration\":\"100\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        Assertions.assertAll(
                () -> assertEquals(400, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }
    @DisplayName("Максимальная длина описания — 200 символов")
    @Test
    public void createFilmsDescriptionFail () throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"nisi eiusmod\", \"description\":\"\"Пятеро друзей " +
                                "( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать " +
                                "господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о " +
                                "Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.\"\", " +
                                "\"releaseDate\":\"1967-03-25\", \"duration\":\"100\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        Assertions.assertAll(
                () -> assertEquals(400, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }
    @DisplayName("дата релиза — не раньше 28 декабря 1895 года")
    @Test
    public void releaseDateFail () throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"nisi eiusmod\", \"description\":\"adipisicing\", " +
                                "\"releaseDate\":\"1800-03-25\", \"duration\":\"100\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        Assertions.assertAll(
                () -> assertEquals(400, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }
    @DisplayName("Продолжительность фильма должна быть положительной")
    @Test
    public void releaseDurationFail () throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/films").contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\":\"nisi eiusmod\", \"description\":\"adipisicing\", " +
                                "\"releaseDate\":\"1967-03-25\", \"duration\":\"-100\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andReturn();
        Assertions.assertAll(
                () -> assertEquals(400, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }
    @DisplayName("Список allFilms")
    @Test
    public void allFilms () throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertAll(
                () -> assertEquals(200, mvcResult.getResponse().getStatus(), "Получен статус " +
                        "отличный от ожидаемого")
        );
    }
}