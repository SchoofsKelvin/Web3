package controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import domain.Role;
import service.ShopService;

public class AddProductHandler extends Handler {

	private final ShopService service;

	public AddProductHandler(ShopService service) {
		super(Role.ADMINISTRATOR);
		this.service = service;
	}

	@Override
	protected Result handle(HttpServletRequest request, HttpServletResponse response,
		boolean post) {
		if ( !post) return new Result("addproduct.jsp");
		String productid = request.getParameter("productid");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		try {
			double actualPrice = Double.parseDouble(price);
			Product p = new Product(productid, description, actualPrice);
			service.addProduct(p);
		} catch (NumberFormatException e) {
			return new Result("addproduct.jsp", "Invalid price");
		} catch (IllegalArgumentException e) {
			return new Result("addproduct.jsp", e.getMessage());
		}
		request.setAttribute("products", service.getProducts());
		return new Result("products.jsp");
	}

}
