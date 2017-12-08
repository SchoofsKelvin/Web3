package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;
import service.ShopService;

public class OverviewHandler extends Handler {

	private final ShopService service;

	public OverviewHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		request.setAttribute("persons", service.getPersons());
		return new Result("overview.jsp");
	}

}
