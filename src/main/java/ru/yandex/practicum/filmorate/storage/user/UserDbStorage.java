package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Repository
@Qualifier
public class UserDbStorage implements UserStorage{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("USER_ID");
        int i =  simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
        user.setId(i);
        return user;
        //https://springframework.guru/spring-jdbctemplate-crud-operations/ ищи по SimpleJdbcInsert
    }

    @Override
    public void delete(int userId) {
        String deleteUser = "DELETE FROM users where id = ? ";
        if (jdbcTemplate.update(deleteUser, userId) != 1) {
            throw new NotFoundException(String.format("User with id = %d not found.", userId));
        }
    }

    @Override
    public User updateUser(User user) {
        final String sql = "update users set login = ?, name = ?, email = ?, birthday = ?   where user_id = ?";
        jdbcTemplate.update(sql,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public Collection<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    private User makeUser(ResultSet rs) throws SQLException {
        // используем конструктор, методы ResultSet
        int id = rs.getInt("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        // Получаем дату и конвертируем её из sql.Date в time.LocalDate
        LocalDate creationDate = rs.getDate("birthday").toLocalDate();
        User user = new User(email, login, name, creationDate); // создаем User
        user.setId(id); // Устанавливаем ID
        return user;
    }

    @Override
    public Map<Integer, User> getUserList() {
        return null;
    }

    @Override
    public boolean isExists(User user) {
        return false;
    }

    @Override
    public void userAddFriend(int userId, int friendId) {
        String startFriendshipQuery = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(startFriendshipQuery, userId, friendId);
    }

    @Override
    public Optional<User> getUser(int userId) {
        final String sql = "SELECT * from USERS where user_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        if (rs.next()) {
            User user = new User(
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    rs.getDate("BIRTHDAY").toLocalDate());
            user.setId(rs.getInt("user_id"));
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findFriendsUser(int userId) {
        final String sql = "SELECT * From USERS where USER_ID IN (SELECT FRIEND_ID FROM FRIENDS where USER_ID = ?)";
        Collection<User> friends = new HashSet<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        while (rs.next()) {
            User user = new User(
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    rs.getDate("BIRTHDAY").toLocalDate());
            user.setId(rs.getInt("user_id"));
            friends.add(user);
        }
        return friends;
    }
    @Override
    public void userDelFriend(int userId, int friendId) {
        String userDelFriend = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(userDelFriend, userId, friendId);
    }

    @Override
    public Collection<User> findCommonFriends(int userId, int otherId) {
        final String sql = "SELECT * From USERS where USER_ID IN (SELECT FRIEND_ID " +
                "FROM FRIENDS where USER_ID = " + userId + ") " +
                "AND USER_ID IN (SELECT FRIEND_ID FROM FRIENDS where USER_ID = " + otherId + ")";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }
}

