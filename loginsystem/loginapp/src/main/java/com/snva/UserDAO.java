package com.snva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private List<User> allUsers;

    public UserDAO() throws SQLException, ClassNotFoundException {
        allUsers = new ArrayList<>();
        loadUserDataFromDatabase();
    }

    private void loadUserDataFromDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DataSourceAccess.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        while (resultSet.next()) {
            allUsers.add(
                    new User(resultSet.getString(2), resultSet.getString(3),
                            resultSet.getString(4), resultSet.getString(5)));
        }
        connection.close();
        statement.close();
        resultSet.close();
    }

    public void displayAllUserInfo() {
        System.out.println("All users info: ");
        allUsers.stream()
                .forEach(user -> System.out.println(user));
    }

    public void displayAllUsernamesSorted() {
        System.out.println("All usernames sorted: ");
        allUsers.stream()
                .map(user -> user.getUsername())
                .sorted()
                .forEach(username -> System.out.print(username + "\n"));
    }

    public void displayAllUsernamesWithSpecialCharacters() {
        System.out.println("All usernames with special characters: ");
        allUsers.stream()
                .filter(user -> !user.getUsername()
                .matches("^[a-zA-Z0-9]*$"))
                .forEach(user -> System.out.println(user.getUsername() + "\n"));
    }
}
