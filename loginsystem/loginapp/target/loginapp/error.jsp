<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Error Message</title>
	<style>
		h2 {
            color: red;
        }
	</style>
</head>
<body>
	<h2>The username already exists, please choose a different one. </h2>
	<p><a href="register.jsp">Go back to registration page</a></p>
	<c:if test="${not empty sessionScope.message}">
       <p>${sessionScope.message}</p>
    </c:if>
</body>
</html>