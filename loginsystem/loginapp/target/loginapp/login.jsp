<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form method="post" action="login">
            <label>Name:</label>
            <input type="text" name="name" placeholder="Enter your name"><br><br>
            <label>Username:</label>
            <input type="text" name="username" placeholder="Enter username" required><br><br>
            <label>Password:</label>
            <input type="password" name="password" placeholder="Enter password" required><br><br>
            <input type="submit" value="Login">
        </form>
        <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>
    </body>
</html>
