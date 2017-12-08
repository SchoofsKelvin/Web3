<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Products</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/${css}.css">
</head>
<body>
	<div id="container">
		<jsp:include page="header.jsp">
			<jsp:param value="Products" name="title"/>
		</jsp:include>
		<main>
		<a href="Controller?action=products&order">Sorteer</a>
		<table>
			<tr>
				<th>ProductId</th>
				<th>Description</th>
				<th>Price</th>
			</tr>
			<c:forEach var="product" items="${products}">
				<tr>
					<td>${product.productid}</td>
					<td>${product.description}</td>
					<td>${product.price}</td>
					<td><a href="Controller?action=deleteProduct&productid=${product.productid}">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
		</main>
		<jsp:include page="footer.jsp">
			<jsp:param value="products" name="page" />
		</jsp:include>
	</div>
</body>
</html>