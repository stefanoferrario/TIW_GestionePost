<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css">
<title>Login</title>
</head>
<body>
	<form action="login" method="post">
		<h1>Login</h1><br>
		<!--  mettere i required -->
		
		<c:choose>
			<c:when test="${loginfailed}">
				Username o password errati
			</c:when>
			<c:when test="${loginempty}">
				Compilare tutti i campi
			</c:when>
			<c:otherwise>
				Inserire username e password
			</c:otherwise>	
		</c:choose>
		<br>
		Username: <input type="text" name="userid" value="<c:out value="${username}"/>"><br>
		Password: <input type="password" name="pw"><br>
		<input type="submit" value="Login">
	</form>

</body>
</html>