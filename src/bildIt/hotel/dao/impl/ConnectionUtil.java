package bildIt.hotel.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Ognjen Lazic
 *
 */
public class ConnectionUtil {

	/*
	 * this is where you set your database settings
	 */
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/hotel";
	static final String USER = "root";
	static final String PASS = "root";
	static Connection conn = null;
	static Statement stmt = null;

	public Connection connect() throws SQLException {

		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		return conn;
	}

	public static Statement statment() throws SQLException {
		stmt = conn.createStatement();
		return stmt;
	}

	public static void closeConnection() throws SQLException {
		conn.close();
	}

}
