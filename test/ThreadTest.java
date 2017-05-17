package test;

import connection.ConnectionPoolSingleton;

public class ThreadTest extends Thread {

	public static void main(String[] args) throws Exception {

		ConnectionPoolSingleton c = ConnectionPoolSingleton.getInstance();
		while (true) {
			c.getConnection();
			System.out.println(c);

		}
	}

}
