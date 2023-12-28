package ru.itis.dao.impl;

import ru.itis.dao.AccountRepository;
import ru.itis.dao.SimpleDataSourceSingleton;
import ru.itis.models.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

public class AccountRepositorySingleTonImpl implements AccountRepository {
    private final static String SQL_INSERT_ACCOUNT = "insert into account(name) values(?)";
    private final static String SQL_SELECT_ACCOUNT_BY_NAME = "select * from account where account.name = ?";

    private static AccountRepositorySingleTonImpl instance;

    private final DataSource dataSource;


    private AccountRepositorySingleTonImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public static AccountRepositorySingleTonImpl getInstance(){
        if(instance == null){
            instance = new AccountRepositorySingleTonImpl(SimpleDataSourceSingleton.getInstance());
        }
        return instance;
    }
    private Function<ResultSet, Account> map = row -> {
        try {
            Long id = row.getLong("id");
            String name = row.getString("name");

            return Account.builder()
                    .id(id)
                    .name(name)
                    .pets(new ArrayList<>())
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    };


    @Override
    public void save(Account account) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement(SQL_INSERT_ACCOUNT, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getName());

            int affectedRowsAccount = preparedStatement.executeUpdate();
            if(affectedRowsAccount != 1)
                throw new SQLException("Exception in <SaveAccount>");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if(generatedKeys.next()){
                account.setId(generatedKeys.getLong("id"));
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Optional<Account> getAccountByName(String name) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NAME);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return Optional.of(map.apply(resultSet));
            return Optional.empty();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
