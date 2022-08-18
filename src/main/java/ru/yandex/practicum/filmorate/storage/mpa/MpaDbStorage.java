package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Repository
@Qualifier
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Mpa> getMpaToId(int id) {
        final String sql = "SELECT mpa_id, name, description FROM mpa_rates WHERE mpa_id = ?";
        Mpa out = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> makeMpa(rs)).get(0);
        return Optional.ofNullable(out);
    }
    @Override
    public Collection<Mpa> getAllMpa() {
        final String sql = "SELECT mpa_id, name, description FROM mpa_rates";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    public Mpa makeMpa(ResultSet rs) throws SQLException {
        // используем конструктор, методы ResultSet
        Mpa mpa = Mpa.builder().
                id(rs.getInt("mpa_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();
        return mpa;
    }



}
