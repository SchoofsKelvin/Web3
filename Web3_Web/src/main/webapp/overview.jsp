<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/${css}.css">
</head>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param value="Overview" name="title" />
		</jsp:include>
		<main>
		<table>
			<tr>
				<th>E-mail</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Length</th>
			</tr>
			<c:forEach var="person" items="${persons}">
				<tr>
					<td><c:out value="${person.email}" /></td>
					<td><c:out value="${person.firstName}" /></td>
					<td><c:out value="${person.lastName}" /></td>
					<td><c:out value="${person.length}" /></td>
					<td><a href="Controller?action=updatePerson&userid=${person.userid}">Update</a></td>
					<td><a href="Controller?action=deletePerson&userid=${person.userid}">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
		</main>
		<jsp:include page="footer.jsp">
			<jsp:param value="overview" name="page" />
		</jsp:include>
	</div>
</body>
</html>