package test;

import java.sql.SQLException;

import dao.CompanyDAO;
import dbdao.CompanyDBDAO;
import facade.AdminFacade;
import main.Company;
import main.Customer;
import myexception.MyException;

public class AdminfacadeTest {

	public static void main(String[] args) throws InterruptedException {

		AdminFacade adminFacade = new AdminFacade();

		Company company = new Company();
		Company company2 = new Company();

		Customer customer = new Customer();
		try {

			company2.setId(20);
			company2.setCompName("adi");
			company2.setEmail("LiavGedalov1@walla.com");

			company.setId(30);
			company.setCompName("dana");
			company.setEmail("Dan@walla.com");

			adminFacade.login("ADMIN", "1234");
			adminFacade.createCompany(company);
			adminFacade.createCompany(company2);
			// Update Email
			company.setEmail("successfuly@walla.com");
			CompanyDBDAO companydbdao = null;
			
			companydbdao.getCompanyById(50);
			adminFacade.updateCompany(company);

	//	    Exception
			adminFacade.removeCompany(company2);
			adminFacade.removeCompany(company);
	//		adminFacade.getCompany(41);
	//		adminFacade.getAllCompanies();

		} catch (SQLException | MyException e) {

			e.printStackTrace();
		}
	/*	try {
			customer.setCustName("Sharon");
			customer.setPassword("123");

			adminFacade.createCustomer(customer);
			customer.setCustName("Dani");

			adminFacade.updateCustomer(customer);
			adminFacade.removeCustomer(customer);
			adminFacade.getCustomer(2);
			adminFacade.getAllCustomer();

		} catch (Exception e) {

			e.getMessage();

		}*/

	}

}
