package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Role;
import service.ShopService;

public class DeleteProductHandler extends Handler {

	private final ShopService service;

	public DeleteProductHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		if ( !post) {
			request.setAttribute("product",
				service.getProduct(request.getParameter("productid")));
			return new Result("deleteProduct.jsp");
		}
		if (request.getParameter("confirm").equals("Delete")) {
			String productid = request.getParameter("productid");
			service.deleteProduct(productid == null ? "" : productid);
		}
		return new Result("products.jsp", true);
	}

}
