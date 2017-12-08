<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Add Product</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/${css}.css">
</head>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param value="Add Product" name="title" />
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
				<label for="productid">ProductId</label>
				<input id="productid" name="productid" type="text" />
			</p>
			<p>
				<label for="description">Description</label>
				<input id="description" name="description" type="text" />
			</p>
			<p>
				<label for="price">Price</label>
				<input id="price" name="price" type="number" step="0.01"/>
			</p>
			<input type="submit" value="Register" />
			<input type="hidden" name="action" value="addproduct" />
		</form>
		</main>
		<jsp:include page="footer.jsp">
			<jsp:param value="addproduct" name="page" />
		</jsp:include>
	</div>
</body>
</html>