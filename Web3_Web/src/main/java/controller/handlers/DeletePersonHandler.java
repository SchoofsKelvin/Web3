package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;
import service.ShopService;

public class DeletePersonHandler extends Handler {

	private final ShopService service;

	public DeletePersonHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		if ( !post) {
			request.setAttribute("person", service.getPerson(request.getParameter("userid")));
			return new Result("deletePerson.jsp");
		}
		if (request.getParameter("confirm").equals("Delete")) {
			String userid = request.getParameter("userid");
			service.deletePerson(userid == null ? "" : userid);
		}
		return new Result("overview.jsp", true);
	}

}
