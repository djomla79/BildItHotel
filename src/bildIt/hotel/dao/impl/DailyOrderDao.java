package bildIt.hotel.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyOrderDao {

	/**
	 * @author Bojan Aleksic
	 * 
	 * @param userName
	 *            User name of user that is checkd in.
	 * @param roomNumber
	 *            Room number of user that is checkd in.
	 * @param roomType
	 *            Room type of user that is checkd in.
	 * @param gym
	 *            is user going to use.
	 * @param restaurant
	 *            is user going to use.
	 * @param saun
	 *            is user going to use.
	 * @param pool
	 *            is user going to use.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 *             This method adds daily order for user based on services he is
	 *             going to use while checkd in.
	 * 
	 */
	public static void addDailyOrderForUser(String userName, String roomNumber,
			String roomType, String gym, String restaurant, String saun,
			String pool) throws ClassNotFoundException, SQLException {

		/** Connect driver */
		Class.forName("com.mysql.jdbc.Driver");
		/**
		 * Establish connection with the Database
		 * 
		 * NOTE for @lazic: I couldn't use ConnectionUtil Class because I had to
		 * work with multiple SQL statements which requires implementing
		 * "allowMultiQueries=true" to the localhost URL
		 */
		Connection mysqlConnect = DriverManager.getConnection(
				"jdbc:mysql://localhost/hotel?allowMultiQueries=true",
				ConnectionUtil.getUser(), ConnectionUtil.getPass());

		/** Invoke ConnectionUtil Class to establish connection with the DB */
		PreparedStatement insertQuery = null;
		try {
			/**
			 * Insert all parameters to the `daily_order` and Update table
			 * `rooms` and set same room number to occupied (true)
			 */
			insertQuery = mysqlConnect
					.prepareStatement(" INSERT INTO `daily_order`(`userName`, `roomNumber`, `roomType`, `gym`, `restaurant`, `saun`, `pool`) VALUES(?, ?, ?, ?, ?, ?, ?); "
							+ "UPDATE `rooms` SET `isOccupied` = 'true' WHERE `roomNumber` = '"
							+ roomNumber + "' ");
			insertQuery.setString(1, userName);
			insertQuery.setString(2, roomNumber);
			insertQuery.setString(3, roomType);
			insertQuery.setString(4, gym);
			insertQuery.setString(5, restaurant);
			insertQuery.setString(6, saun);
			insertQuery.setString(7, pool);
			insertQuery.executeUpdate(); // execute update
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the database */
				mysqlConnect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Author Mladen Todorovic
	 * @param userName
	 *            User name of user that is going to be checkd in the db.
	 */
	public static void CheckInUser(String userName) {

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
			 * Update `users` tabele, postavljanje isCheckdIn na true, i
			 * danasnji datum na dateOfCheckingIn za unesenog korisnika
			 */
			unos = mysqlConnect
					.prepareStatement(" UPDATE users SET isCheckdIn = 'true' ,dateOfCheckingIn = '"
							+ date + "' WHERE userName = '" + userName + "';");
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
	 * @author Mladen Todorovic
	 * @param typeOfRoom
	 *            what type of room do you seek to find free
	 * @return returns list of room numbers that are free!
	 */
	public static List<Integer> listFreeRooms(String typeOfRoom) {

		List<Integer> result = null;

		/**
		 * Invoke ConnectionUtil Class to establish MySQL connection with the DB
		 */
		ConnectionUtil connection = new ConnectionUtil();

		PreparedStatement ps = null;

		try {
			result = new ArrayList<>();
			Connection mysqlConnect = connection.connect();
			/** Select `rooms` table where 'roomType' equals inputed typeOfRoom */
			ps = mysqlConnect
					.prepareStatement(" SELECT * FROM rooms WHERE roomType = '"
							+ typeOfRoom + "';");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				/** If row isOccupied equals false */
				if (rs.getString("isOccupied").equals("false")) {
					/** Add roomNumber to the list */
					result.add(rs.getInt("roomNumber"));
				}
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the DB */
				ConnectionUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @author Ognjen Lazic
	 * @param userName
	 *            if true returns only data splited by - User name of user that
	 *            is searched for daily order in db
	 * @param rawData
	 * @return returns string of users daily order
	 */

	public static String getDailyOrder(String userName, boolean rawData) {
		String result = "";

		ConnectionUtil connection = new ConnectionUtil();
		PreparedStatement prepStat = null; // declare PreparedStatement
											// interface
		try {
			Connection mysqlConnect = connection.connect();
			/** Select "isAdmin" column from the table "users" */
			prepStat = mysqlConnect

			.prepareStatement(" SELECT * FROM daily_order WHERE userName='"
					+ userName + "';");
			ResultSet rs = prepStat.executeQuery(); // execute function
			rs.next();

			if (rawData) {
				result = rs.getString("roomNumber") + "-"
						+ rs.getString("roomType") + "-" + rs.getString("gym")
						+ "-" + rs.getString("restaurant") + "-"
						+ rs.getString("saun") + "-" + rs.getString("pool");
			} else {
				result = "Room type:\t\t[" + rs.getString("roomType") + "]\n"
						+ "Using gym:\t\t[" + rs.getString("gym") + "]\n"
						+ "Using restaurant:\t[" + rs.getString("restaurant")
						+ "]\n" + "Using Saun:\t\t[" + rs.getString("saun")
						+ "]\n" + "Using pool:\t\t[" + rs.getString("pool")
						+ "]\n";
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
	 * @author Mladen Todorovic
	 * 
	 * @param userName
	 *            User that is changing the order
	 * @param roomNumber
	 *            new room number
	 * @param roomType
	 *            new room type
	 * @param gym
	 *            is user using gym
	 * @param restaurant
	 *            is user using gym
	 * @param saun
	 *            is user using gym
	 * @param pool
	 *            is user using gym
	 * @param makeReciept
	 *            true to make recipt in db false no recipt needed (user ceckd
	 *            in today no need for recipt) it true will make recipt if false
	 *            will not make recipt
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 * */
	public static void changeDailyOrder(String userName, String roomNumber,
			String roomType, String gym, String restaurant, String saun,
			String pool, boolean makeReciept) throws SQLException,
			ClassNotFoundException {

		/** Invoke ConnectionUtil Class to establish connection with the DB */
		ConnectionUtil connection = new ConnectionUtil();

		Connection mysqlConnect = connection.connect();
		PreparedStatement updateToReciept = null;
		PreparedStatement updateToDailyOrder = null;

		/** Create statement */
		Statement statement1 = mysqlConnect.createStatement();
		Statement statement2 = mysqlConnect.createStatement();

		/** Create ResultSet users table and daily_order table */
		ResultSet users = statement1
				.executeQuery(" SELECT `dateOfCheckingIn` FROM `users` WHERE `userName` = '"
						+ userName + "' ");
		ResultSet dOr = statement2
				.executeQuery(" SELECT `roomNumber`, `roomType`, `gym`, `restaurant`, `saun`, `pool` FROM `daily_order` WHERE `userName` = '"
						+ userName + "' ");

		/** Instantiate Date object, for date (today) in wanted format */
		// java.util.Date dateOfCheckingIn = new java.util.Date();
		// java.util.Date dateToday = new java.util.Date(); // @Ogi
		SimpleDateFormat datum = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// moze se u i ovom formatu, izaberi sta god je po tebi bolje :)
		long milis = System.currentTimeMillis();
		java.util.Date today = new Date(milis);
		String dateToday = datum.format(today);
		String dateOfCheckingIn = null;

		String gym1 = null, restaurant1 = null, saun1 = null, pool1 = null, roomNumber1 = null, roomType1 = null;

		try {
			/** Obtaining checking in date from DB for this user */
			while (users.next()) {
				dateOfCheckingIn = datum.format(users
						.getDate("dateOfCheckingIn"));
				// dateOfCheckingIn = users.getDate("dateOfCheckingIn");
			}
			/** Obtain data from `daily_order` table */
			while (dOr.next()) {
				roomNumber1 = dOr.getString("roomNumber");
				roomType1 = dOr.getString("roomType");
				gym1 = dOr.getString("gym");
				restaurant1 = dOr.getString("restaurant");
				saun1 = dOr.getString("saun");
				pool1 = dOr.getString("pool");

			}

			/**
			 * Update all data from `daily_order` and checkd in date to
			 * dateFrom, dateToday to dateTo, to `reciept` table for this user
			 */
			if (makeReciept) {
				/**
				 * Update all data from `daily_order` and checkd in date to
				 * dateFrom, dateToday to dateTo, to `reciept` table for this
				 * user
				 */
				updateToReciept = mysqlConnect
						.prepareStatement(" INSERT INTO `reciept` (`roomNumber`, `roomType`, `dateFrom`, `dateTo`, `gym`, `restaurant`, `saun`, `pool`,`userName`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
				// updateToReciept = mysqlConnect
				// .prepareStatement(" INSERT INTO `reciept` SET `roomNumber` = ?, `roomType` = ?, `dateFrom` = ?, `dateTo` = ?, `gym` = ?, `restaurant` = ?, `saun` = ?, `pool` = ? WHERE `userName` = ?");
				updateToReciept.setString(1, roomNumber1);
				updateToReciept.setString(2, roomType1);
				updateToReciept.setString(3, dateOfCheckingIn);
				updateToReciept.setString(4, dateToday);
				// updateToReciept.setDate(3, (Date) dateOfCheckingIn);
				// updateToReciept.setDate(4, (Date) dateToday);
				updateToReciept.setString(5, gym1);
				updateToReciept.setString(6, restaurant1);
				updateToReciept.setString(7, saun1);
				updateToReciept.setString(8, pool1);
				updateToReciept.setString(9, userName);
				updateToReciept.executeUpdate();

				PreparedStatement freeRoom = mysqlConnect
						.prepareStatement(" UPDATE `users` SET `dateOfCheckingIn` = ? WHERE `userName` = '"
								+ userName + "' ");
				freeRoom.setString(1, dateToday);
				freeRoom.executeUpdate();

			}
			/** Update all inputed parameters to `daily_order` table */
			updateToDailyOrder = mysqlConnect
					.prepareStatement(" UPDATE `daily_order` SET `roomNumber` = ?, `roomType` = ?, `gym` = ?, `restaurant` = ?, `saun` = ?, `pool` = ? WHERE `userName` = '"
							+ userName + "' ");
			updateToDailyOrder.setString(1, roomNumber);
			updateToDailyOrder.setString(2, roomType);
			updateToDailyOrder.setString(3, gym);
			updateToDailyOrder.setString(4, restaurant);
			updateToDailyOrder.setString(5, saun);
			updateToDailyOrder.setString(6, pool);
			updateToDailyOrder.executeUpdate();

			// set roomNumber1 to free;
			PreparedStatement freeRoom = mysqlConnect
					.prepareStatement(" UPDATE `rooms` SET `isOccupied` = ? WHERE `roomNumber` = '"
							+ roomNumber1 + "' ");
			freeRoom.setString(1, "false");
			freeRoom.executeUpdate();

			// set roomNumber to occupied;
			PreparedStatement takeRoom = mysqlConnect
					.prepareStatement(" UPDATE `rooms` SET `isOccupied` = ? WHERE `roomNumber` = '"
							+ roomNumber + "' ");
			takeRoom.setString(1, "true");
			takeRoom.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/** Close connection with the DB */
				ConnectionUtil.closeConnection();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
