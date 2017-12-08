package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;
import service.ShopService;

public class ProductsHandler extends Handler {

	private final ShopService service;

	public ProductsHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		if (request.getParameter("order") == null) {
			request.setAttribute("products", service.getProducts());
		} else {
			request.setAttribute("products", service.getProductsSorted());
		}
		return new Result("products.jsp");
	}
}
