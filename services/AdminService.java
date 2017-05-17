package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import facade.AdminFacade;
import main.Company;
import main.Customer;
import main.WebCompany;
import main.WebCustomer;
import myexception.MyException;

@Path("/AdminService")
public class AdminService {

	@Context
	private HttpServletRequest request;

	public AdminService() {

	}

	@POST
	@Path("/createCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void creatCompany(@QueryParam("compName") String compName, @QueryParam("password") String password,
			@QueryParam("email") String email) {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");

		try {
			Company company = new Company();
			company.setCompName(compName);
			company.setEmail(email);
			company.setPassword(password);
			admin.createCompany(company);
		} catch (Exception e) {
			e.getMessage();
		}

	}

	@PUT
	@Path("/updateCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void updateCompany(@QueryParam("compID") long compID, @QueryParam("password") String password,
			@QueryParam("email") String email) throws SQLException, InterruptedException {

		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");

		Company company = admin.getCompany(compID);
		company.setEmail(email);
		company.setPassword(password);
		admin.updateCompany(company);
	}

	@DELETE
	@Path("/removeCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void removeCompany(@QueryParam("compID") long compID)
			throws SQLException, InterruptedException, MyException {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");
		try {
			Company company = admin.getCompany(compID);
			admin.removeCompany(company);

		} catch (Exception e) {
			e.getMessage();
		}
	}

	@GET
	@Path("/getCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public WebCompany getCompany(@QueryParam("longID") long compId) {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");
		Company company = null;
		WebCompany webCompany = null;

		try {
			company = admin.getCompany(compId);
			webCompany = new WebCompany();
			webCompany.setCompName(company.getCompName());
			webCompany.setEmail(company.getEmail());
			webCompany.setCoupons(company.getCoupons());

		} catch (Exception e) {
			e.getMessage();
		}

		return webCompany;

	}

	@GET
	@Path("/getAllCompanies")
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<WebCompany> getAllCompanies() throws SQLException, InterruptedException {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");

		Collection<Company> companies;
		Collection<WebCompany> webCompanies;

		companies = admin.getAllCompanies();
		webCompanies = new ArrayList<WebCompany>();

		try {
			for (Company company : companies) {

				WebCompany webCompany = new WebCompany();
				webCompany.setId(company.getId());
				webCompany.setCompName(company.getCompName());
				webCompany.setEmail(company.getEmail());
				webCompany.setCoupons(company.getCoupons());
				webCompanies.add(webCompany);
			}

		} catch (Exception e) {
			e.getMessage();

		}

		return webCompanies;
	}

	@POST
	@Path("/createCustomer")
	@Produces(MediaType.TEXT_PLAIN)
	public void createCustomer(@QueryParam("custName") String custName, @QueryParam("password") String password) {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");
		try {
			Customer customer = new Customer();
			customer.setCustName(custName);
			customer.setPassword(password);
			admin.createCustomer(customer);

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void updateCustomer(@QueryParam("custID") long custID, @QueryParam("password") String password) {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");

		try {

			Customer customer = admin.getCustomer(custID);
			customer.setPassword(password);
			admin.updateCustomer(customer);

		} catch (Exception e) {
			e.getMessage();
		}

	}

	@DELETE
	@Path("/removeCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void removeCustomer(@QueryParam("custID") long custID) throws SQLException, InterruptedException {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");
		try {
			Customer customer = admin.getCustomer(custID);
			admin.removeCustomer(customer);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@GET
	@Path("/getCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public WebCustomer getCustomer(@QueryParam("custID") long custID) {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");

		Customer customer = null;
		WebCustomer webCustomer = null;

		try {
			customer = admin.getCustomer(custID);
			webCustomer = new WebCustomer();
			webCustomer.setCustName(customer.getCustName());
			webCustomer.setCoupons(customer.getCoupons());

		} catch (Exception e) {
			e.getMessage();
		}

		return webCustomer;

	}

	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<WebCustomer> getAllCustomer() {
		HttpSession s = request.getSession(false);
		AdminFacade admin = (AdminFacade) s.getAttribute("facade");

		Collection<Customer> customers = null;
		Collection<WebCustomer> webCustomers = null;

		try {

			customers = admin.getAllCustomer();
			webCustomers = new ArrayList<WebCustomer>();

			for (Customer customer : customers) {
				WebCustomer webCustomer = new WebCustomer();
				webCustomer.setId(customer.getId());
				webCustomer.setCustName(customer.getCustName());
				webCustomer.setPassword(customer.getPassword());

				webCustomers.add(webCustomer);

			}

		} catch (Exception e) {
			e.getMessage();
		}

		return webCustomers;

	}

}
