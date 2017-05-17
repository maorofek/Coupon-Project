package test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import dao.CouponType;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import main.Company;
import main.Coupon;
import main.Customer;
import myexception.MyException;

public class DaoTest {
	public static void main(String[] args) throws InterruptedException, MyException {

		DaoTest daotest = new DaoTest();
		daotest.companySets();
		daotest.couponSets();
		daotest.customerSets();
	}

	public void couponSets() throws InterruptedException {
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		Coupon coupon = new Coupon();
		Calendar c = Calendar.getInstance();
		long timestamp = c.getTimeInMillis();
		Date date = new Date(timestamp);

		coupon.setId(1);
		coupon.setTitle("some title");
		coupon.setStartDate(date);
		coupon.setEndDate(date);
		coupon.setAmount(10);
		coupon.setType(CouponType.CAMPING);
		coupon.setMessage("message");
		coupon.setPrice(10);
		coupon.setImage("image");

		try {
			couponDBDAO.createCoupon(coupon);
			System.out.println(couponDBDAO.getCoupon(2));
			System.out.println(couponDBDAO.getAllCoupon().getClass());
			CouponType couponType = CouponType.CAMPING;
			System.out.println(couponDBDAO.getCouponByType(couponType));
			couponDBDAO.updateCoupon(coupon);
			couponDBDAO.removeCoupon(coupon);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void companySets() throws InterruptedException, MyException {
		Company company = new Company();
		CompanyDBDAO companyDBDAO = new CompanyDBDAO();

		company.setId(1);
		company.setCompName("Maor");
		company.setEmail("maor_ofek1@walla.com");

		try {
			companyDBDAO.createCompany(company);
			System.out.println(companyDBDAO.getCompany(1));
			System.out.println(companyDBDAO.getAllCompanies());
			companyDBDAO.updateCompany(company);
			companyDBDAO.removeCompany(company);
			companyDBDAO.getCoupons(company);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void customerSets() throws InterruptedException {
		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer customer = new Customer();
		customer.setId(1);
		customer.setCustName("root");
		customer.setPassword("123456");

		try {
			customerDBDAO.createCustomer(customer);
			System.out.println(customerDBDAO.getCustomer(1));
			System.out.println(customerDBDAO.getAllCustomer());
			customerDBDAO.updateCustomer(customer);
			customerDBDAO.removeCustomer(customer);
			customerDBDAO.getCoupons(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
