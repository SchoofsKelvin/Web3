<header>
	<h1>
		<span>Web shop</span>
	</h1>
	<nav>
		<ul>
			<%
	String[] actions = (String[]) request.getAttribute("actions");
	for (String action : actions) {
		boolean gg = action.equalsIgnoreCase(request.getParameter("title"));
		String link = action.toLowerCase().replaceAll("\\s+", "");
%>
			<li <%=gg ? " id=\"actual\"" : ""%>>
				<a href="Controller?action=<%=link%>"><%=action%></a>
			</li>
			<%}%>
		</ul>
	</nav>
	<h2>${param.title}</h2>
</header>