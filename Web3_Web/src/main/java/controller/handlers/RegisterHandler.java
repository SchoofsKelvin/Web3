package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;
import domain.Role;
import service.ShopService;

public class RegisterHandler extends Handler {

	private final ShopService service;

	public RegisterHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		if ( !post) return new Result("register.jsp");
		String userid = request.getParameter("userid");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		try {
			int length = Integer.parseUnsignedInt(request.getParameter("length"));
			Person p = new Person(userid, email, password, firstname, lastname, length,
				Role.CUSTOMER);
			service.addPerson(p);
		} catch (NumberFormatException e) {
			return new Result("register.jsp", "Length has to be a positive number");
		} catch (Exception e) {
			return new Result("register.jsp", e.getMessage());
		}
		return new Result("index.jsp", true);
	}

}
