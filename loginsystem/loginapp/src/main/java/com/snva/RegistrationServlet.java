package com.snva;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (addUser(name, email, username, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("name", name);
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private boolean addUser(String name, String email, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DataSourceAccess.getDataSource().getConnection();
            if (userExists(username, connection)) {
                return false;
            }
            PreparedStatement statement = connection
                    .prepareStatement("insert into users (name, username, email, password) values (?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }

    private boolean userExists(String username, Connection connection) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("select username from users where username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}