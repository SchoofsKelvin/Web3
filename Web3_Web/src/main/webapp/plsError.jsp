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
			<jsp:param value="Error" name="title" />
		</jsp:include>
		<main> <%
 	String err = request.getParameter("error");
 	err = err == null || err.isEmpty() ? "Empty error" : err;
 	if (err != null) throw new RuntimeException(err);
 %> </main>
		<jsp:include page="footer.jsp">
			<jsp:param value="" name="page" />
		</jsp:include>
	</div>
</body>
</html>