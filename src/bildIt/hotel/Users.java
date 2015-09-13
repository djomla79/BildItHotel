package bildIt.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bildIt.hotel.dao.impl.ConnectionUtil;

public class Users {

	/**
	 * ---------------------- Class Users
	 * 
	 * @author Bojan Aleksic ----------------------
	 */

	/** Declare data fields for users */
	private static String userName;
	private static String password;
	private String firstName;
	private String lastName;
	@SuppressWarnings("unused")
	private boolean isAdmin;
	private String gender;
	private int idCard;
	private String isOnline;

	/** Instantiate static Users object with userName and password */
	public static Users user = new Users(userName, password);

	/** An empty constructor */
	public Users() {

	}

	/** Constructor with specified user name and password */
	public Users(String userName, String password) {
		Users.userName = userName;
		Users.password = password;
	}

	/** Constructor with specified all parameters */
	public Users(String userName, String password, String firstName,
			String lastName, boolean isAdmin, String gender, int idCard,
			String isOnline) {
		Users.userName = userName;
		Users.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = isAdmin;
		this.gender = gender;
		this.idCard = idCard;
		this.isOnline = isOnline;
	}

	// ///////////////////////////////////////////////////////////////////////////////////

	/** Setters */

	public void setUserName(String userName) {
		Users.userName = userName;
	}

	public void setPassword(String password) {
		Users.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/** Check if logged user is admin */
	public static boolean isAdmin() {

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
			while (rs.next()) { // while table contains some data
				/**
				 * Check if column "userName" from "users" is equal to input's
				 * user name string
				 */
				if (rs.getString("userName").equals(user.getUserName())) {
					/** Check whether that user is admin */
					if (rs.getString("isAdmin").equals("true")) {
						return true; // if it's admin, return true
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

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	/** Getters */

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getGender() {
		return gender;
	}

	public int getIdCard() {
		return idCard;
	}

	public String isOnline() {
		return isOnline;
	}

	// ///////////////////////////////////////////////////////////////////////////////////

	/** User's login method */
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
			while (rs.next()) {
				/**
				 * Check if userName from `users` corresponds input's userName
				 * and check same for password
				 */
				if (rs.getString("userName").equals(userName)
						&& rs.getString("password").equals(password)) {
					/** If `isOnline` column is set to 'false' (user is offline) */
					if (rs.getString("isOnline").equals("false")) {
						insertQuery.executeUpdate(); // execute update
						return user; // return user object (user is online now)
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

	// ///////////////////////////////////////////////////////////////////////////////////

	/** User's log out method */
	public static void logOut(Users userSession) {
		/** Instantiate Users object */
		userSession = new Users(userName, password);
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

}