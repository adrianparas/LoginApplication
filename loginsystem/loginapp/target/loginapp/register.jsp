<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Registration Page</title>
    </head>
    <body>
        <h1>Registration Page</h1>
        <form method="post" action="register">
            <label>Name:</label>
            <input type="text" name="name" placeholder="Enter your name"><br><br>
            <label>Username:</label>
            <input type="text" name="username" placeholder="Enter username" required><br><br>
            <label>Email:</label>
            <input type="email" name="email" placeholder="Enter email"><br><br>
            <label>Password:</label>
            <input type="password" name="password" placeholder="Enter password" required><br><br>
            <input type="submit" value="Register">
        </form>
        <p>Already have an account? <a href="login.jsp">Log in</a></p>
        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>
    </body>
</html>