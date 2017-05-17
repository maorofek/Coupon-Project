package test;

import java.sql.SQLException;

import dbdao.CompanyDBDAO;
import facade.AdminFacade;
import main.Company;
import myexception.MyException;

public class RemoveTest {

	public static void main(String[] args) {
		AdminFacade adminFacade = new AdminFacade();

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Company company = new Company();
		try {

			company.setId(30);
			company.setCompName("sss");
			company.setEmail("ss@walla.com");

			adminFacade.login("ADMIN", "1234");

			adminFacade.createCompany(company);
			companyDBDAO.removeCompany(company);

		} catch (SQLException | MyException | InterruptedException e) {

			e.printStackTrace();

		}

	}

}
