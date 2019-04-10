<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Posts list</title>
</head>
<body>
	<div class="top-bar">
		<ul>
			<li><h1>All Posts</h1></li>
			<li style="float:right" > <p>
				<c:url value="/logout" var="logout"></c:url>
				<a href="${logout}">Logout</a>
			</p>
			</li>
		</ul>
	</div>
	<h2> Welcome <c:out value="${sessionUser.userId}" /></h2>
	
	<form method="post" action="createpost">
		URL immagine: <input type="text" name="imgURL"><br>
		Testo*: <textarea rows="4" cols="50" name="text" required></textarea><br>
		<input type="submit" value="Send">
	</form>
	
	<h2>Select a post id to pin it</h2>
	<c:choose>
		<c:when test="${posts.size()>0}">
			<div><table class="table_blur">
				<tr>
					<th>ID</th>
					<th>Message</th>
					<th>Date</th>
					<th>Image</th>
					<th>User</th>
					<th># Likes</th>
				</tr>
				<tbody>
					<c:forEach var="post" items="${posts}" varStatus="row">
						<tr>
							<td>
								<c:url value="/getpost" var="regURL">
									<c:param name="pin" value="${post.id}" />
								</c:url>
								<a href="${regURL}"> <c:out value="${post.id}" /></a>
							</td>
							<td>
								<c:out value="${post.text}" />
							</td>
							<td class="date">
								<c:out value="${post.date}" />
							</td>
							<td class="img">
								<c:if test="${!empty post.image}">
									<!--<c:url value="${post.image}" var="imgURL"></c:url> -->
									<img src="<c:out value="${post.image}"/>" />
								
								</c:if>
							</td>
							<td class="userid">
								<c:out value="${post.userid}" />
							</td>
							<td>
								<c:out value="${post.likes}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table></div>
		</c:when>
		<c:otherwise>No post to display.
		</c:otherwise>
	</c:choose>


</body>
</html>