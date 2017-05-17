package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import myexception.MyException;

public class ConnectionPoolSingleton {

	private static final int MAX_CONNECTIONs = 100;
	private static ConnectionPoolSingleton instance = null;
	private static Collection<Connection> pool;
	private static Collection<Connection> connections = new ArrayList<Connection>();

	private ConnectionPoolSingleton() {

	}

	public static ConnectionPoolSingleton getInstance() {
		if (instance == null) {
			instance = new ConnectionPoolSingleton();
			try {
				instance.init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	private void init() throws Exception {
		pool = new ArrayList<Connection>();
		for (int i = 0; i < MAX_CONNECTIONs; i++) {
			pool.add(createConnection());
		}

	}

	private Connection createConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");

		Connection connection = (Connection) DriverManager
				.getConnection("JDBC:mysql://localhost:3306/myschema?autoReconnect&useSSL=false", "root", "123456");
		return connection;
	}

	public synchronized Connection getConnection() throws InterruptedException {
		while (pool.size() == 0) {
			System.out.println("waiting for connection");
			System.out.println("\t Current size of pool is:" + pool.size() + "\t Method name is: "
					+ Thread.currentThread().getStackTrace()[2].getMethodName());
			wait();
		}
		notifyAll();

		Connection connection = pool.iterator().next();
		pool.remove(connection);
		connections.add(connection);
		notifyAll();
		return connection;

	}

	public synchronized void returnConnection(java.sql.Connection connection) throws InterruptedException {

		while (pool.size() >= MAX_CONNECTIONs) {
			System.out.println("\t waiting for returning connection \t Current size of pool is:" + pool.size()
					+ "\t Method name is: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			wait();
		}

		pool.add(connection);
		connections.remove(connection);
		notifyAll();

	}

	public static void closeAllConnection() throws MyException {

		try {
			for (Connection connection : pool) {
				connection.close();
			}
			for (Connection connection : connections) {
				connection.close();

			}

		} catch (SQLException e) {
			throw new MyException("an error occured while trying to close all connections : " + e.getMessage());
		}
	}

}
