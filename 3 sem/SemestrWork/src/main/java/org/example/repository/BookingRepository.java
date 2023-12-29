package org.example.repository;

import org.example.mapper.RowMapper;
import org.example.model.Booking;
import org.example.model.User;
import org.example.model.enums.BookingStatus;
import org.example.repository.database.PostgresConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingRepository {

    private final static String SQL_INSERT_INTO_BOOKING = "insert into booking (user_id, pc_id, from_time, to_time) values (?,?,?,?);";

    private final static String SQL_UPDATE_INTO_BOOKING = "update booking set user_id = ?, pc_id = ?, from_time = ?, to_time = ? where id = ?;";

    private final static String SQL_DELETE_FROM_BOOKING = "delete from booking where id = ?;";

    private final static String SQL_FIND_BY_ID_FROM_BOOKING = "select * from booking where id = ?;";

    private final static String SQL_FIND_BY_USER_ID_FROM_BOOKING = "select * from booking where user_id = ?;";

    private final static String SQL_FIND_BY_PC_ID_FROM_BOOKING = "select * from booking where pc_id = ?;";

    private final static String SQL_FIND_BY_USER_ID_AND_PC_ID_FROM_BOOKING = "select * from booking where user_id = ? and pc_id = ?;";

    private final static String SQL_FIND_ALL_FROM_BOOKING = "select * from booking;";

    private final static String SQL_IS_WAITING = "select * from booking where id = ? and status = 'waiting';";

    private final static String SQL_UPDATE_STATUS = "update booking set status = ? where id = ?";
    private BookingRepository(){}

    public void create(Booking booking) {
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_BOOKING);
            statement.setLong(1, booking.getUserId());
            statement.setLong(2, booking.getPcId());
            statement.setObject(3, booking.getTimeFrom());
            statement.setObject(4, booking.getTimeTo());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Booking> findById(Long bookingId) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_FROM_BOOKING);
            statement.setLong(1, bookingId);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(BOOKING_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> findByUserId(Long userId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID_FROM_BOOKING);
            statement.setLong(1, userId);
            ResultSet set = statement.executeQuery();
            ArrayList<Booking> list = new ArrayList<>();
            while (set.next()){
                list.add(BOOKING_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> findAll(){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_FROM_BOOKING);
            ResultSet set = statement.executeQuery();
            ArrayList<Booking> list = new ArrayList<>();
            while (set.next()){
                list.add(BOOKING_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> findByPCId(Long pcId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_PC_ID_FROM_BOOKING);
            statement.setLong(1, pcId);
            ResultSet set = statement.executeQuery();
            ArrayList<Booking> list = new ArrayList<>();
            while (set.next()){
                list.add(BOOKING_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> findByUserIdAndPCId(Long userId, Long pcId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_USER_ID_AND_PC_ID_FROM_BOOKING);
            statement.setLong(1, userId);
            statement.setLong(2, pcId);
            ResultSet set = statement.executeQuery();
            ArrayList<Booking> list = new ArrayList<>();
            while (set.next()){
                list.add(BOOKING_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long bookingId) {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_FROM_BOOKING);
            statement.setLong(1, bookingId);
            statement.execute();
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public boolean isWaiting(Long id){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_IS_WAITING);
            statement.setLong(1,id);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStatus(Long id, BookingStatus status){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS);
            statement.setString(1, status.getValue());
            statement.setLong(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Booking booking) {
        if (isWaiting(booking.getId())) {
            try (Connection connection = PostgresConnectionProvider.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INTO_BOOKING);
                statement.setLong(1, booking.getUserId());
                statement.setLong(2, booking.getPcId());
                statement.setObject(3, booking.getTimeFrom());
                statement.setObject(4, booking.getTimeTo());
                statement.setLong(5, booking.getId());
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            throw new RuntimeException();
        }
    }

    private static final RowMapper<Booking> BOOKING_ROW_MAPPER = resultSet -> {
        try {
            return Booking.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .pcId(resultSet.getLong("pc_id"))
                    .timeFrom(resultSet.getTimestamp("from_time").toLocalDateTime())
                    .timeTo(resultSet.getTimestamp("to_time").toLocalDateTime())
                    .status(BookingStatus.valueOf(resultSet.getString("status").toUpperCase()))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    public List<Booking> getWaitingBooking() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from booking where status ='waiting';");
            ResultSet set = statement.executeQuery();
            ArrayList<Booking> list = new ArrayList<>();
            while (set.next()){
                list.add(BOOKING_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class SingletonHolder{
        public static final BookingRepository INSTANCE = new BookingRepository();
    }

    public static BookingRepository getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
