package controller.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;
import domain.Role;

public abstract class Handler {

	public final Role				role;

	private final static String[]	defaultActions	= { "Home", "Register" };
	private final static String[]	customerActions	= { "Home", "Products", "Register" };
	private final static String[]	adminActions	=
		{ "Home", "Overview", "Products", "Add Product", "Register" };

	public Handler(Role role) {
		this.role = role;
	}

	public Person getUser(HttpServletRequest request) {
		return (Person) request.getSession().getAttribute("user");
	}

	public final void handleRequest(HttpServletRequest request, HttpServletResponse response,
		boolean post) throws ServletException, IOException {

		String css = "red";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("colour")) {
					css = cookie.getValue();
				}
			}
		}
		request.setAttribute("css", css);
		Person user = getUser(request);
		if (user == null) {
			request.setAttribute("actions", defaultActions);
		} else if (user.getRole() == Role.ADMINISTRATOR) {
			request.setAttribute("actions", adminActions);
		} else {
			request.setAttribute("actions", customerActions);
		}
		Result result = handle(request, response, post);
		if (result.isRedirect) {
			response.sendRedirect(result.url);
		} else {
			request.getRequestDispatcher(result.url).forward(request, response);
		}
	}

	public boolean canAccess(Person person) {
		return true;
	}

	protected abstract Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post);

	public static class Result {

		public final String		url, error;
		public final boolean	isRedirect;

		public Result(String url, boolean isRedirect) {
			this.isRedirect = isRedirect;
			this.error = null;
			this.url = url;
		}

		public Result(String url) {
			this(url, false);
		}

		public Result(String url, String error) {
			this.isRedirect = true;
			this.error = error;
			this.url = url;
		}

	}
}
