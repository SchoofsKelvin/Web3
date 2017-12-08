<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Delete Person</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/${css}.css">
</head>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param value="Delete person" name="title" />
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
			} else {
		%>
		<p>Are you sure you want to remove ${person.firstName}
			${person.lastName}?</p>
		<form action="Controller" method="post">
			<input type="submit" name="confirm" value="Cancel" />
			<input type="submit" name="confirm" value="Delete" />
			<input type="hidden" name="action" value="deletePerson" />
			<input type="hidden" name="userid" value="${person.userid}" />
		</form>
		<%
			}
		%> </main>
		<jsp:include page="footer.jsp">
			<jsp:param value="deletePerson" name="page" />
			<jsp:param value="true" name="disableColour" />
		</jsp:include>
	</div>
</body>
</html>