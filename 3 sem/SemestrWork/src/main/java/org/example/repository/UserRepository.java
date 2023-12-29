package org.example.repository;

import org.example.mapper.RowMapper;
import org.example.model.Tournament;
import org.example.model.User;
import org.example.model.enums.UserRole;
import org.example.repository.database.PostgresConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {


    private static final String SQL_INSERT_USER = "insert into users (username, email, hash_password) VALUES (?,?,?);";
    private static final String SQL_UPDATE_USER = "update users set username = ?, email = ?, hash_password = ?, user_role = ? where id = ?;";
    private static final String SQL_DELETE_USER = "delete from users where id = ?;";
    private static final String SQL_FIND_BY_ID_USER = "select * from users where id = ?;";
    private static final String SQL_FIND_BY_USERNAME_USER = "select * from users where username = ?;";
    private static final String SQL_FIND_BY_EMAIL_USER = "select * from users where email = ?;";
    private static final String SQL_GET_ALL_ADMINS = "select * from users where user_role = 'admin'";
    private static final String SQL_FIND_ALL = "select * from users;";
    private static final String SQL_FIND_USER_BY_TOURNAMENT_ID = "select users.id, username, email, hash_password, user_role from users left join user_tournament ut on users.id = ut.user_id where ut.id = ?;";
    private static final String SQL_FIND_USER_BY_BOOKING_ID = "select users.id, username, email, hash_password, user_role from users left join booking b on users.id = b.user_id where b.id = ?;";

    public List<User> findUsersByTournamentId(Long tournamentId) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_TOURNAMENT_ID);
            statement.setLong(1,tournamentId);
            ResultSet set = statement.executeQuery();
            List<User> list = new ArrayList<>();
            while (set.next()){
                list.add(USER_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findUsersByBookingId(Long bookingId) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_BOOKING_ID);
            statement.setLong(1,bookingId);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(USER_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<User> getAdminList(){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_ADMINS);
            ResultSet set = statement.executeQuery();
            List<User> list = new ArrayList<>();
            while (set.next()){
                list.add(USER_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(User user){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getHashPassword());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getHashPassword());
            statement.setString(4, user.getRole().getValue());
            statement.setLong(5, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long userId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER);
            statement.setLong(1, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> findAll(){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet set = statement.executeQuery();
            List<User> list = new ArrayList<>();
            while (set.next()){
                list.add(USER_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findById(Long userId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_USER);
            statement.setLong(1, userId);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(USER_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByUsername(String username){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USERNAME_USER);
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(USER_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByEmail(String email){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_EMAIL_USER);
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(USER_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private static final RowMapper<User> USER_ROW_MAPPER = resultSet -> {
        try {
            return User.builder()
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .username(resultSet.getString("username"))
                    .hashPassword(resultSet.getString("hash_password"))
                    .role(UserRole.valueOf(resultSet.getString("user_role").toUpperCase()))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    private UserRepository(){}

    private static class SingletonHolder{
        public static final UserRepository INSTANCE = new UserRepository();
    }

    public static UserRepository getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
