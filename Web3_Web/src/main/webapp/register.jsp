<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Register</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/${css}.css">
</head>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param value="Register" name="title" />
		</jsp:include>
		<main> <%
 	if (request.getAttribute("error") != null) {
 %>
		<div class="alert-danger">
			<ul>
				<li>${error}</li>
			</ul>
		</div>
		<%
			}
		%>
		<form action="Controller" method="post">
			<p>
				<label for="userid">UserId</label>
				<input id="userid" name="userid" type="text" value="${param.userid}" />
			</p>
			<p>
				<label for="firstname">First Name</label>
				<input id="firstname" name="firstname" type="text"
					value="${param.firstname}" />
			</p>
			<p>
				<label for="lastname">Last Name</label>
				<input id="lastname" name="lastname" type="text"
					value="${param.lastname}" />
			</p>
			<p>
				<label for="length">Length in cm</label>
				<input id="length" name="length" type="text"
					value="${param.length}" />
			</p>
			<p>
				<label for="email">E-Mail</label>
				<input id="email" name="email" type="email" value="${param.email}" />
			</p>
			<p>
				<label for="password">Password</label>
				<input id="password" name="password" type="password"/>
			</p>
			<input type="submit" value="Register" id="register"/>
			<input type="hidden" name="action" value="register" />
		</form>
		</main>
		<jsp:include page="footer.jsp">
			<jsp:param value="register" name="page" />
		</jsp:include>
	</div>
</body>
</html>