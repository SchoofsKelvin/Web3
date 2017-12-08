package controller.handlers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;

public class SwitchColourHandler extends Handler {

	public SwitchColourHandler() {
		super(Role.ADMINISTRATOR);
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		String page = "Controller?action=" + request.getParameter("page");
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equalsIgnoreCase("colour")) {
				cookie.setValue(cookie.getValue().equals("red") ? "yellow" : "red");
				response.addCookie(cookie);
				return new Result(page, true);
			}
		}
		Cookie cookie = new Cookie("colour", "yellow");
		response.addCookie(cookie);
		return new Result(page, true);
	}

}
