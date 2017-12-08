package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Person;
import domain.Role;
import service.ShopService;

public class UpdatePersonHandler extends Handler {

	private final ShopService service;

	public UpdatePersonHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		if ( !post) {
			Person person = service.getPerson(request.getParameter("userid"));
			request.setAttribute("person", person);
			if (person == null) { return new Result("updatePerson.jsp", "Person not found"); }
			request.setAttribute("role", person.getRole().name());
			Role[] roles = Role.values();
			String[] options = new String[roles.length];
			for (int i = 0; i < roles.length; i++) {
				String name = roles[i].name();
				options[i] =
					name.substring(0, 1).toUpperCase() + name.substring(1).toUpperCase();
			}
			request.setAttribute("roles", options);
			return new Result("updatePerson.jsp");
		}
		if (request.getParameter("confirm").equals("Update")) {
			String userid = request.getParameter("userid");
			try {
				Person user = getUser(request);
				Person person = service.getPerson(userid);
				if (person == null) throw new IllegalArgumentException("Person not found");
				Role role = Role.valueOf(request.getParameter("role"));
				if (user.equals(person) && role != user.getRole())
					throw new IllegalArgumentException("Can't change your own role");
				person.setFirstName(request.getParameter("firstname"));
				person.setLastName(request.getParameter("lastname"));
				person.setLength(Integer.parseUnsignedInt(request.getParameter("length")));
				person.setEmail(request.getParameter("email"));
				person.setRole(role);
				service.updatePersons(person);
			} catch (NumberFormatException e) {
				request.setAttribute("error", "Length has to be a positive number");
				return handle(request, response, false);
			} catch (Exception e) {
				request.setAttribute("error", e.getMessage());
				return handle(request, response, false);
			}
		}
		request.setAttribute("persons", service.getPersons());
		return new Result("overview.jsp", true);
	}

}
