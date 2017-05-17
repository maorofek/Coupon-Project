package test;

import connection.CouponSystemSingleton;
import enums.clientType;

public class MainTest {

	public static void main(String[] args) {

		try {
			CouponSystemSingleton.getInstance().login("admin", "1234", clientType.ADMIN);
			Thread.sleep(3000);
			CouponSystemSingleton.getInstance().shutDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
