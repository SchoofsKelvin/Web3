<%@page import="java.util.Map.Entry"%>
<footer>
	<%
		if (request.getParameter("disableColour") == null) {
	%>
	<form action="Controller?action=switchColour" method="post">
		<input type="submit" value="Switch Colour">
		<input type="hidden" value="${param.page}" name="page">
	</form>
	<%
		}
	%>
	&copy; Webontwikkeling 3, UC Leuven-Limburg
</footer>