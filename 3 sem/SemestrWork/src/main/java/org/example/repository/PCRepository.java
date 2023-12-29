package org.example.repository;

import org.example.mapper.RowMapper;
import org.example.model.PC;
import org.example.repository.database.PostgresConnectionProvider;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PCRepository {

    private final static String SQL_FIND_ON_TIME_FROM_PC = "select * from pc left join (select pc_id, user_id, status from booking where ((from_time > ? and from_time > ?) " +
            " or (to_time < ? and to_time > ?)) and status != 'denied') as b on b.pc_id = pc.id where pc_id IS NULL and start_working_time < ? and end_working_time > ?;";

    private final static String SQL_FIND_ALL = "select * from pc;";

    private final static String SQL_FIND_BY_ID = "select * from pc where id =?;";

    private final static String SQL_CREATE_PC = "insert into pc (start_working_time, end_working_time) values (?,?);";

    private final static String SQL_UPDATE_PC = "update pc set start_working_time = ?, end_working_time = ? where id = ?;";

    private final static String SQL_DELETE_PC = "delete from pc where id = ?;";


    private PCRepository(){}

    public void create(PC pc){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PC);
            statement.setTime(1, Time.valueOf(pc.getStartWorkingTime()));
            statement.setTime(2, Time.valueOf(pc.getEndWorkingTime()));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(PC pc){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PC);
            statement.setTime(1, Time.valueOf(pc.getStartWorkingTime()));
            statement.setTime(2, Time.valueOf(pc.getEndWorkingTime()));
            statement.setLong(3, pc.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long pcId){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PC);
            statement.setLong(1,pcId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PC> findById(Long pcId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, pcId);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(Pc_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PC> findAll(){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
            ResultSet set = statement.executeQuery();
            ArrayList<PC> list = new ArrayList<>();
            while (set.next()){
                list.add(Pc_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PC> findOnTime(LocalDateTime from, LocalDateTime to){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ON_TIME_FROM_PC);
            statement.setObject(1, from);
            statement.setObject(2, to);
            statement.setObject(3,to);
            statement.setObject(4,from);
            statement.setTime(5, Time.valueOf(from.toLocalTime()));
            statement.setTime(6, Time.valueOf(to.toLocalTime()));
            ResultSet set = statement.executeQuery();
            ArrayList<PC> list = new ArrayList<>();
            while (set.next()){
                list.add(Pc_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final RowMapper<PC> Pc_ROW_MAPPER = resultSet -> {
        try {
            return PC.builder()
                    .id(resultSet.getLong("id"))
                    .startWorkingTime(resultSet.getTime("start_working_time").toLocalTime())
                    .endWorkingTime(resultSet.getTime("end_working_time").toLocalTime())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    private static class SingletonHolder{
        public static final PCRepository INSTANCE = new PCRepository();
    }

    public static PCRepository getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
