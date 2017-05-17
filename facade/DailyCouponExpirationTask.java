package facade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionPoolSingleton;
import dbdao.CouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {
	private Connection connection;

	private ConnectionPoolSingleton pool = ConnectionPoolSingleton.getInstance();

	private static boolean quit = false;
	// loading CouponDbDao
	private CouponDBDAO coupondbdao = new CouponDBDAO();

	// Empty Constructor
	public DailyCouponExpirationTask() {
		super();
	}

	@Override
	public void run() {

		while (!quit) {
			try {
				connection = pool.getConnection();
				String query = "SELECT * FROM coupon where END_DATE < curdate()";
				ResultSet results = connection.createStatement().executeQuery(query);
				if (results.next()) {
					long id = results.getLong("id");
					coupondbdao.removeCoupon(coupondbdao.getCoupon(id));
				}
				Thread.sleep(24 * 60 * 60 * 1000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					pool.returnConnection(connection);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public static void stopTask() {
		quit = true;
		System.out.println("The Daily Task Stopped");
	}

	public static void setSleepTime(long sleepTime) {

		// set sleep time

	}
}
