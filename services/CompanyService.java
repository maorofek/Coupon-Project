package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.CouponType;
import facade.AdminFacade;
import facade.CompanyFacade;
import main.Company;
import main.Coupon;

@Path("COMPANY")
public class CompanyService {

	@Context
	private HttpServletRequest request;

	public CompanyService() {

	}

	@POST
	@Path("creatCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void createCoupon(@QueryParam("title") String title, @QueryParam("Id") long Id,
			@QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate,
			@QueryParam("amount") int amount, @QueryParam("type") CouponType type,
			@QueryParam("message") String message, @QueryParam("price") double price,
			@QueryParam("image") String image) {

		HttpSession s = request.getSession(false);
		CompanyFacade compFacade = (CompanyFacade) s.getAttribute("facade");

		try {
			Company company = new Company();
			company.setId(Id);
			Coupon coupon = new Coupon();
			coupon.setId(Id);
			coupon.setTitle(title);
			coupon.setStartDate(startDate);
			coupon.setEndDate(endDate);
			coupon.setAmount(amount);
			coupon.setType(type);
			coupon.setMessage(message);
			coupon.setImage(image);
			coupon.setPrice(price);
			compFacade.creatCoupon(coupon);

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@PUT
	@Path("updateCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void updateCoupon(@QueryParam("Id") long Id) {

		HttpSession s = request.getSession(false);
		CompanyFacade compFacade = (CompanyFacade) s.getAttribute("facade");
		try {
			Coupon coupon = new Coupon();
			coupon.setId(Id);
			compFacade.updateCoupon(coupon);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Path("removeCoupon")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void removeCoupon(@QueryParam("Id") String Id) {

		HttpSession s = request.getSession(false);
		CompanyFacade compFacade = (CompanyFacade) s.getAttribute("facade");
		try {
			long couponID = Long.parseLong(Id);
			Coupon coupon = new Coupon();
			coupon.setId(couponID);
			compFacade.removeCoupon(coupon);

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@GET
	@Path("getCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Coupon getCoupon(@QueryParam("Id") String Id) {

		HttpSession s = request.getSession(false);
		CompanyFacade compFacade = (CompanyFacade) s.getAttribute("facade");
		Coupon coupon = new Coupon();

		try {
			long couponID = Long.parseLong(Id);
			coupon = compFacade.getCoupon(couponID);
		} catch (Exception e) {
			e.getMessage();
		}
		return coupon;

	}

	@GET
	@Path("getAllCoupons")
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<Coupon> getAllCoupons(@QueryParam("Id") long Id) {

		AdminFacade adminFacade = new AdminFacade();
		Company company = new Company();
		Coupon coupon = new Coupon();
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		HttpSession s = request.getSession(false);
		CompanyFacade compFacade = (CompanyFacade) s.getAttribute("facade");

		try {
			company = adminFacade.getCompany(Id);
			for (Coupon c : compFacade.getAllCoupon()) {
				coupon.setId(c.getId());
				coupon.setTitle(c.getTitle());
				coupon.setStartDate(c.getStartDate());
				coupon.setEndDate(c.getEndDate());
				coupon.setAmount(c.getAmount());
				coupon.setType(c.getType());
				coupon.setMessage(c.getMessage());
				coupon.setPrice(c.getPrice());
				coupon.setImage(c.getImage());
				coupons.add(coupon);

			}

		} catch (Exception e) {
			e.getMessage();
		}
		return coupons;

	}

	@GET
	@Path("getAllCouponsByType")
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<Coupon> getCouponByType(@QueryParam("type") String type) {

		HttpSession s = request.getSession(false);
		CompanyFacade compFacade = (CompanyFacade) s.getAttribute("facade");
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			coupons = compFacade.getCouponByType(CouponType.valueOf(type));
		} catch (Exception e) {
			e.getMessage();
		}
		return coupons;

	}

	public Date convert(String dateToParse) {
		StringTokenizer tokens = new StringTokenizer(dateToParse, "/");
		String Sday = tokens.nextToken();
		if (Sday.startsWith("0"))
			Sday = Sday.substring(1);
		int day = Integer.parseInt(Sday);

		String Smonth = tokens.nextToken();
		if (Smonth.startsWith("0"))
			Smonth = Smonth.substring(1);
		int month = Integer.parseInt(Smonth) - 1;

		String Syear = tokens.nextToken();
		int year = Integer.parseInt(Syear);

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		return new Date(c.getTimeInMillis());
	}

}
