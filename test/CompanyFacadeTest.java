package test;

import java.sql.SQLException;

import dao.CouponType;
import dbdao.CompanyDBDAO;
import facade.CompanyFacade;
import main.Company;
import main.Coupon;
import myexception.MyException;

public class CompanyFacadeTest {

	public static void main(String[] args) {

		CompanyFacade companyFacade = new CompanyFacade();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Company company = new Company();
		Coupon coupon = new Coupon();
		companyFacade.login("ADMIN", "1234");

		CouponType couponType = null;

		try {
			companyFacade.creatCoupon(coupon);
			companyFacade.updateCoupon(coupon);
			companyFacade.removeCoupon(coupon);
			companyFacade.getCoupon(12);
			companyFacade.getAllCoupon();
			companyFacade.getCouponByType(couponType);

		} catch (SQLException | MyException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
