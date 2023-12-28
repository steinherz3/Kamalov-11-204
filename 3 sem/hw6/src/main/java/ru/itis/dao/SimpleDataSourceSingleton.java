package ru.itis.dao;

import jdk.jshell.spi.ExecutionControl;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class SimpleDataSourceSingleton implements DataSource {

    private static SimpleDataSourceSingleton simpleDataSourceSingletonInstance;
    private Connection connection;

    private final Properties properties;


    private SimpleDataSourceSingleton(Properties properties){
        this.properties = properties;
        loadDriver();
    }

    public static SimpleDataSourceSingleton getInstance(){
        if(simpleDataSourceSingletonInstance == null){
            throw new IllegalArgumentException("DataSource is not exist, use getInstance(Properties properties)");
        }
        return simpleDataSourceSingletonInstance;
    }
    public static SimpleDataSourceSingleton getInstance(Properties properties){
        if(simpleDataSourceSingletonInstance == null){
            simpleDataSourceSingletonInstance = new SimpleDataSourceSingleton(properties);
        }
        return simpleDataSourceSingletonInstance;
    }
    private void loadDriver() {
        try {
            Class.forName(this.properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void openConnection() {
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            openConnection();
        }
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        try {
            throw new ExecutionControl.NotImplementedException("This method is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}
