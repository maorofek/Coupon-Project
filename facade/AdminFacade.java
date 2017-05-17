package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dbdao.CompanyDBDAO;
import dbdao.CustomerDBDAO;
import main.Company;
import main.Customer;
import myexception.MyException;

public class AdminFacade implements CouponClientFacade {
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	Company company = null;

	@Override
	public boolean login(String name, String password) {
		boolean bool = false;
		if (name.equals("ADMIN") & password.equals("1234")) {
			AdminFacade adminf = new AdminFacade();
			System.out.println("Admin logged in");
			bool = true;
		} else {
			bool = false;
			System.out.println("wrong password/name");
		}

		return bool;
	}

	public boolean createCompany(Company company) throws SQLException, MyException, InterruptedException {
		if (!companyDBDAO.isCompExist(company.getCompName())) {
			companyDBDAO.createCompany(company);
			System.out.println("created seccessfuly" + " :" + company.getCompName());

			return true;
		}
		System.out.println(company.getCompName() + "company name is exsit");

		return false;
	}

	public void removeCompany(Company company) throws SQLException, InterruptedException, MyException {
		companyDBDAO.removeCompany(company);
		System.out.println("Company" + company.getCompName() + " was successfuly deleted");

	}

	public void updateCompany(Company company) throws SQLException, InterruptedException {
		companyDBDAO.updateCompany(company);
		System.out.println("successfuly update");
	}

	public Company getCompany(long id) throws SQLException, InterruptedException {
		System.out.println(companyDBDAO.getCompany(id));
		return company;
	}

	public Collection<Company> getAllCompanies() throws SQLException, InterruptedException {
		Collection<Company> getAll = companyDBDAO.getAllCompanies();
		System.out.println("Companies List:" + getAll);
		return getAll;
	}

	public boolean createCustomer(Customer customer) throws SQLException, MyException, InterruptedException {

		if (!customerDBDAO.isCompExist(customer.getCustName())) {
			customerDBDAO.createCustomer(customer);
			System.out.println("created seccessfuly" + " :" + customer.getCustName());

			return true;
		}
		System.out.println(customer.getCustName() + "company name is exsit");

		return false;
	}

	public void removeCustomer(Customer customer) throws SQLException, InterruptedException {
		customerDBDAO.removeCustomer(customer);

	}

	public void updateCustomer(Customer customer) throws SQLException, InterruptedException {
		customerDBDAO.removeCustomer(customer);

	}

	public Customer getCustomer(long custID) throws SQLException, InterruptedException {
		customerDBDAO.getCustomer(custID);
		return getCustomer(custID);
	}

	public Collection<Customer> getAllCustomer() throws SQLException, InterruptedException {
		Collection<Customer> getAllCust = new ArrayList<Customer>();
		try {
			getAllCust = customerDBDAO.getAllCustomer();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return getAllCust;
	}

}
