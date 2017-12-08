package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.handlers.*;
import domain.Person;
import service.ShopService;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {

	// select *,pg_terminate_backend(pid) from pg_stat_activity
	// where usename = 'r0620441' and application_name not like 'pgAdmin III%'

	private static final long				serialVersionUID	= -2670825831853246200L;

	private static ShopService				service;
	private static HashMap<String, Handler>	handlers			= new HashMap<>();

	private static String getPassword(ServletContext con) {
		try {
			String pass = System.getenv("password");
			if (pass != null) return pass;
		} catch (Exception e) {}
		try {
			String pass = con.getInitParameter("db_password");
			if (pass != null) return pass;
		} catch (Exception e2) {}
		throw new RuntimeException("Couldn't get password");
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext con = getServletContext();
		Properties props = new Properties();
		props.setProperty("storage", con.getInitParameter("storage"));
		props.setProperty("url", con.getInitParameter("db_url"));
		props.setProperty("ssl", con.getInitParameter("db_ssl"));
		props.setProperty("sslfactory", con.getInitParameter("db_sslfactory"));
		props.setProperty("user", con.getInitParameter("db_user"));
		props.setProperty("scheme_person", con.getInitParameter("db_scheme_person"));
		props.setProperty("scheme_product", con.getInitParameter("db_scheme_product"));
		props.setProperty("password", getPassword(con));
		service = new ShopService(props);
		initHandlers();
	}

	private void initHandlers() {
		handlers.put("overview", new OverviewHandler(service));
		handlers.put("updateperson", new UpdatePersonHandler(service));
		handlers.put("deleteperson", new DeletePersonHandler(service));

		handlers.put("products", new ProductsHandler(service));
		handlers.put("addproduct", new AddProductHandler(service));
		handlers.put("deleteproduct", new DeleteProductHandler(service));

		handlers.put("login", new LoginHandler(service));
		handlers.put("logout", new LogoutHandler());
		handlers.put("register", new RegisterHandler(service));

		handlers.put("switchcolour", new SwitchColourHandler());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doRequest(request, response, false);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		doRequest(request, response, true);
	}

	private void doRequest(HttpServletRequest request, HttpServletResponse response,
		boolean post) throws ServletException, IOException {
		String action = request.getParameter("action");
		Person user = (Person) request.getSession().getAttribute("user");
		Handler handler = action == null ? null : handlers.get(action.toLowerCase());
		if (handler == null) {
			error("Page not found", request, response);
		} else if ( !handler.canAccess(user)) {
			error("Not authorized", request, response);
		} else {
			handler.handleRequest(request, response, post);
		}
	}

	private static void error(String message, HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("error", message);
		forward("overview.jsp", request, response);
	}

	private static void forward(String destination, HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch = request.getRequestDispatcher(destination);
		dispatch.forward(request, response);
	}

}
