
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = ("com.mysql.jdbc.Driver");
	static final String DB_URL = "jdbc:mysql://localhost";

	// Database credentials
	static final String DB_NAME = "myschema";
	static final String USER = "root";
	static final String PASS = "123456";

	public static Connection getConnection() {
		Connection connection;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection successfuly");
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		System.out.println("connection field");
		return null;
	}

	public static void returnConnection(Connection connection) {
		
		
	}

	public static void closeAllConnection() {

		
	}

}
