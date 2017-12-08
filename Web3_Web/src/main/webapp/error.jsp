<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isErrorPage="true"%>
<c:set var="newline" value="<%=\"\n\"%>"></c:set>
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
		<main>
		<div class="alert-danger">
			<ul>
				<li>Status code: ${pageContext.errorData.statusCode}</li>
				<li>Request URI: ${pageContext.errorData.requestURI}</li>
				<c:if test="${not empty pageContext.errorData.throwable.message}">
					<li>Error: <c:out value="${pageContext.errorData.throwable.message}"></c:out></li>
				</c:if>
				<c:if
					test="${not empty pageContext.errorData.throwable.cause.message}">
					<li>Cause: <c:out value="${pageContext.errorData.throwable.cause.message}"></c:out></li>
				</c:if>
			</ul>
		</div>
		</main>
		<jsp:include page="footer.jsp">
			<jsp:param value="" name="page" />
		</jsp:include>
	</div>
</body>
</html>