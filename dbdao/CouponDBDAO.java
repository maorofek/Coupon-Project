package dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import connection.ConnectionPoolSingleton;
import dao.CouponDAO;
import dao.CouponType;
import main.Company;
import main.Coupon;
import myexception.MyException;

public class CouponDBDAO implements CouponDAO {

	static private ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();

	// Objects Members
	private Connection connection;

	// Constructor
	public CouponDBDAO() {
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
	public void createCoupon(final Coupon coupon) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "INSERT INTO `myschema`.`coupon`(`TITEL`,`START_DATE`,`END_DATE`,`AMOUNT`,`TYPE`,`MESSAGE`,`PRICE`,`IMAGE`)VALUES("
				+ "'" + coupon.getTitle() + "'," + "'" + coupon.getStartDate() + "'," + "'" + coupon.getEndDate() + "',"
				+ coupon.getAmount() + "," + "'" + coupon.getType() + "'," + "'" + coupon.getMessage() + "',"
				+ coupon.getPrice() + "," + "'" + coupon.getImage() + "'" + ")";

		System.out.println(query);

		final boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("A coupon was successfully inserted into the DB");
		}
	}
	// java doc

	@Override
	public void removeCoupon(Coupon coupon) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "delete from `myschema`.`coupon` where id =" + coupon.getId();
		boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("Coupon " + coupon.getId() + " was successfully deleted");
		}

	}

	// java doc
	public Collection<Coupon> getCouponByCompany(Company company) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();
		String query = "SELECT c.* FROM coupon c inner join company_coupon cp on c.ID = cp.ID_COUPON where cp.ID_COMPANY ="
				+ company.getId();
		System.out.println(query);
		ResultSet results = connection.createStatement().executeQuery(query);
		Collection<Coupon> cc = createCouponCollection(results);

		return cc;

	}

	public void removeCoupon(Integer id) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();
		String query = "delete from `myschema`.`coupon` where id =" + id;
		boolean results = connection.createStatement().execute(query);

		query = "delete from `myschema`.`company_coupon` where id =" + id;
		results = connection.createStatement().execute(query);

		query = "delete from `myschema`.`customer_coupon` where id =" + id;
		results = connection.createStatement().execute(query);

		if (results) {
			System.out.println("Coupon " + id + " was successfully deleted");
		}

	}

	@Override
	public void updateCoupon(Coupon coupon) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		final String query = "UPDATE `myschema`.`coupon` SET `TITEL` = " + "'" + coupon.getTitle() + "',`START_DATE` = "
				+ "'" + coupon.getStartDate() + "',`END_DATE` = " + "'" + coupon.getEndDate() + "',`AMOUNT` = " + "'"
				+ coupon.getAmount() + "',`TYPE` = " + "'" + coupon.getType() + "',`MESSAGE` = " + "'"
				+ coupon.getMessage() + "',`PRICE` = " + "'" + coupon.getPrice() + "',`IMAGE` = " + "'"
				+ coupon.getImage() + "' WHERE `ID` =" + coupon.getId();

		boolean results = connection.createStatement().execute(query);
		if (results) {
			System.out.println("Coupon " + coupon.getId() + " was successfully updated");
		}

	}
	// java doc

	@Override
	public Coupon getCoupon(long id) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select * from `myschema`.`coupon` where id =" + id;
		ResultSet results = connection.createStatement().executeQuery(query);

		Coupon coupon = createSingleCoupon(results);

		return coupon;
	}
	// java doc

	@Override
	public Collection<Coupon> getAllCoupon() throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select * from `myschema`.`coupon`";
		ResultSet results = connection.createStatement().executeQuery(query);

		Collection<Coupon> cc = createCouponCollection(results);

		return cc;
	}
	// java doc

	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		String query = "select * from `myschema`.`coupon` where type = '" + couponType + "'";
		ResultSet results = connection.createStatement().executeQuery(query);

		Collection<Coupon> coupunsCollection = createCouponCollection(results);

		return coupunsCollection;
	}

	public Collection<Coupon> createCouponCollection(ResultSet results) throws SQLException, InterruptedException {
		Collection<Coupon> couponCollection = new ArrayList<Coupon>();

		while (results.next()) {

			Coupon coupon = createSingleCoupon(results);
			couponCollection.add(coupon);
		}
		return couponCollection;
	}

	private Coupon createSingleCoupon(ResultSet results) throws SQLException, InterruptedException {
		Connection connection = pool.getConnection();

		Coupon coupon = new Coupon();
		if (results.next()) {
			coupon.setId(results.getLong("ID"));
			coupon.setTitle(results.getString("TITEL"));
			coupon.setStartDate(results.getDate("START_DATE"));
			coupon.setEndDate(results.getDate("END_DATE"));
			coupon.setAmount(results.getInt("AMOUNT"));
			CouponType enumVal = CouponType.valueOf(results.getString("TYPE"));
			coupon.setType(enumVal);
			coupon.setMessage(results.getString("MESSAGE"));
			coupon.setPrice(results.getDouble("PRICE"));
			coupon.setImage(results.getString("IMAGE"));
		}

		return coupon;
	}

	public boolean isCouponExist(String couponName) throws MyException {

		try {
			Connection connection = pool.getConnection();
			String query = "SELECT * FROM coupon where TITEL = '" + couponName + "'";
			ResultSet results = connection.createStatement().executeQuery(query);

			return results.next();

		} catch (Exception e) {
			throw new MyException(e.getMessage());

		}
	}
}