package bildIt.hotel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bildIt.hotel.Users;

/**
 * 
 * @author Bojan Aleksic
 */
public class UsersDao {

	Users user = new Users();
	Users userSession = new Users();

	/**
	 * @param userName
	 * @param password
	 * @return
	 */
	public static Users logOn(String userName, String password) {

		/**
		 * Instantiate the Users object with specified userName and password as
		 * arguments
		 */
		Users user = new Users(userName, password);

		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();
		/** Declare PreparedStatement's prepStat and insertQuery variables */
		PreparedStatement prepStat, insertQuery = null;
		try {
			Connection mysqlConnect = connection.connect();
			/** Select userName, password and isOnline columns from the database */
			prepStat = mysqlConnect
					.prepareStatement(" SELECT `userName`, `password`, `isOnline` FROM `users` ");
			/** Set `isOnline` to 'true' for the current logged user */
			insertQuery = mysqlConnect
					.prepareStatement(" UPDATE `users` SET `isOnline` = 'true' WHERE `userName` = '"
							+ user.getUserName() + "' ");
			ResultSet rs = prepStat.executeQuery();

			if (!rs.next()) {
				System.out.println("no data found!");
				return null;
			} else {
				rs.beforeFirst();
				while (rs.next()) {
					/**
					 * Check if userName from `users` corresponds input's
					 * userName and check same for password
					 */
					if (rs.getString("userName").equals(userName)
							&& rs.getString("password").equals(password)) {
						/**
						 * If `isOnline` column is set to 'false' (user is
						 * offline)
						 */
						if (rs.getString("isOnline").equals("false")) {
							insertQuery.executeUpdate(); // execute update
							return user; // return user object (user is online
											// now)
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the database */
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null; // otherwise return null
	}

	/**
	 * @param userSession
	 */
	public static void logOut(Users userSession) {
		/** Instantiate Users object */
		userSession = new Users(userSession.getUserName(),
				userSession.getPassword());
		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement insertQuery = null;
		try {
			Connection mysqlConnect = connection.connect();
			/**
			 * Update `users` table, set column `isOnline` to 'false' for the
			 * current user
			 */
			insertQuery = mysqlConnect
					.prepareStatement(" UPDATE `users` SET `isOnline` = 'false' WHERE `userName` = '"
							+ userSession.getUserName() + "' ");
			insertQuery.executeUpdate(); // execute update
			userSession.setUserName(null); // set userName to null
			userSession.setPassword(null); // set password to null
			userSession = null; // set object to null
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the database */
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean isAdmin(Users user) {

		/**
		 * Invoke ConnectionUtil Class to establish MySQL connection with the DB
		 */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement prepStat = null; // declare PreparedStatement
											// interface
		try {
			Connection mysqlConnect = connection.connect();
			/** Select "isAdmin" column from the table "users" */
			prepStat = mysqlConnect
					.prepareStatement(" SELECT `isAdmin`, `userName` FROM `users` ");
			ResultSet rs = prepStat.executeQuery(); // execute function
			if (!rs.next()) {
				System.out.println("no data found!");
			} else {
				rs.beforeFirst();
				while (rs.next()) { // while table contains some data
					/**
					 * Check if column "userName" from "users" is equal to
					 * input's user name string
					 */
					if (rs.getString("userName").equals(user.getUserName())) {
						/** Check whether that user is admin */
						if (rs.getString("isAdmin").equals("true")) {
							return true; // if it's admin, return true
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the Database */
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false; // if it's not admin, return false
	}

	/**
	 * Author Ognjen Lazic
	 * 
	 * @param userName
	 *            user name of user that is loged in
	 * @return will check db every time user makes a choice and if user is
	 *         offline wont let him do stuff
	 */
	public static boolean isActive(String userName) {
		boolean result = true;

		ConnectionUtil con = new ConnectionUtil();
		PreparedStatement preparedStatment = null;
		String sql = "SELECT isOnline FROM users WHERE userName='" + userName
				+ "';";
		try {

			Connection connection = con.connect();
			preparedStatment = connection.prepareStatement(sql);
			ResultSet rs = preparedStatment.executeQuery();

			if (rs.next()) {

				// System.out.println(rs.getString("isOnline"));
				// if user is not online stop this thread!
				if (rs.getString("isOnline").equals("false")) {
					result = false;
				} else
					result = true;

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				// ------------------------------- IMPORTANT!!!
				// CLOSE CONNECTION!!
				// ------------------------------- IMPORTANT!!!
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;

	}

	/**
	 * Method - Register New User to the Database
	 * 
	 * @author Bojan Aleksic
	 *
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param isAdmin
	 * @param gender
	 * @param idCard
	 * @param isOnline
	 */
	public static void addUserToDatabase(String userName, String password,
			String firstName, String lastName, String isAdmin, String gender,
			String idCard, String isOnline) {

		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement insertQuery = null;

		try {
			Connection mysqlConnect = connection.connect();
			/** Insert queries into database */
			insertQuery = mysqlConnect
					.prepareStatement(" INSERT INTO `users`(`userName`, `password`, `name`, `lastName`, `isAdmin`, `gender`, `idCard`, `isOnline`) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ");
			insertQuery.setString(1, userName);
			insertQuery.setString(2, password);
			insertQuery.setString(3, firstName);
			insertQuery.setString(4, lastName);
			insertQuery.setString(5, isAdmin);
			insertQuery.setString(6, gender);
			insertQuery.setString(7, idCard);
			insertQuery.setString(8, isOnline);
			insertQuery.executeUpdate(); // execute update
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the database */
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method - Check if user exists in the database
	 * 
	 * @author Bojan Aleksic
	 * 
	 * @param userNameSearch
	 * @return
	 */
	public static boolean isAlreadyRegistered(String userNameSearch) {

		/**
		 * Invoke ConnectionUtil Class to establish MySQL connection with the DB
		 */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement prepStat = null; // declare PreparedStatement
											// interface
		try {
			Connection mysqlConnect = connection.connect();
			/** Select `userName` column from the table `users` */
			prepStat = mysqlConnect
					.prepareStatement(" SELECT `userName` FROM `users` ");
			ResultSet rs = prepStat.executeQuery(); // execute function
			while (rs.next()) {
				/**
				 * Check if column "userName" from "users" is equal to input's
				 * user name
				 */
				if (rs.getString("userName").equals(userNameSearch)) {
					return true; // If user IS found, return true
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the Database */
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false; // If user is NOT found, return false
	}
}
