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

@WebServlet("/login")
public class AccountController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String usernameOrEmail = request.getParameter("usernameEmail");
        String password = request.getParameter("password");
        String res = authenticate(name, usernameOrEmail, password);
        if (res.equals("success")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("name", name);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("message", "Error: " + res);
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }

    private String authenticate(String name, String usernameOrEmail, String password) {
        boolean isValid = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DataSourceAccess.getDataSource().getConnection();
            PreparedStatement ps = connection
                    .prepareStatement("select * from users where name=? and (username=? or email =?)");
            
            ps.setString(1, name);
            ps.setString(2, usernameOrEmail);
            ps.setString(3, usernameOrEmail);  
            
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    isValid = true;
                }
            }
            resultSet.close();
            ps.close();
            connection.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return isValid ? "success" : "Invalid login credentials!";
    }
}
