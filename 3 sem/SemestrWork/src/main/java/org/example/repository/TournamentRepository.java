package org.example.repository;

import org.example.mapper.RowMapper;
import org.example.model.Tournament;
import org.example.repository.database.PostgresConnectionProvider;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TournamentRepository {

    private static final String SQL_CREATE_TOURNAMENT = "insert into tournament (name, description, start_time) VALUES (?,?,?);";

    private static final String SQL_UPDATE_TOURNAMENT = "update tournament set name = ?, description =?, start_time = ? where id = ?;";

    private static final String SQL_DELETE_TOURNAMENT = "delete from tournament where id = ?;";

    private static final String SQL_FIND_BY_ID_TOURNAMENT = "select * from tournament where id=?;";

    private static final String SQL_FIND_ALL_TOURNAMENT = "select * from tournament;";

    private static final String SQL_FIND_ON_TIME_TOURNAMENT = "select * from tournament where start_time > ? and start_time < ?;";
    private static final String SQL_REGISTER_TOURNAMENT = "insert into user_tournament (user_id, tournament_id) VALUES (?,?);";
    private static final String SQL_DELETE_FROM_TOURNAMENT = "delete from user_tournament where user_id = ? and tournament_id = ?;";


    public void create(Tournament tournament){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_TOURNAMENT);
            statement.setString(1,tournament.getName());
            statement.setString(2, tournament.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(tournament.getStartTime()));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Tournament tournament){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TOURNAMENT);
            statement.setString(1,tournament.getName());
            statement.setString(2, tournament.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(tournament.getStartTime()));
            statement.setLong(4, tournament.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long tournamentId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TOURNAMENT);
            statement.setLong(1, tournamentId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Tournament> findById(Long tournamentId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_TOURNAMENT);
            statement.setLong(1, tournamentId);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(TOURNAMENT_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tournament> findAll(){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_TOURNAMENT);
            ResultSet set = statement.executeQuery();
            List<Tournament> list = new ArrayList<>();
            if (set.next()){
                list.add(TOURNAMENT_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tournament> findOnTime(LocalDateTime from, LocalDateTime to){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ON_TIME_TOURNAMENT);
            statement.setTimestamp(1, Timestamp.valueOf(from));
            statement.setTimestamp(2, Timestamp.valueOf(to));
            ResultSet set = statement.executeQuery();
            List<Tournament> list = new ArrayList<>();
            if (set.next()){
                list.add(TOURNAMENT_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private TournamentRepository(){}

    private static final RowMapper<Tournament> TOURNAMENT_ROW_MAPPER = resultSet -> {
        try {
            return Tournament.builder()
                    .id(resultSet.getLong("id"))
                    .description(resultSet.getString("description"))
                    .name(resultSet.getString("name"))
                    .startTime(resultSet.getTimestamp("start_time").toLocalDateTime())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    public void registerOnTournament(Long tournamentId, Long userId) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_REGISTER_TOURNAMENT);
            statement.setLong(1, userId);
            statement.setLong(2, tournamentId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFromTournament(Long userId, Long tournamentId) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FROM_TOURNAMENT);
            statement.setLong(1, userId);
            statement.setLong(2, tournamentId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class SingletonHolder{
        public static final TournamentRepository INSTANCE = new TournamentRepository();
    }

    public static TournamentRepository getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
