package services;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.CouponSystemSingleton;
import enums.clientType;
import facade.CouponClientFacade;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CouponSystemSingleton system;

	public void init() {
		try {
			system = CouponSystemSingleton.getInstance();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void service(HttpServletRequest req, HttpServletResponse res) {
		HttpSession hs = req.getSession(false);
		if (hs != null) {
			hs.invalidate();
		}
		hs = req.getSession(true);
		String user = req.getParameter("user");
		String pass = req.getParameter("password");
		String type = req.getParameter("type");

		clientType ct = clientType.valueOf(type);
		try {
			CouponClientFacade f = system.login(user, pass, ct);
			if (f != null) {

				hs.setAttribute("facade", f);

				switch (ct) {

				case ADMIN:

					req.getRequestDispatcher("HTML/adminSPA.html").forward(req, res);

					break;

				case COMPANY:
					req.getRequestDispatcher("HTML/companySPA.html").forward(req, res);

					break;

				case CUSTOMER:
					req.getRequestDispatcher("HTML/customerSPA.html").forward(req, res);

					break;
				}
			}

		} catch (Exception e) {
			e.toString();

		}
	}
}