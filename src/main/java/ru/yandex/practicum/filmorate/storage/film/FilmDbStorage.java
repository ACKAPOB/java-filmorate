package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
@Qualifier
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Override
    public Film create(Film film) {
        String sql = "INSERT INTO FILMS (name, description, releaseDate, duration, mpa_id) " +
                "VALUES (?,?,?,?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"FILM_ID"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, (java.sql.Date.valueOf(film.getReleaseDate())));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, kh);
        film.setId(Objects.requireNonNull(kh.getKey()).intValue());
        return film;
    }

    @Override
    public void deleteFilm(int filmId) {
        String delete = "DELETE FROM films where film_id = ? ";
        if (jdbcTemplate.update(delete, filmId) != 1) {
            throw new NotFoundException(String.format("User with id = %d not found.", filmId));
        }
    }

    @Override
    public Film update(Film film) {
        final String sql = "UPDATE films " +
                "SET name = ?, " +
                "description = ?, " +
                "releaseDate = ?, " +
                "duration = ?,  " +
                "mpa_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return getFilm(film.getId());
    }

    @Override
    public Collection<Film> getAll() {
        String sql = "SELECT " +
                "f.FILM_ID, " +
                "f.NAME, " +
                "f.DURATION, " +
                "f.DESCRIPTION, " +
                "f.RELEASEDATE, " +
                "f.MPA_ID, " +
                "r.MPA_ID, " +
                "r.NAME AS rateName, " +
                "r.DESCRIPTION AS rateDesc " +
                "from FILMS AS f " +
                "join MPA_RATES AS r on f.MPA_ID = r.MPA_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilms(rs));
    }

    public Film makeFilms(ResultSet rs) throws SQLException {
        // используем конструктор, методы ResultSet
        int id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
        int duration = rs.getInt("duration");
        Mpa mpa = Mpa.builder().
                id(rs.getInt("mpa_id"))
                .name(rs.getString("rateName"))
                .description(rs.getString("rateDesc"))
                .build();
        Film film = new Film(name, description, releaseDate, duration, mpa); // создаем
        film.setId(id); // Устанавливаем ID
        film.setGenres(new LinkedHashSet<>(genreStorage.getGenresToFilm(id)));
        return film;
    }

    @Override
    public Map<Integer, Film> getFilmList() {
        return null;
    }

    @Override
    public boolean isExists(Film film) {
        return getFilm(film.getId()) != null;
    }

    @Override
    public Film getFilm(int id) {
        String sql = "SELECT " +
                "f.FILM_ID, " +
                "f.NAME, " +
                "f.DURATION, " +
                "f.DESCRIPTION, " +
                "f.RELEASEDATE, " +
                "f.MPA_ID, " +
                "r.MPA_ID, " +
                "r.NAME AS rateName, " +
                "r.DESCRIPTION AS rateDesc " +
                "from FILMS AS f " +
                "join MPA_RATES AS r " +
                "on f.MPA_ID = r.MPA_ID " +
                "where f.FILM_ID = ?";
        return jdbcTemplate.queryForObject(sql,(rs, rowNum) -> makeFilms(rs), id);
    }

    @Override
    public void userAddLikes(int filmId, int userId) {
        final String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void userDelLikes(int filmId, int userId) {
        final String sql = "DELETE FROM likes WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public Collection<Film> findTopFilms(int count) {
        final String sql = "SELECT " +
                "f.film_id, " +
                "f.name, " +
                "f.description, " +
                "f.releaseDate, " +
                "f.duration, " +
                "f.mpa_id, " +
                "r.MPA_ID, " +
                "r.NAME AS rateName, " +
                "r.DESCRIPTION AS rateDesc  " +
                "FROM films AS f " +
                "JOIN mpa_rates AS r on f.mpa_id = r.mpa_id " +
                "LEFT JOIN likes AS l ON f.film_id = l.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(l.user_id) " +
                "DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilms(rs), count);
    }
}