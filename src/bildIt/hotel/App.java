package bildIt.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bildIt.hotel.dao.impl.ConnectionUtil;

class Runner implements Runnable {

	private String userName;

	public Runner(String userName) {
		this.userName = userName;
	}

	// basicly this thread is going into the database every 5 seconds to check
	// if current user is online, if some one else logs out this user this
	// thread will find that out and log out user as soon as possible
	@Override
	public void run() {

		try {
			while (true) {

				ConnectionUtil con = new ConnectionUtil();
				PreparedStatement preparedStatment = null;
				String sql = "SELECT isOnline FROM users WHERE userName='"
						+ userName + "';";
				try {

					Connection connection = con.connect();
					preparedStatment = connection.prepareStatement(sql);
					ResultSet rs = preparedStatment.executeQuery();

					if (rs.next()) {

						System.out.println(rs.getString("isOnline"));
						if (rs.getString("isOnline").equals("false")) {
							return;
						}

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

				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// return;

	}

}

public class App {

	// new User sessionUser=new User();
	public static String test = "t";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
		String userName = "";
		String password = "";
		Users userSession = new Users();
		Scanner input = new Scanner(System.in);

		while (true) {

			System.out.println("working");

			System.out.println("--------------------------------------------");
			System.out.println("Welcome to the Hotel Management Application");
			System.out.println("--------------------------------------------");
			System.out.println(">Please login to proceed.\n");

			System.out.println("User name:");
			userName = input.next();
			System.out.println("Password:");
			password = input.next();
			userSession = Users.logOn(userName, password);

			if (Users.isAdmin() == true) {
				// this part is a thread that is checking if some one loged out
				// or user using other thread!
				Thread isUserOnlineThread = new Thread(new Runner(userName));
				isUserOnlineThread.start();

				while (userSession.getUserName() != null) {
					while (isUserOnlineThread.isAlive()) {
						System.out.println("Admin is logged in");
						// System.out.println(userSession.getUserName() + " "
						// + userSession.getPassword());

						System.out.println("Press 1 to log out");
						int off = input.nextInt();
						if (off == 1) {
							Users.logOut(userSession);
							// userSession = null;
							isUserOnlineThread.stop();
							System.out.println("You are logged out.");
						}
					}
					System.out.println("Some one kicked you out!");
					Users.logOut(userSession);
				}
			} else {

				// this part is a thread that is checking if some one loged out
				// or user using other thread!
				Thread isUserOnlineThread = new Thread(new Runner(userName));
				isUserOnlineThread.start();

				while (userSession.getUserName() != null) {
					while (isUserOnlineThread.isAlive()) {
						System.out.println("User is logged in");
						System.out.println(userSession.getUserName() + " "
								+ userSession.getPassword());

						System.out.println("Press 1 to log out");
						int off = input.nextInt();
						if (off == 1) {
							Users.logOut(userSession);
							// userSession = null;
							isUserOnlineThread.stop();
							System.out.println("You are logged out.");
						}
					}
					System.out.println("Some one kicked you out!");
					Users.logOut(userSession);
				}
			}

			// input.close();
		}
	}

	/**
	 * @return string that user has inputed
	 */
	public static String getStringInput() {

		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		sc.close();
		return input;

	}

}
