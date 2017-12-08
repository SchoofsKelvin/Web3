<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Update Person</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/${css}.css">
</head>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param value="Update person" name="title" />
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
			if (request.getAttribute("person") != null) {
		%>
		<form action="Controller" method="post">
			<input type="hidden" name="userid" value="${person.userid}" />
			<p>
				<label for="firstname">First Name</label>
				<input id="firstname" name="firstname" type="text"
					value="${person.firstName}" />
			</p>
			<p>
				<label for="lastname">Last Name</label>
				<input id="lastname" name="lastname" type="text"
					value="${person.lastName}" />
			</p>
			<p>
				<label for="length">Length</label>
				<input id="length" name="length" type="text"
					value="${person.length}" />
			</p>
			<p>
				<label for="email">E-Mail</label>
				<input id="email" name="email" type="email" value="${person.email}" />
			</p>
			<p>
				<label for="email">Role</label>
				<select name="role">
					<%
						for (String role : (String[]) request.getAttribute("roles")) {
								String selected =
									role.equalsIgnoreCase((String) request.getAttribute("role"))
										? "selected" : "";
					%>
					<option value="<%=role.toUpperCase()%>" <%=selected%>><%=role%></option>
					<%
						}
					%>
				</select>
			</p>
			<input type="submit" name="confirm" value="Cancel" />
			<input type="submit" name="confirm" value="Update" />
			<input type="hidden" name="action" value="updatePerson" />
		</form>
		<%
			}
		%> </main>
		<jsp:include page="footer.jsp">
			<jsp:param value="register" name="page" />
		</jsp:include>
	</div>
</body>
</html>