package bildIt.hotel;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import sun.util.logging.resources.logging;
import bildIt.hotel.dao.impl.UsersDao;

//class Runner implements Runnable {
//
//	private String userName;
//
//	public Runner(String userName) {
//		this.userName = userName;
//	}
//
//	// basicly this thread is going into the database every 5 seconds to check
//	// if current user is online, if some one else logs out this user this
//	// thread will find that out and log out user as soon as possible
//	@Override
//	public void run() {
//
//		try {
//			while (true) {
//
//				ConnectionUtil con = new ConnectionUtil();
//				PreparedStatement preparedStatment = null;
//				String sql = "SELECT isOnline FROM users WHERE userName='"
//						+ userName + "';";
//				try {
//
//					Connection connection = con.connect();
//					preparedStatment = connection.prepareStatement(sql);
//					ResultSet rs = preparedStatment.executeQuery();
//
//					if (rs.next()) {
//
//						System.out.println(rs.getString("isOnline"));
//						// if user is not online stop this thread!
//						if (rs.getString("isOnline").equals("false")) {
//							return;
//						}
//
//					}
//
//				} catch (Exception e) {
//
//					e.printStackTrace();
//				} finally {
//					try {
//						// ------------------------------- IMPORTANT!!!
//						// CLOSE CONNECTION!!
//						// ------------------------------- IMPORTANT!!!
//						ConnectionUtil.closeConnection();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//
//				}
//
//				Thread.sleep(1000);
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		// return;
//
//	}	
//
//}

public class App {

