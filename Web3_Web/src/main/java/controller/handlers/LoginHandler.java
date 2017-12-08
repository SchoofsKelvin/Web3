package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;
import domain.Role;
import service.ShopService;

public class LoginHandler extends Handler {

	private final ShopService service;

	public LoginHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		if (getUser(request) != null) return new Result("index.jsp", "Already logged in");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		Person user = service.getUserIfAuthenticated(userid, password);
		if (user == null) return new Result("index.jsp", "User not found or invalid password");
		request.getSession().setAttribute("user", user);
		return new Result("index.jsp", true);
	}

}
