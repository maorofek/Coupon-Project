package facade;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import dao.CouponType;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import main.Coupon;
import myexception.MyException;

public class CompanyFacade {

	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();

	public CompanyFacade() {

	}

	public boolean creatCoupon(Coupon coupon) throws SQLException, MyException, InterruptedException {
		if (!couponDBDAO.isCouponExist(coupon.getTitle())) {
			couponDBDAO.createCoupon(coupon);
			return true;
		}
		return false;
	}

	public void removeCoupon(Coupon coupon) throws SQLException, InterruptedException {
		couponDBDAO.removeCoupon(coupon);

	}

	public void updateCoupon(Coupon coupon) throws SQLException, InterruptedException {

		couponDBDAO.updateCoupon(coupon);

	}

	public Coupon getCoupon(long couponID) throws SQLException, InterruptedException {
		couponDBDAO.getCoupon(couponID);
		return null;

	}

	// -----------------waiting for finished--------------------------------
	public Collection<Coupon> getAllCoupon() throws SQLException, InterruptedException {

		return couponDBDAO.getAllCoupon();
	}

	public static List<Coupon> getCouponByType(CouponType couponType) {

		List<Coupon> getCouponByType = CompanyFacade.getCouponByType(couponType);
		return getCouponByType;

	}

	public boolean login(String compName, String password) {
		boolean bool = false;
		if (compName.equals("COMPANY") & password.equals("1234")) {
			bool = true;
		} else {
			bool = false;
			System.out.println("wrong password/name");
		}

		return bool;
	}
}
