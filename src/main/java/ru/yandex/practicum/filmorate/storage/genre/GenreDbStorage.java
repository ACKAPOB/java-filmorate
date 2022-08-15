package ru.yandex.practicum.filmorate.storage.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Qualifier
public class GenreDbStorage implements GenreStorage{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> getGenre(int id) {
        final String sql = "SELECT * FROM genres WHERE genre_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), id).stream().findAny();
    }

    @Override
    public Collection <Genre> getAllGenres() {
        String sql = "select * from genres";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public void deleteGenresToFilm(int id) {
        String sqlQuery = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
    @Override
    public void recordGenresToFilm(int film_id, int genre_id) {
        String sql = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, film_id, genre_id);
    }

    @Override
    public List<Genre> getGenresToFilm (int id) {
        String sql =
                "SELECT genres.genre_id, genres.name " +
                        "FROM film_genre " +
                        "JOIN genres " +
                        "ON genres.genre_id = film_genre.genre_id " +
                        "WHERE film_genre.film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), id);
    }

    public Genre makeGenre(ResultSet rs) throws SQLException {
        Genre out = Genre.builder().
                id(rs.getInt("genre_id"))
                .name(rs.getString("name"))
                .build();
        return out;
    }
}
