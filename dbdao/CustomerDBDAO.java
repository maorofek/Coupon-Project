package dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import connection.ConnectionPoolSingleton;
import dao.CustomerDAO;
import main.Company;
import main.Coupon;
import main.Customer;
import myexception.MyException;

public class CustomerDBDAO implements CustomerDAO {

	static Company company;

	static private ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();

	// Object Members
	private Connection connection;

	// Constructors
	public CustomerDBDAO() {
		try {
			pool = ConnectionPoolSingleton.getInstance();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// DB Queries

	// java doc
	@Override
	public void createCustomer(Customer customer) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "INSERT INTO `myschema`.`customer` (`CUST_NAME`,`PASSWORD`) VALUES(" + "'"
				+ customer.getCustName() + "'," + "'" + customer.getPassword() + "'" + ")";

		boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("A coupon was successfully inserted into the DB");
		}
	}
	// java doc

	@Override
	public void removeCustomer(Customer customer) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "delete from `myschema`.`customer` where id =" + customer.getId();
		boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("Coupon " + customer.getId() + " was successfully deleted");
		}

	}
	// java doc

	@Override
	public void updateCustomer(Customer customer) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "UPDATE `myschema`.`customer` SET `CUST_NAME` = " + "'" + customer.getCustName()
				+ "',`PASSWORD` = " + "'" + customer.getPassword() + "' WHERE `ID` =" + customer.getId();

		boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("Customer " + customer.getId() + " was successfully updated");
		}

	}
	// java doc

	@Override
	public Customer getCustomer(long id) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select * from `myschema`.`coupon` where id =" + id;
		ResultSet results = connection.createStatement().executeQuery(query);

		Customer customer = createSingleCustomer(results);

		return customer;
	}

	// java doc

	public Collection<Customer> getAllCustomer() throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select * from `myschema`.`customer`";
		ResultSet results = connection.createStatement().executeQuery(query);

		Collection<Customer> customerCollection = new ArrayList<Customer>();
		createCustomerCollection(results, customerCollection);

		return customerCollection;
	}

	// results
	// customerCollection
	// SQLException

	private void createCustomerCollection(ResultSet results, Collection<Customer> customerCollection)
			throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		while (results.next()) {
			System.out.println(results.getRow());
			Customer customer = createSingleCustomer(results);

			customerCollection.add(customer);
		}

	}

	// java doc

	public boolean login(String custName, String password) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		ResultSet results = connection.createStatement().executeQuery(
				"SELECT * FROM customer WHERE Comp_Name='" + custName + "'AND Password ='" + password + "'");

		pool.returnConnection(connection);
		return results.next();

	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select `myschema`.`coupon`.* from `myschema`.`customer_coupon` inner join `myschema`.`coupon` on customer_coupon.ID_Coupon = coupon.id where customer_coupon.ID_cust = "
				+ customer.getId();
		System.out.println(query);
		ResultSet results = connection.createStatement().executeQuery(query);

		List<Coupon> couponCollection = new ArrayList<Coupon>();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		couponDBDAO.createCouponCollection(results);

		return couponCollection;
	}

	private Customer createSingleCustomer(final ResultSet results) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		Customer customer = new Customer();
		if (results.next()) {
			customer.setId(results.getLong("ID"));
			customer.setCustName(results.getString("CUST_NAME"));
			customer.setPassword(results.getString("PASSWORD"));
		}
		;

		return customer;
	}

	public boolean isCompExist(String custName) throws MyException {

		try {
			Connection connection = pool.getConnection();
			String query = "SELECT * FROM customer where CUST_NAME = '" + custName + "'";
			ResultSet results = connection.createStatement().executeQuery(query);

			return results.next();

		} catch (Exception e) {
			throw new MyException(e.getMessage());

		}

	}

	public void addCoupon(Customer customer, Coupon coupon) {
		try {
			Connection connection = pool.getConnection();
			String query = "INSERT INTO `myschema`.`customer_coupon` (`ID_CUST`, `ID_COUPON`) VALUES ("
					+ customer.getId() + " , " + coupon.getId() + ")";
			connection.createStatement().executeQuery(query);
		} catch (SQLException | InterruptedException e) {

		} finally {
			try {
				pool.returnConnection(connection);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isCouponExist(Customer customer, Coupon coupon) throws InterruptedException, MyException {
		Connection connection = pool.getConnection();

		try {

			String query = "SELECT ID_COUPON FROM myschema.customer_coupon where customer_ID = " + customer.getId()
					+ "And ID_COUPON = " + coupon.getId();
			ResultSet results = connection.createStatement().executeQuery(query);

			return (results.next());

		} catch (SQLException e) {
			throw new MyException("An Erorr occured while a customer is trying to purchease a coupon" + e.getMessage());
		} finally {
			pool.returnConnection(connection);
		}

	}

}
