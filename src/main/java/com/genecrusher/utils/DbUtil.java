package com.genecrusher.utils;

import com.genecrusher.dao.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DbUtil {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(PropertyUtils.getDbPropertiesObject().getProperty("url"),
                    PropertyUtils.getDbPropertiesObject().getProperty("username"), PropertyUtils.getDbPropertiesObject().getProperty("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void executeStatement(Connection connection, String statementString) {
        Statement statement = null;
        try {
            statement = Objects.requireNonNull(connection).createStatement();
            statement.executeQuery(statementString);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(statement).close();
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static List<String> executeStatementAndGetTableNames(Connection connection) {
        Statement statement;
        List<String> existingTables = new ArrayList<>();
        try {
            statement = Objects.requireNonNull(connection).createStatement();
            ResultSet resultSet = statement.executeQuery("select * from USER_TABLES");
            while (Objects.requireNonNull(resultSet).next()) {
                existingTables.add(resultSet.getString(1));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return existingTables;
    }

    public static void checkAndCreateTable() {
        Connection connection = getConnection();
        if (!checkIfTableExists(connection)) {
            executeStatement(getConnection(), SqlUtil.CREATE_USERS_TABLE_STATEMENT);
        }
    }

    private static boolean checkIfTableExists(Connection connection) {
        List<String> existingTables = executeStatementAndGetTableNames(connection);
        return existingTables.contains(SqlUtil.TABLE_NAME);
    }


    public static User executeStatementAndFetchUser(Connection connection, String fetchUserStatementString) {
        Statement statement = null;
        User user = new User();
        try {
            statement = Objects.requireNonNull(connection).createStatement();
            ResultSet resultSet = statement.executeQuery(fetchUserStatementString);
            if (resultSet.next()) {
                user.setLoginName(resultSet.getString(1));
                user.setEmail(resultSet.getString(3));
                user.setRoles(resultSet.getString(4));
                user.setCreatedBy(resultSet.getString(5));
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(statement).close();
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return user;
    }
}
