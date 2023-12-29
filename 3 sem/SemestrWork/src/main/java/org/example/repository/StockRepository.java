package org.example.repository;

import org.example.mapper.RowMapper;
import org.example.model.Stock;
import org.example.repository.database.PostgresConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockRepository {

    private static final String SQL_INSERT_INTO_STOCK = "insert into stock (name, count, price) VALUES (?,?,?);";

    private static final String SQL_UPDATE_STOCK = "update stock set name = ?, count = ?, price = ? where id = ?;";

    private static final String SQL_DELETE_STOCK = "delete from stock where id = ?";

    private static final String SQL_FIND_BY_ID_STOCK = "select * from stock where id = ?;";

    private static final String SQL_FIND_ALL_STOCK = "select * from stock;";
    private static final String SQL_FIND_ALL_EXISTENCE_STOCK = "select * from stock where count >0;";


    public void create(Stock stock){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_INTO_STOCK);
            statement.setString(1, stock.getName());
            statement.setLong(2, stock.getCount());
            statement.setLong(3, stock.getPrice());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Stock stock){
        try(Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STOCK);
            statement.setString(1, stock.getName());
            statement.setLong(2, stock.getCount());
            statement.setLong(3, stock.getPrice());
            statement.setLong(4, stock.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long stockId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_STOCK);
            statement.setLong(1, stockId);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Stock> findById(Long stockId){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID_STOCK);
            statement.setLong(1, stockId);
            ResultSet set = statement.executeQuery();
            if (set.next()){
                return Optional.of(STOCK_ROW_MAPPER.mapRow(set));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stock> findAll(){
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_STOCK);
            ResultSet set = statement.executeQuery();
            ArrayList<Stock> list = new ArrayList<>();
            while (set.next()){
                list.add(STOCK_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private static final RowMapper<Stock> STOCK_ROW_MAPPER = resultSet -> {
        try {
            return Stock.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .count(resultSet.getLong("count"))
                    .price(resultSet.getLong("price"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    };

    private StockRepository(){}

    public List<Stock> getAllExistence() {
        try (Connection connection = PostgresConnectionProvider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_EXISTENCE_STOCK);
            ResultSet set = statement.executeQuery();
            ArrayList<Stock> list = new ArrayList<>();
            while (set.next()){
                list.add(STOCK_ROW_MAPPER.mapRow(set));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class SingletonHolder{
        public static final StockRepository INSTANCE = new StockRepository();
    }

    public static StockRepository getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