	// new User sessionUser=new User();
	public static String test = "t";
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws InterruptedException,
			ClassNotFoundException, SQLException {

		String userName = "";
		String password = "";
		Users userSession = new Users();

		// program has to runn all the time....
		while (true) {

			System.out.println("--------------------------------------------");
			System.out.println("Welcome to the Hotel Management Application");
			System.out.println("--------------------------------------------");
			System.out.println(">Please login to proceed.\n");

			// get users user name and password
			System.out.println("User name:");
			userName = input.next();
			System.out.println("Password:");
			password = input.next();
			userSession = UsersDao.logOn(userName, password);

			// check if login was success
			if (userSession != null) {

				// check if user is admin!
				if (UsersDao.isAdmin(userSession) == true) {

					// be in main menu for this user while he is logd in
					while (userSession.getUserName() != null) {
						// every move of admin check if he is still logd in
						// other admin can log out any one
						while (UsersDao.isActive(userSession.getUserName())) {

							System.out.println("Admin: ["
									+ userSession.getUserName()
									+ "] is logged in.");

							System.out
									.println("--------------------------------------------");
							System.out.println("Admin main menu:");
							System.out
									.println("--------------------------------------------");
							System.out.println("[1] Register new user");
							System.out.println("-[2] Register user in room");
							System.out.println("-[3] Unregister user in room");
							System.out.println("[4] Edit user");
							System.out.println("-[5] Print receipt ");
							System.out
									.println("[6] Check who is online (log some one out, log every one out)");
							System.out
									.println("[7] Find user info by: Name,ID or UserName");
							System.out
									.println("-[8] Check for pending registration changes");
							System.out.println("[9] Logout");

							// make method that asks user to chouse from 1 to 9
							int userInput = input.nextInt();

							// register user
							if (userInput == 1) {
								boolean run = true;
								do {

									System.out
											.println("--------------------------------------------");
									System.out.println("Register user:");
									System.out
											.println("--------------------------------------------");
									// ask for user name!
									System.out.println("Insert user name:");
									// ignore a line
									input.nextLine();
									String tempUserName = input.nextLine();
									// check if user name is taken
									if (UsersDao
											.isAlreadyRegistered(tempUserName)) {
										System.out
												.println("That user name is taken!");
									} else {

										// get user data
										System.out.println("password for: "
												+ tempUserName + " user.");
										String tempPassword = input.nextLine();

										System.out.println("first name for: "
												+ tempUserName + " user.");
										String tempFirstName = input.nextLine();

										System.out.println("last name for: "
												+ tempUserName + " user.");
										String tempLastName = input.nextLine();

										System.out.println("gender name for: "
												+ tempUserName + " user.");
										String tempGender = input.nextLine();

										System.out.println("idCard name for: "
												+ tempUserName + " user.");
										String tempIdCard = input.nextLine();

										// set admin or basic user
										System.out
												.println("Give admin rights to: "
														+ tempUserName
														+ " user.(yes or no)");
										String tempIsAdmin = input.nextLine();
										if ("yes".equals(tempIsAdmin))
											tempIsAdmin = "true";
										else
											tempIsAdmin = "false";

										UsersDao.addUserToDatabase(
												tempUserName, tempPassword,
												tempFirstName, tempLastName,
												tempIsAdmin, tempGender,
												tempIdCard, "false");
									}

									System.out
											.println("Try again or register new: type [1] for YES or [2] for NO");

									if (input.nextInt() == 2)
										run = false;

								} while (run);
							} else if (userInput == 2) {

								System.out.println("reg user to room");
							} else if (userInput == 3) {
								System.out.println("unreg user to room");
							} else if (userInput == 4) {

								boolean run = true;
								do {
									System.out
											.println("--------------------------------------------");
									System.out
											.println("What user do you wont to edit:");
									System.out.println(UsersDao
											.getUsersFromDB());
									System.out
											.println("--------------------------------------------");

									System.out.println("User name: ");
									// clear scener
									input.nextLine();
									String tempUserName = input.nextLine();
									if (!UsersDao
											.isAlreadyRegistered(tempUserName)) {
										System.out
												.println("User name invalid!");
									} else {

										System.out
												.println(UsersDao
														.findUserByUserName(tempUserName));
										System.out
												.println("---------------------------------------------------------");
										// ask user how many things he wonts to
										// edit
										System.out
												.println("How many fields do you wont to edit?");
										int fNumber = input.nextInt();
										String field = "";
										String fieldValue = "";
										// clear the scener...
										input.nextLine();
										for (int i = 0; i < fNumber; i++) {
											// ask user for every field what
											// value to enter
											do {
												System.out
														.println("What field do you wont to edit:");
												field = input.nextLine();
												// cant select field that does
												// not exist

											} while (!((field
													.equals("userName")
													|| field.equals("password")
													|| field.equals("name")
													|| field.equals("lastName")
													|| field.equals("isAdmin")
													|| field.equals("gender") || field
														.equals("idCard"))));

											System.out
													.println("New value for field "
															+ field + ".");
											fieldValue = input.nextLine();

											// check if that userName is taken
											if (field.equals("userName")
													&& UsersDao
															.isAlreadyRegistered(fieldValue)) {
												System.out
														.println("Did not update userName because that userName is taken!");

											} else {

												UsersDao.updateUserInDatabase(
														tempUserName, field,
														fieldValue);
												System.out.println("Field "
														+ field
														+ " updated to value: "
														+ fieldValue);
											}
										}
									}

									System.out
											.println("Try again or edit more: type [1] for YES or [2] for NO");

									if (input.nextInt() == 2)
										run = false;

								} while (run);

							} else if (userInput == 5) {
								System.out.println("print recipt");
							} else if (userInput == 6) {
								boolean run = true;

								do {
									System.out
											.println("--------------------------------------------");
									System.out.println("Online users: ");
									System.out.println(UsersDao
											.getOnlineUsers());
									System.out
											.println("--------------------------------------------");
									System.out
											.println("[1] for log everyone out! [2] for log some one out!");
									int userInputForOnline = input.nextInt();
									if (userInputForOnline == 1) {
										UsersDao.logOutAllUsers();
										System.out
												.println("Every one is now loged out!");
									} else {
										String logOutSomeOne = "";

										System.out
												.println("Who do you wont to log out:");
										// clean scaner again
										input.nextLine();
										logOutSomeOne = input.nextLine();
										if (UsersDao
												.isAlreadyRegistered(logOutSomeOne)) {
											Users logMeOut = new Users(
													logOutSomeOne);
											UsersDao.logOut(logMeOut);
											System.out.println(logOutSomeOne
													+ " is loged out!");
										} else {
											System.out.println("Invalid user!");
										}

									}

									System.out
											.println("Logout someone again?: type [1] for YES or [2] for NO");
									if (input.nextInt() == 2)
										run = false;

								} while (run);

							} else if (userInput == 7) {

								boolean run = true;
								do {
									System.out
											.println("How do you wona search for your user: [1] for by userName [2] for by Name [3] for by ID");
									int userInputForSearch = input.nextInt();
									if (userInputForSearch == 1) {
										// clera scener
										input.nextLine();
										System.out
												.println("What is that user userName?");
										String tempSearch = input.nextLine();
										if (UsersDao
												.findUserByUserName(tempSearch) != null) {
											System.out
													.println(UsersDao
															.findUserByUserName(tempSearch));

										} else {
											System.out
													.println("No data found!");
										}

									} else if (userInputForSearch == 2) {

										// clera scener
										input.nextLine();
										System.out
												.println("What is that user Name?");
										System.out.println(UsersDao
												.findUserByName(input
														.nextLine()));

									} else if (userInputForSearch == 3) {
										// clera scener
										input.nextLine();
										System.out
												.println("What is that user ID number?");
										System.out.println(UsersDao
												.findUserByIDCard(input
														.nextLine()));

									}

									System.out
											.println("Logout someone again?: type [1] for YES or [2] for NO");
									if (input.nextInt() == 2)
										run = false;

								} while (run);

							} else if (userInput == 8) {
								System.out.println("check for pend changes");
							} else if (userInput == 9) {
								UsersDao.logOut(userSession);
								break;
							}
						}
						System.out.println("You are logged out.");
						UsersDao.logOut(userSession);
					}
				} else {

					// stay in main menu for user until he logs out or some one
					// logs him out
					while (userSession.getUserName() != null) {
						// every move of user in main menu check if he is still
						// logd in if some one logs him out get out of here
						while (UsersDao.isActive(userSession.getUserName())) {
							System.out.println("User: ["
									+ userSession.getUserName()
									+ "] is logged in.");
							// MAIN MENU USER

							System.out.println("Press 1 to log out");
							int off = input.nextInt();
							if (off == 1) {
								UsersDao.logOut(userSession);
								System.out.println("You are logged out.");
								break;
							}
						}
						System.out.println("Admin loged you out!");

						UsersDao.logOut(userSession);

					}
				}

			}
			// System.out.println("Login failed!");
			// input.close();
		}
	}
}
