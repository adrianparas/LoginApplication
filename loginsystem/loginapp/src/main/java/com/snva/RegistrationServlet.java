package com.snva;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("confPassword");
        String res = addUser(name, username, email, password, passwordConfirm);
        if (res.equals("success")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("name", name);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("message", "Error: " + res);
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
        }

    }

    private String addUser(String name, String username, String email, String password, String passwordConfirm) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DataSourceAccess.getDataSource().getConnection();
            if (userExists(username, connection)) {
                return "Username already exists!";
            } 
            if (!password.equals(passwordConfirm)) {
                return "Passwords do not match!";
            }
            String res = validatePassword(password);
            if (!res.equals("valid")) {
                return res;
            }
             
            PreparedStatement ps = connection
                    .prepareStatement(
                            "insert into users (name, username, email, password) values (?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, BCrypt.hashpw(password, BCrypt.gensalt(12)));
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "success";
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

    private String validatePassword(String password) {
        if (password.length() < 12) {
            return "Password does not follow criteria";
        }
        int upperCount = 0, lowerCount = 0, numCount = 0, specialCount = 0;
        for(char ch: password.toCharArray()) {
            if (Character.isAlphabetic(ch) && Character.isLowerCase(ch)) {
                lowerCount++;
            } else if (Character.isAlphabetic(ch) && Character.isUpperCase(ch)) {
                upperCount++;
            } else if (Character.isDigit(ch)) {
                numCount++;
            } else if (!Character.isDigit(ch) && !Character.isLetter(ch)) {
                specialCount++;
            }
        }
        return (upperCount == 0 || lowerCount == 0 || numCount == 0 || specialCount == 0) ? "Password does not follow criteria" : "valid";
    }

}