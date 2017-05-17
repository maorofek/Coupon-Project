package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.CouponType;
import dbdao.CustomerDBDAO;
import facade.CustomerFacade;
import main.Coupon;
import main.Customer;

@Path("/CustomerService")
public class CustomerService {

	@Context
	private HttpServletRequest request;

	public CustomerService() {

	}

	@POST
	@Path("purchaseCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Coupon purchaseCoupon(@QueryParam("couponId") Coupon couponId, @QueryParam("Id") String Id) {

		HttpSession s = request.getSession(false);
		CustomerFacade CustFacade = (CustomerFacade) s.getAttribute("facade");
		CustomerFacade customerFacade = new CustomerFacade();
		CustomerDBDAO cutomerdbdao = new CustomerDBDAO();
		try {
			long couponID = Long.parseLong(Id);
			Coupon coupon = null;
			coupon = CustFacade.purchaseCoupon(couponId);
			customerFacade.purchaseCoupon(coupon);
		} catch (Exception e) {
			e.getMessage();
		} finally {
		}
		return couponId;
	}

	@GET
	@Path("getAllPurchased")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<Coupon> getAllPurchased(Coupon coupon) {
		HttpSession s = request.getSession(false);
		CustomerFacade CustFacade = (CustomerFacade) s.getAttribute("facade");
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			coupons = CustFacade.getAllPurchasedCoupons();
		} catch (Exception e) {
			e.getMessage();
		}
		return coupons;
	}

	@GET
	@Path("getAllPurchasedByType")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<Coupon> getAllPurchasedByType(String type) {
		HttpSession s = request.getSession(false);
		CustomerFacade CustFacade = (CustomerFacade) s.getAttribute("facade");
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			coupons = CustFacade.getAllPurchasedCouponsByType(CouponType.valueOf(type));
		} catch (Exception e) {
			e.getMessage();
		}
		return coupons;

	}

	@GET
	@Path("getAllPurchasedByPrice")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<Coupon> getAllPurchasedByPrice(String price) {
		HttpSession s = request.getSession(false);
		CustomerFacade CustFacade = (CustomerFacade) s.getAttribute("facade");
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		long couponPrice = Long.parseLong(price);

		try {
			coupons = CustFacade.getAllPurchasedCouponsByPrice(couponPrice);
		} catch (Exception e) {
			e.getMessage();
		}

		return coupons;
	}

}
