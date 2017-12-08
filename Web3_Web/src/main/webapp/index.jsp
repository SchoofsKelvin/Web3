<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/${css}.css">
</head>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param value="Home" name="title" />
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
		%> Sed ut perspiciatis unde omnis iste natus error sit voluptatem
		accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae
		ab illo inventore veritatis et quasi architecto beatae vitae dicta
		sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit
		aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos
		qui ratione voluptatem sequi nesciunt.</main>
		<%
			if (request.getSession().getAttribute("user") == null) {
		%>
		<form method="post" action="Controller">
			<input type="hidden" name="action" value="login">
			<label for="userid">Your userid</label>
			<input type="text" name="userid" id="userid"><br/>
			<label for="password">Your password</label>
			<input type="password" name="password" id="password"><br/>
			<input type="submit" value="Log in" id="login">
		</form>
		<%
			} else {
		%>
		<p>Welcome, ${user.firstName}.</p>
		<form method="post" action="Controller">
		<input type="hidden" name="action" value="logout">
			<input type="submit" value="Log out" id="logout">
		</form>
		<%
			}
		%>
		<jsp:include page="footer.jsp">
			<jsp:param value="" name="page" />
		</jsp:include>
	</div>
</body>
</html>