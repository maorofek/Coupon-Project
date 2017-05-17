package connection;

import enums.clientType;
import facade.AdminFacade;
import facade.CouponClientFacade;
import facade.DailyCouponExpirationTask;
import myexception.MyException;

public class CouponSystemSingleton {

	private static CouponSystemSingleton instance = null;
	AdminFacade adminfacade = new AdminFacade();

	private CouponSystemSingleton() {
		super();
	}
	// not finished

	public static CouponSystemSingleton getInstance() throws Exception {
		if (instance == null) {
			instance = new CouponSystemSingleton();
		}
		return instance;
	}

	public CouponClientFacade login(String name, String password, clientType clientType) {
		CouponClientFacade facade = null;
		if (clientType.equals("ADMIN")) {
			adminfacade = new AdminFacade();

		} else if (clientType.equals("COMPANY")) {
			adminfacade = new AdminFacade();

		} else if (clientType.equals("CUSTOMER")) {
			adminfacade = new AdminFacade();

		} else {
			facade = null;
			System.out.println("System: The Client Type must be: Admin or Company or Customer");
		}
		System.out.println("System: Facade = " + clientType);
		return facade;
	}

	public void setDailTaskSleepTime(long sleepTime) {
		DailyCouponExpirationTask.setSleepTime(sleepTime);
	}

	public void shutDown() throws MyException {
		ConnectionPoolSingleton.closeAllConnection();
		DailyCouponExpirationTask.stopTask();
	}
}
