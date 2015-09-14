package examples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bildIt.hotel.dao.impl.ConnectionUtil;

/**
 * @author Ognjen Lazic
 *
 */
public class ConnectionToDBExample {

	public static void main(String[] args) {

		// /*
		// * Uncomment everything by selecting it and pressing CONTROL+SHIFT+C
		// *
		// * Example how we are going to use connection in methods i did this in
		// * main just for example you will do this in your method example:
		// * login(){ connect/ try/ finlly close connection }
		// */
		//
		// // use our ConnectionUtil class to make a connection
		// ConnectionUtil con = new ConnectionUtil();
		// PreparedStatement preparedStatment = null;
		// String sql = "SELECT * FROM rooms;";
		// try {
		//
		// // call method .connect(); to connect
		// Connection connection = con.connect();
		//
		// // use database
		// // do what ever you wont here
		// preparedStatment = connection.prepareStatement(sql);
		// ResultSet rs = preparedStatment.executeQuery();
		//
		// while (rs.next()) {
		//
		// System.out.println(rs.getString("roomType"));
		// }
		//
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// } finally {
		// try {
		// // ------------------------------- IMPORTANT!!!
		// // CLOSE CONNECTION!!
		// // ------------------------------- IMPORTANT!!!
		// ConnectionUtil.closeConnection();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// }
	}

}