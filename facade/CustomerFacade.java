package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import dao.CouponType;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import main.Coupon;
import main.Customer;
import myexception.MyException;

/**
 * 
 * @author maor_
 *
 */
public class CustomerFacade {
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	Customer customer = new Customer();
	Coupon coupon = new Coupon();

	public CustomerFacade() {

	}

	public Coupon purchaseCoupon(Coupon coupon) throws SQLException, InterruptedException, MyException {
		customerDBDAO.isCouponExist(customer, coupon);

		Date endDate = coupon.getEndDate();
		Date toDay = new Date();
		Calendar endDate1 = Calendar.getInstance();
		Calendar toDay1 = Calendar.getInstance();
		toDay1.setTimeInMillis(endDate.getTime());
		endDate1.setTimeInMillis(toDay.getTime());
		toDay.after(endDate);

		customerDBDAO.addCoupon(customer, coupon);
		return coupon;

	}

	public Collection<Coupon> getAllPurchasedCoupons() throws SQLException, InterruptedException {

		return customerDBDAO.getCoupons(customer);
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws SQLException, InterruptedException {
		Collection<Coupon> couponList = new ArrayList<Coupon>();
		for (Coupon coupon : getAllPurchasedCoupons()) {
			if (coupon.getType() == type)
				couponList.add(coupon);
		}
		return couponList;
	}

	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws SQLException, InterruptedException {
		Collection<Coupon> couponList = new ArrayList<Coupon>();
		for (Coupon coupon : getAllPurchasedCoupons()) {
			if (coupon.getPrice() == price)
				couponList.add(coupon);
		}
		return couponList;
	}

	public boolean login(String custName, String password) {
		boolean bool = false;
		if (custName.equals("CUSTOMER") & password.equals("1234")) {
			bool = true;
		} else {
			bool = false;
			System.out.println("wrong password/name");
		}

		return bool;
	}

}
