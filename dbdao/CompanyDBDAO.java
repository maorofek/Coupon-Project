package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import connection.ConnectionPoolSingleton;
import dao.CompanyDAO;
import main.Company;
import main.Coupon;
import myexception.MyException;

public class CompanyDBDAO implements CompanyDAO {

	static private ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();
	// Object Members
	Connection connection;

	public CompanyDBDAO() {
		try {
			pool = ConnectionPoolSingleton.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// DB Queries
	// java doc

	@Override
	public void createCompany(Company company) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "INSERT INTO `myschema`.`company` (`COMP_NAME`,`PASSWORD`,`EMAIL`) VALUES(" + "'"
				+ company.getCompName() + "'," + "'" + company.getPassword() + "'," + "'" + company.getEmail() + "'"
				+ ")";

		boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("A coupon was successfully inserted into the DB");
		}

	}

	@Override
	public void removeCompany(Company company) throws SQLException, InterruptedException, MyException {
		Company companyToDelete = getCompByName(company);
		if (companyToDelete != null) {
			deleteCompanyCouponsFromTheJoinTable(companyToDelete);
			deleteCompanyFromCompanyTable(companyToDelete);
		}
	}

	private void deleteCompanyFromCompanyTable(Company companyToDelet) throws MyException {
		String query = "delete from company where id = " + companyToDelet.getId();
		generateQueryToDatabase(query);
		System.out.println(query);
	}

	private void deleteCompanyCouponsFromTheJoinTable(Company companyToDelet) throws MyException {
		String query = "delete from company_coupon where ID_COMPANY = " + companyToDelet.getId();
		generateQueryToDatabase(query);
		System.out.println(query);
	}

	public Company getCompByName(Company company) throws InterruptedException {
		Connection connection = pool.getConnection();
		String query1 = "select * from company where COMP_NAME = " + company.getCompName();

		System.out.println(company);
		return company;
	}

	@Override
	public void removeCompanies(Company company) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		Collection<Coupon> cc = getCoupons(company);
		CouponDBDAO cdbdao = new CouponDBDAO();
		try {
			for (Coupon coupon : cc) {
				cdbdao.removeCoupon(coupon);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		String query = "delete from company where id =   " + company.getId();
		System.out.println(company.getId());

		connection.createStatement().executeQuery(query);

	}

	// java doc

	@Override
	public void updateCompany(Company company) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "UPDATE `myschema`.`Company` SET `COMP_NAME` = " + "'" + company.getCompName()
				+ "',`PASSWORD` = " + "'" + company.getPassword() + "',`EMAIL` = " + "'" + company.getEmail()
				+ "' WHERE `COMP_NAME` ='" + company.getCompName() + "'";
		System.out.println(query);
		boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("Customer " + company.getId() + " was successfully updated");
		}

	}

	// java doc

	@Override
	public Company getCompany(long id) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select * from `myschema`.`Company` where id =" + id;
		ResultSet results = connection.createStatement().executeQuery(query);

		Company company = createSingleCompany(results);

		return company;
	}

	// results
	// return

	private Company createSingleCompany(ResultSet results) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		Company company = new Company();
		if (results.next()) {
			company.setId(results.getLong("ID"));
			company.setCompName(results.getString("COMP_NAME"));
			company.setPassword(results.getString("PASSWORD"));
			company.setEmail(results.getString("EMAIL"));
		}
		;

		return company;
	}
	// java doc

	@Override
	public Collection<Company> getAllCompanies() throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select * from `myschema`.`Company`";
		ResultSet results = connection.createStatement().executeQuery(query);

		List<Company> companyCollection = new ArrayList<Company>();
		createCompanyCollection(results, companyCollection);

		return companyCollection;
	}

	// results
	// companyCollection
	// throws SQLException

	private void createCompanyCollection(ResultSet results, List<Company> companyCollection)
			throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		while (results.next()) {

			// System.out.println(results.getRow());
			Company company = createSingleCompany(results);
			companyCollection.add(company);
		}

	}

	// java doc

	@Override
	public Collection<Coupon> getCoupons(Company company) throws SQLException, InterruptedException {
		CouponDBDAO coupondbdao = new CouponDBDAO();
		return coupondbdao.getCouponByCompany(company);

	}

	// java doc

	@Override
	public boolean login(String compName, String password) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();
		connection.createStatement().executeQuery(
				"SELECT * FROM company WHERE Comp_Name='" + compName + "'AND Password`='" + password + "'");

		pool.returnConnection(connection);

		return false;
	}

	// java doc

	@Override
	public String getCompanyById(long id) throws InterruptedException {
		Connection connection = pool.getConnection();
		String name = null;
		String query = "SELECT * FROM company where COMP_NAME = '" + name + "'";
		System.out.println(query);

		return query.toString();
	}

	// java doc
	public boolean isCompExist(String name) throws MyException {

		try {
			Connection connection = pool.getConnection();
			String query = "SELECT * FROM company where COMP_NAME = '" + name + "'";
			ResultSet results = connection.createStatement().executeQuery(query);
			return results.next();

		} catch (Exception e) {
			throw new MyException(e.getMessage());

		}
	}
	// java doc

	public int findCompanyAndReturnId(Company company) {
		try {
			String name = company.getCompName();
			String password = company.getPassword();
			String sql = "insert into company ( name + password) values (....)-----------------------------------";
			System.out.println(sql);
			PreparedStatement statement = connection.prepareStatement(sql);
			// Statement.RETURN_GENERATED_KEYS();

			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			generatedKeys.next();

			int newId = generatedKeys.getInt(1);

			return newId;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	private void generateQueryToDatabase(String query) throws MyException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new MyException("Cannot create connection at CompanyDBDAO.generateQueryToDatabase");
		}
		try {
			connection.createStatement().execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(query);
			throw new MyException("Query issue at CustomerDBDAO.CompanyDBDAO.generateQueryToDatabase");
		} finally {
			try {
				pool.returnConnection(connection);
			} catch (InterruptedException e) {
				throw new MyException("Cannot return connection at CompanyDBDAO.generateQueryToDatabase");
			}
		}
	}

}