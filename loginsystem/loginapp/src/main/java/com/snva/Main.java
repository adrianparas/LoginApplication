package com.snva;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDAO userDAO = new UserDAO();
        userDAO.displayAllUsernamesSorted();
        System.out.println("----------------------------------------------");
        userDAO.displayAllUserInfo();
        System.out.println("----------------------------------------------");
        userDAO.displayAllUsernamesWithSpecialCharacters();
    }

}
