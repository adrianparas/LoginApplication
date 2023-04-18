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

@WebServlet("/login")
public class DashboardController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (authenticate(name, username, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("name", name);
            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private boolean authenticate(String name, String username, String password) {
        boolean isValid = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DataSourceAccess.getDataSource().getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("select * from users where name=? and username=? and password=?");
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return isValid;
    }
}
