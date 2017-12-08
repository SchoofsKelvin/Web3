package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;

public class LogoutHandler extends Handler {

	public LogoutHandler() {
		super(Role.ADMINISTRATOR);
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		request.getSession().removeAttribute("user");
		return new Result("index.jsp");
	}

}
