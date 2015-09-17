package bildIt.hotel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import bildIt.hotel.Users;

/**
 * 
 * @author Bojan Aleksic
 */
public class UsersDao {

	/**
	 * @author Bojan Aleksic
	 * @param userName
	 *            User name of user that is logging in
	 * @param password
	 *            Password of user that is logging in
	 * @return returns Users object if login success and returns null if it
	 *         fails
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
	 * @author Bojan Aleksic
	 * @param userSession
	 *            sets current login user to null
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
	 * @author Bojan Aleksic
	 * @param user
	 *            that is going to be checkd if its admin
	 * @return return true if its admin false if not
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
			rs.beforeFirst();
			if (rs.next()) {

				// if user is found check if its online and return false if not
				if (rs.getString("isOnline").equals("false")) {
					result = false;
				} else
					result = true;

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
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
	 *            User name of user that is going to be added to db.
	 * @param password
	 *            Password of user that is going to be added to db.
	 * @param Name
	 *            Name of user that is going to be added to db.
	 * @param lastName
	 *            Last Name of user that is going to be added to db.
	 * @param isAdmin
	 *            Add true if admin false if not in db.
	 * @param gender
	 *            Add male or female to db.
	 * @param idCard
	 *            ID card of user that is going to be added to db.
	 * @param isOnline
	 *            Is user online is set to false(user is just created).
	 */
	public static void addUserToDatabase(String userName, String password,
			String Name, String lastName, String isAdmin, String gender,
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
			insertQuery.setString(3, Name);
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
	 *            User Name that we are going to search in db.
	 * @return True if User Name is already registerd otherwise false.
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

	/**
	 * @author Bojan Aleksic
	 * @return String value of all users that are not having admin rights.
	 */
	public static String getUsersFromDB() {

		String s = "";

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
					.prepareStatement(" SELECT `userName` FROM `users` WHERE `isAdmin` = 'false' ");
			ResultSet rs = prepStat.executeQuery(); // execute function
			while (rs.next()) {
				s += "[" + rs.getString("userName") + "]\n";
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
		return s;
	}

	/**
	 * @author Ognjen Lazic
	 * @return returns List<Users> that are not having admin rights.
	 */
	public static List<Users> getUsersFromDBtoUsersList() {

		List<Users> result = new ArrayList<Users>();

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
					.prepareStatement(" SELECT * FROM `users` WHERE `isAdmin` = 'false' ");
			ResultSet rs = prepStat.executeQuery(); // execute function

			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {

					Users tempUser = new Users();
					tempUser.setUserName(rs.getString("userName"));
					tempUser.setPassword(rs.getString("password"));
					tempUser.setFirstName(rs.getString("name"));
					tempUser.setLastName(rs.getString("lastname"));
					tempUser.setAdmin(rs.getBoolean("isAdmin"));
					tempUser.setGender(rs.getString("gender"));
					tempUser.setIdCard(rs.getString("idCard"));
					tempUser.setIsOnline(rs.getBoolean("isOnline"));
					tempUser.setIsCheckdIn(rs.getBoolean("isCheckdIn"));
					tempUser.setDateOfCheckingIn(rs.getDate("dateOfCheckingIn"));
					tempUser.setUserCheckdOutDate(rs
							.getDate("userCheckdOutDate"));

					result.add(tempUser);

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
		return result;
	}

	/**
	 * Method - Update user in the Database (change information)
	 * 
	 * @author Bojan Aleksic
	 *
	 * @param userName
	 *            User name of user that we are going to edit.
	 * @param column
	 *            Field that is going to be edited.
	 * @param queryToUpdate
	 *            New value for the field that we are editing.
	 */
	public static void updateUserInDatabase(String userName, String column,
			String queryToUpdate) {

		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement updateQuery = null;

		try {
			Connection mysqlConnect = connection.connect();
			/**
			 * Update query in database at specified column with specified query
			 */
			updateQuery = mysqlConnect.prepareStatement(" UPDATE `users` SET "
					+ column + " = ? WHERE `userName` = '" + userName + "' ");
			updateQuery.setString(1, queryToUpdate);
			updateQuery.executeUpdate(); // execute update
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
	 * @author Mladen
	 * @param userName
	 *            User name of user that we are searching
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @return returns String data about our user
	 */
	public static String findUserByUserName(String userName) {
		String result = "";

		// Users user = new Users(userName);

		ConnectionUtil con = new ConnectionUtil();

		PreparedStatement preparedStatement = null;

		String sql = "SELECT * FROM users WHERE `userName` = '" + userName
				+ "' ";

		try {

			Connection connection = con.connect();

			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			if (!rs.next()) {
				result = null;
			} else {

				result = "[userName]\t" + rs.getString("userName") + " \n"
						+ "[password]\t" + rs.getString("password") + " \n"
						+ "[name]\t\t" + rs.getString("Name") + " \n"
						+ "[lastName]\t" + rs.getString("lastName") + " \n"
						+ "[isAdmin]\t" + rs.getBoolean("isAdmin") + " \n"
						+ "[gender]\t" + rs.getString("gender") + " \n"
						+ "[idCard]\t" + rs.getString("idCard");

			}
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {

				ConnectionUtil.closeConnection();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * @author Mladen
	 * @param Name
	 *            Name of user that we are searching
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @return returns String data about our user
	 */
	public static String findUserByName(String Name)
			throws ClassNotFoundException, SQLException {
		String result = "";
		ConnectionUtil con = new ConnectionUtil();
		PreparedStatement preparedStatement = null;

		String sql = "SELECT * FROM users WHERE `Name` = '" + Name + "' ";

		try {

			Connection connection = con.connect();

			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {

					result += "[userName]\t" + rs.getString("userName") + " "
							+ "[password]\t" + rs.getString("password") + " "
							+ "[name]\t" + rs.getString("Name") + " "
							+ "[lastName]\t" + rs.getString("lastName") + " "
							+ "[isAdmin]\t" + rs.getBoolean("isAdmin") + " "
							+ "[gender]\t" + rs.getString("gender") + " "
							+ "[idCard]\t" + rs.getString("idCard") + "\n";

				}
			} else {
				result += "No data found!";
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {

				ConnectionUtil.closeConnection();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	/**
	 *
	 * @author Mladen
	 *
	 *         Metoda koja prima unos licne karte korisnika i vrsi pretragu u
	 *         bazi za tog korisnika
	 *
	 * @param string
	 *            ID card of user we are searching
	 * @return returns String data about our user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String findUserByIDCard(String string)
			throws ClassNotFoundException, SQLException {

		String result = "";

		ConnectionUtil con = new ConnectionUtil();

		PreparedStatement preparedStatement = null;

		String sql = "SELECT * FROM users WHERE `idCard` = '" + string + "' ";

		try {

			Connection connection = con.connect();

			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				rs.beforeFirst();
				while (rs.next()) {
					result += "[userName]\t" + rs.getString("userName") + " "
							+ "[password]\t" + rs.getString("password") + " "
							+ "[name]\t" + rs.getString("Name") + " "
							+ "[lastName]\t" + rs.getString("lastName") + " "
							+ "[isAdmin]\t" + rs.getBoolean("isAdmin") + " "
							+ "[gender]\t" + rs.getString("gender") + " "
							+ "[idCard]\t" + rs.getString("idCard") + "\n";
				}
			} else {
				result += "No data found!";
			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {

				ConnectionUtil.closeConnection();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	/**
	 * @author Bojan Aleksic
	 */
	public static void logOutAllUsers() {

		/** Instantiate Users object */
		Users currentUser = new Users();
		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement insertQuery = null;
		try {
			Connection mysqlConnect = connection.connect();
			/**
			 * Update `users` table, set column `isOnline` to 'false' for all
			 * logged in users except for the current user
			 */
			insertQuery = mysqlConnect
					.prepareStatement(" UPDATE `users` SET `isOnline` = 'false' WHERE `userName` != '"
							+ currentUser.getUserName() + "' ");
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
	 * @author Bojan Aleksic
	 * @return returns string value of all online users.
	 */
	public static String getOnlineUsers() {

		/** Declare empty string to add online users to it */
		String onlineUsers = "";

		/**
		 * Invoke ConnectionUtil Class to establish MySQL connection with the DB
		 */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement prepStat = null; // declare PreparedStatement
											// interface
		try {
			Connection mysqlConnect = connection.connect();
			/** Select all from `users` */
			prepStat = mysqlConnect
					.prepareStatement(" SELECT * FROM `users` WHERE `isOnline` = 'true' ");
			ResultSet rs = prepStat.executeQuery(); // execute function
			while (rs.next()) {
				/** Add online users to the string */
				onlineUsers += "[" + rs.getString("userName") + "]\n";
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
		return onlineUsers; // return online users String
	}

	/**
	 * @author Dijana Markovic
	 * @param from
	 *            lover limit of menu
	 * @param to
	 *            top limit of meu
	 * @param inputScan
	 *            scenner we are using
	 * 
	 * @return returns selected int
	 */
	public static int inputInt(int from, int to, Scanner inputScan) {

		int result = 0;
		boolean isOk = true;
		Scanner input = inputScan;
		do {
			try {
				System.out.println(" Select in range between " + from + " - "
						+ to);
				result = input.nextInt();

				if (result >= from && result <= to) {
					isOk = false;
				} else {
					System.out.println("Out  of range! Try again");
				}

			} catch (InputMismatchException e) {

				System.out.println("You must enter integer type value between "
						+ from + " - " + to);
				input.nextLine();
			}

		} while (isOk);
		return result;
	}

	/**
	 * 
	 * Author Mladen Todorovic
	 * 
	 * @Method Metoda upisuje u tabeli user userCheckdOutDate na danasnji datum.
	 * 
	 * */
	public static void userChecksOut(String userName) {

		/** Pozivanje ConnectionUtil klase za konekciju sa DB */
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement unos = null;
		/**
		 * Pozivanje klase Date, i pozivanje metode za dobijanje danasnjeg
		 * datuma
		 */
		SimpleDateFormat datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long milis = System.currentTimeMillis();
		java.util.Date now = new Date(milis);
		String date = datum.format(now);

		try {
			Connection mysqlConnect = connection.connect();
			/**
			 * Update `users` tabele, upis danasnjeg datuma na dateOfCheckingIn
			 * za trenutnog korisnika
			 */
			unos = mysqlConnect
					.prepareStatement(" UPDATE `users` SET `userCheckdOutDate` = ? WHERE `userName` = '"
							+ userName + "'; ");
			unos.setString(1, date);
			unos.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Zatvaranje konekcije sa DB */
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * ----------------------------- CheckOutUser(String userName)
	 * 
	 * @author Bojan Aleksic ----------------------------- Method goes through
	 *         the `reciept` table and retrieves all records for the current
	 *         user, sums (according to dates passed since user is Checked In)
	 *         and displays them, then sums `daily_order` records as well and
	 *         add it to the new bill and displays how much user owes the money
	 *         for the services. It then deletes all records in `daily_order`
	 *         table for that user, and sets 'checkdIn' in `users` table to
	 *         'false' and columns `dateOfCheckingIn` and `userCheckOutDate`
	 *         sets to NULL. It sets also column `isOccupied` to 'false' in the
	 *         `rooms` table for that user.
	 * 
	 * @param userName
	 *            User that is going to be checked out
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String CheckOutUser(String userName, boolean clearDB,
			Date userCheckdHimSelfOut) throws SQLException,
			ClassNotFoundException {
		String finalResult = "";
		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();

		Connection mysqlConnect = connection.connect();

		/** Create statements */
		Statement statement1 = mysqlConnect.createStatement();
		Statement statement2 = mysqlConnect.createStatement();
		Statement statement3 = mysqlConnect.createStatement();
		Statement statement4 = mysqlConnect.createStatement();

		/** Create ResultSets for reciept, users and daily_order tables */
		ResultSet reciept = statement1
				.executeQuery(" SELECT `dateFrom`, `dateTo`, `gym`, `restaurant`, `saun`, `pool`, `roomType` FROM `reciept` WHERE `userName` = '"
						+ userName + "' ");
		ResultSet users = statement2
				.executeQuery(" SELECT `dateOfCheckingIn` FROM `users` WHERE `userName` = '"
						+ userName + "' ");
		ResultSet dailyOrder = statement3
				.executeQuery(" SELECT `gym`, `restaurant`, `saun`, `pool`, `roomType` FROM `daily_order` WHERE `userName` = '"
						+ userName + "' ");
		ResultSet getRoomNumber = statement4
				.executeQuery(" SELECT `roomNumber` FROM `daily_order` WHERE `userName` = '"
						+ userName + "' ");

		/** Create PreparedStatements for tables */
		PreparedStatement deleteReciept = null;
		PreparedStatement deleteDailyOrder = null;
		PreparedStatement checkOutUser = null;
		PreparedStatement setIsOccupied = null;

		/** Declare bills to sum expences for `reciept` */
		double billGym = 0;
		double billRestaurant = 0;
		double billSaun = 0;
		double billPool = 0;
		double billRoom = 0;

		/** Declare variables for used services from `reciept` table */
		String gymUsed = "";
		String restaurantUsed = "";
		String saunUsed = "";
		String poolUsed = "";
		String roomUsed = "";

		/** Declare bills to sum expences for `daily_order` */
		double billGymDaily = 0;
		double billRestaurantDaily = 0;
		double billSaunDaily = 0;
		double billPoolDaily = 0;
		double billRoomDaily = 0;

		/** Declare variables for used services from `daily_order` table */
		String gymUsedDaily = "";
		String restaurantUsedDaily = "";
		String saunUsedDaily = "";
		String poolUsedDaily = "";
		String roomUsedDaily = "";

		String roomNumb = ""; // declare roomNumb

		/** Instantiate Date objects for date of checking in and today's date */
		java.util.Date dateOfCheckingIn = new java.util.Date();
		java.util.Date dateToday = null;

		// if admin apruved use users date of check out!

		if (userCheckdHimSelfOut == null) {
			dateToday = new java.util.Date();
		} else {
			dateToday = userCheckdHimSelfOut;
		}
		/** Instantiate Date objects for dates FROM - TO */
		java.util.Date dateReciept1 = new java.util.Date();
		java.util.Date dateReciept2 = new java.util.Date();

		try {
			/** Go through `daily_order` table to obtain number of the room */
			while (getRoomNumber.next()) {
				roomNumb = getRoomNumber.getString("roomNumber");
			}
			/** SET current user's room to false */
			// if method is used for checkOut
			if (clearDB) {
				setIsOccupied = mysqlConnect
						.prepareStatement(" UPDATE `rooms` SET `isOccupied` = 'false' WHERE `roomNumber` = '"
								+ roomNumb + "' ");
				setIsOccupied.executeUpdate();
			}
			/** Go through `users` table and obtain Checking In - date */
			while (users.next()) {
				dateOfCheckingIn = users.getDate("dateOfCheckingIn");
			}

			/** Get passed days from CheckIn date until today's date */
			long daysDaily = (dateToday.getTime() - dateOfCheckingIn.getTime())
					/ (1000 * 60 * 60 * 24);

			/** Go through `daily_order` table and obtain data */
			while (dailyOrder.next()) {
				String gym = dailyOrder.getString("gym");
				String restaurant = dailyOrder.getString("restaurant");
				String saun = dailyOrder.getString("saun");
				String pool = dailyOrder.getString("pool");
				String roomtype = dailyOrder.getString("roomType");

				/** calculate room price */
				if (roomtype.equals("one bed")) {
					if (daysDaily != 0) {
						billRoomDaily += 20 * daysDaily;
					}
				} else if (roomtype.equals("two beds")) {
					if (daysDaily != 0) {
						billRoomDaily += 40 * daysDaily;
					}
				} else if (roomtype.endsWith("apartment")) {
					if (daysDaily != 0) {
						billRoomDaily += 60 * daysDaily;
					}
				}

				/**
				 * If columns in DB are set to 'true' sum values counting prices
				 * for gym as 10, for restaurant as 20, etc. and multiply them
				 * with passed days from obtained dates if needed
				 */
				if (gym.equals("true")) {
					if (daysDaily != 0) { // Avoid multiplication with 0
						billGymDaily += 10 * daysDaily;
					} else {
						billGymDaily += 10;
					}
					gymUsedDaily = "Gym" + "(" + billGymDaily + "$)";
				}
				if (restaurant.equals("true")) {
					if (daysDaily != 0) { // Avoid multiplication with 0
						billRestaurantDaily += 20 * daysDaily;
					} else {
						billRestaurantDaily += 20;
					}
					restaurantUsedDaily = "Restaurant" + "("
							+ billRestaurantDaily + "$)";
				}
				if (saun.equals("true")) {
					if (daysDaily != 0) { // Avoid multiplication with 0
						billSaunDaily += 10 * daysDaily;
					} else {
						billSaunDaily += 10;
					}
					saunUsedDaily = "Saun" + "(" + billSaunDaily + "$)";
				}
				if (pool.equals("true")) {
					if (daysDaily != 0) { // Avoid multiplication with 0
						billPoolDaily += 10 * daysDaily;
					} else {
						billPoolDaily += 10;
					}
					poolUsedDaily = "Pool" + "(" + billPoolDaily + "$)";
				}

				roomUsedDaily = "Room" + "(" + billRoomDaily + "$)"
						+ " - room type: " + roomtype;

			}
			double billDaily = billGymDaily + billRestaurantDaily
					+ billSaunDaily + billPoolDaily + billRoomDaily;

			double billReciept = 0;
			/** Go through `reciept` table and obtain data */
			double billTotal = +billDaily;
			while (reciept.next()) {
				/**
				 * Retrieve data from the `reciept` table, and assign it to the
				 * variables
				 */
				String gym = reciept.getString("gym");
				String restaurant = reciept.getString("restaurant");
				String saun = reciept.getString("saun");
				String pool = reciept.getString("pool");
				String roomtype = reciept.getString("roomType");
				dateReciept1 = reciept.getDate("dateFrom");
				dateReciept2 = reciept.getDate("dateTo");

				/**
				 * Get passed days FROM the specified date TO the specified date
				 * in the `reciept` table
				 */
				long daysRecip = (dateReciept2.getTime() - dateReciept1
						.getTime()) / (1000 * 60 * 60 * 24);

				/** calculate room price */
				if (roomtype.equals("one bed")) {
					if (daysDaily != 0) {
						billRoom += 20 * daysRecip;
					}
				} else if (roomtype.equals("two beds")) {
					if (daysDaily != 0) {
						billRoom += 40 * daysRecip;
					}
				} else if (roomtype.endsWith("apartment")) {
					if (daysDaily != 0) {
						billRoom += 60 * daysRecip;
					}
				}

				/**
				 * If columns in DB are set to 'true' sum values counting prices
				 * for gym as 10, for restaurant as 20, etc. and multiply them
				 * with passed days from obtained dates if needed
				 */
				if (gym.equals("true")) {
					if (daysRecip != 0) { // Avoid multiplication with 0
						billGym += 10 * daysRecip;
					} else {
						billGym += 10;
					}
					gymUsed = "Gym" + "(" + billGym + "$)";
				}
				if (restaurant.equals("true")) {
					if (daysRecip != 0) { // Avoid multiplication with 0
						billRestaurant += 20 * daysRecip;
					} else {
						billRestaurant += 20;
					}
					restaurantUsed = "Restaurant" + "(" + billRestaurant + "$)";
				}
				if (saun.equals("true")) {
					if (daysRecip != 0) { // Avoid multiplication with 0
						billSaun += 10 * daysRecip;
					} else {
						billSaun += 10;
					}
					saunUsed = "Saun" + "(" + billSaun + "$)";
				}
				if (pool.equals("true")) {
					if (daysRecip != 0) { // Avoid multiplication with 0
						billPool += 10 * daysRecip;
					} else {
						billPool += 10;
					}
					poolUsed = "Pool" + "(" + billPool + "$)";
				}

				roomUsed = "Room" + "(" + billRoom + "$)" + " - room type: "
						+ roomtype;

				/** Get sum of the bill from `reciept` talbe */
				billReciept = billGym + billRestaurant + billSaun + billPool
						+ billRoom;

				finalResult += "-----------------------------------------------\n";
				finalResult += "Bill from " + dateReciept1 + " to "
						+ dateReciept2 + " is: " + billReciept + "$\n";
				finalResult += "-----------------------------------------------\n";
				finalResult += "You used following services: \n-" + gymUsed
						+ " \n-" + restaurantUsed + " \n-" + saunUsed + " \n-"
						+ poolUsed + "\n-" + roomUsed + "\n-";

				/*
				 * System.out
				 * .println("-----------------------------------------------");
				 * System.out.println("Bill from " + dateReciept1 + " to " +
				 * dateReciept2 + " is: " + billReciept + "$"); System.out
				 * .println("-----------------------------------------------");
				 * System.out.println("You used following services: \n-" +
				 * gymUsed + " \n-" + restaurantUsed + " \n-" + saunUsed +
				 * " \n-" + poolUsed + "\n-" + roomUsed + "\n-");
				 */
				billTotal += billReciept;
			}

			/** Get sum of the bill from `daily_order` table */

			/** Display results: */

			finalResult += "-----------------------------------------------\n";
			finalResult += "Daily bill: " + billDaily + "$\n";
			finalResult += "-----------------------------------------------\n";
			finalResult += "You used following daily services (since: "
					+ dateOfCheckingIn + " to " + dateToday + "): \n-"
					+ gymUsedDaily + "\n-" + restaurantUsedDaily + "\n-"
					+ saunUsedDaily + "\n-" + poolUsedDaily + "\n-"
					+ roomUsedDaily + "\n-";
			finalResult += "-----------------------------------------------\n";
			finalResult += "Bill for all services is: " + billTotal + "$\n";
			finalResult += "-----------------------------------------------\n";

			/*
			 * System.out
			 * .println("-----------------------------------------------");
			 * System.out.println("Daily bill: " + billDaily + "$"); System.out
			 * .println("-----------------------------------------------");
			 * System.out.println("You used following daily services (since: " +
			 * dateOfCheckingIn + " to " + dateToday + "): \n-" + gymUsedDaily +
			 * "\n-" + restaurantUsedDaily + "\n-" + saunUsedDaily + "\n-" +
			 * poolUsedDaily + "\n-" + roomUsedDaily + "\n-");
			 * 
			 * System.out.println("--------------------------------");
			 * System.out.println("Bill for all services is: " + billTotal +
			 * "$"); System.out.println("---------------------------------");
			 */

			if (clearDB) {
				/** Delete records from `reciept` - table, for the current user */
				deleteReciept = mysqlConnect
						.prepareStatement(" DELETE FROM `reciept` WHERE `userName` = '"
								+ userName + "' ");
				deleteReciept.executeUpdate();

				/**
				 * Delete records from `daily_order` - table, for the current
				 * user
				 */
				deleteDailyOrder = mysqlConnect
						.prepareStatement(" DELETE FROM `daily_order` WHERE `userName` = '"
								+ userName + "' ");
				deleteDailyOrder.executeUpdate();

				/**
				 * Set is checked in to 'false', date of checking in to 'null'
				 * and date of checking out to 'null'
				 */
				checkOutUser = mysqlConnect
						.prepareStatement(" UPDATE `users` SET `isCheckdIn` = 'false', `dateOfCheckingIn` = NULL, `userCheckdOutDate` = NULL WHERE `userName` = '"
								+ userName + "' ");
				checkOutUser.executeUpdate();
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
		return finalResult;
	}

	/**
	 * @author Ognjen Lazic
	 * @param UserName
	 *            User name of user we are checking in db
	 * @return returns true if user is checkd in false if not
	 */
	public static boolean isCheckdIn(String userName) {
		boolean result = false;
		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement prepStat = null; // declare PreparedStatement
											// interface
		try {
			Connection mysqlConnect = connection.connect();
			/** Select "isAdmin" column from the table "users" */
			prepStat = mysqlConnect

			.prepareStatement(" SELECT isCheckdIn FROM users WHERE userName='"
					+ userName + "';");
			ResultSet rs = prepStat.executeQuery(); // execute function
			rs.next();
			if (rs.getString("isCheckdIn").equals("true")) {
				result = true; // if it's admin, return true
			} else {
				result = false;
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
		return result;

	}

	/**
	 * @author Bojan Aleksic
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public static Date getCheckedIn(String userName) throws SQLException {

		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();

		Connection mysqlConnect = connection.connect();

		/** Create statements */
		Statement statement = mysqlConnect.createStatement();

		/** Select dateOfCheckingIn for current user */
		ResultSet rs = statement
				.executeQuery(" SELECT `dateOfCheckingIn` FROM `users` WHERE `userName` = '"
						+ userName + "' ");

		java.util.Date getCheckInDate = new java.util.Date();

		try {
			while (rs.next()) {
				/** Obtain date of checking in from the `users` table */
				getCheckInDate = rs.getDate("dateOfCheckingIn");
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
		return getCheckInDate; // Return date of checking in
	}

}
