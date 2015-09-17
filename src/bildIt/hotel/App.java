package bildIt.hotel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import bildIt.hotel.dao.impl.DailyOrderDao;
import bildIt.hotel.dao.impl.UsersDao;

/**
 * @author Ognjen Lazic
 *
 */
public class App {

	// scenner
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws InterruptedException,
			ClassNotFoundException, SQLException {

		// inputing values and our sesion user
		String userName = "";
		String password = "";
		Users userSession = new Users();

		// program has to run all the time....
		// infinite loop... just becouse this is console progarm!
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

			// try loging in with data user provided
			userSession = UsersDao.logOn(userName, password);

			// check if login was success
			if (userSession != null) {

				// check if user is admin!
				if (UsersDao.isAdmin(userSession) == true) {

					// be in main menu for this user while he is logged in
					while (userSession.getUserName() != null) {
						// every time user is doing a command check if we are
						// still logged in the system (we can get logged out by
						// other admin)
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
							System.out.println("[2] Check user in");
							System.out.println("[3] Check user out");
							System.out.println("[4] Edit user");
							System.out.println("[5] Online options");
							System.out.println("[6] Find user");
							System.out.println("[7] Logout");

							// user our inputInt method to check what is user
							// going to do
							int userInput = UsersDao.inputInt(1, 7, input);

							// register user
							if (userInput == 1) {
								// do this until its done right or admin gives
								// up! (do not let bad data get in db!)
								boolean run = true;
								do {

									System.out
											.println("--------------------------------------------");
									System.out.println("Register user: ");
									System.out
											.println("--------------------------------------------");
									System.out
											.println("[1] for continue [2] for Cancel");
									if (UsersDao.inputInt(1, 2, input) == 2) {
										break;
									}

									// set a valid USER NAME
									String tempUserName = "";
									do {
										// ask for user name!
										System.out.println("Insert user name:");
										// ignore a line scanner is madness
										input.nextLine();
										tempUserName = input.nextLine();
										if (tempUserName.length() < 4
												|| tempUserName.length() > 20)
											System.out
													.println("User name has to be biger than 4 letters and smaller than 20 ");
										else
											break;
										// check if user name is taken
									} while (true);
									if (UsersDao
											.isAlreadyRegistered(tempUserName)) {
										System.out
												.println("That user name is taken!");
									} else {
										// boolean for other restrictions
										boolean validInput = true;

										// get user data
										// set password
										String tempPassword = "";
										do {
											System.out.println("password for: "
													+ tempUserName + " user.");
											tempPassword = input.nextLine();

											// password only has to be more than
											// 8 letters long and less than 20
											// this all can be changed
											if (tempPassword.length() < 8) {
												System.out
														.println("Password is too short!");
											} else if (tempPassword.length() > 20) {
												System.out
														.println("Password is too long!");
											} else
												break;

										} while (validInput);

										// set a valid FIRST NAME
										String tempFirstName = "";
										do {
											System.out
													.println("first name for: "
															+ tempUserName
															+ " user.");
											tempFirstName = input.nextLine();

											if (tempFirstName.length() < 1) {
												System.out
														.println("Name must have more than 1 letter!");
											} else if (tempFirstName.length() > 20) {
												System.out
														.println("Name is too long long!");
											} else {
												// set whole name to lower case
												tempFirstName.toLowerCase();

												// set first letter of name to
												// upper case
												tempFirstName = Character
														.toUpperCase(tempFirstName
																.charAt(0))
														+ tempFirstName
																.substring(1);

												break;
											}

										} while (validInput);

										// set a valid LAST NAME
										String tempLastName = "";
										do {
											System.out
													.println("last name for: "
															+ tempUserName
															+ " user.");
											tempLastName = input.nextLine();

											if (tempLastName.length() < 1) {
												System.out
														.println("Last name must have more than 1 letter!");
											} else if (tempLastName.length() > 20) {
												System.out
														.println("Last name is too long!");
											} else {

												// set whole last name to lower
												// case
												tempLastName.toLowerCase();

												// set first letter of last name
												// to upper case
												tempLastName = Character
														.toUpperCase(tempLastName
																.charAt(0))
														+ tempLastName
																.substring(1);

												break;
											}

										} while (validInput);

										// set a valid GENDER
										System.out
												.println("gender name for: "
														+ tempUserName
														+ " user [1] for male [2] for female.");
										String tempGender = "";

										// admin can only select 1 or 2 after he
										// selects its set male or female
										// according to his selection
										if (UsersDao.inputInt(1, 2, input) == 1) {
											tempGender = "male";
										} else {
											tempGender = "female";
										}

										// set a valid ID CARD
										input.nextLine();
										String tempIdCard = "";
										do {
											System.out
													.println("idCard name for: "
															+ tempUserName
															+ " user.");
											tempIdCard = input.nextLine();

											if (tempIdCard.length() < 8) {
												System.out
														.println("Id has to be more than 8 letters!");
											} else if (tempIdCard.length() > 20) {
												System.out
														.println("Id has to be less than 20 letters!");
											} else
												break;

										} while (validInput);
										// set a valid IS ADMIN
										String tempIsAdmin = "";
										System.out
												.println("Give admin rights to: "
														+ tempUserName
														+ " user [1] for yes [2] for no!");

										// admin can only select 1 or 2 if its
										// one set admin to true if not set it
										// to false
										if (UsersDao.inputInt(1, 2, input) == 1)
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
											.println("--------------------------------------------");
									System.out
											.println("Register more users?: type [1] for YES or [2] for NO");
									System.out
											.println("--------------------------------------------");

									if (UsersDao.inputInt(1, 2, input) == 2)
										run = false;

								} while (run);

								// Check some user in
							} else if (userInput == 2) {

								// if there are free rooms do stuff if not....
								// no check in today
								if (!DailyOrderDao.listFreeRooms("apartment")
										.isEmpty()
										&& !DailyOrderDao.listFreeRooms(
												"one bed").isEmpty()
										&& !DailyOrderDao.listFreeRooms(
												"two beds").isEmpty()) {

									boolean run = true;

									do {
										List<Users> registerdUsersTemp = UsersDao
												.getUsersFromDBtoUsersList();
										List<Users> registerdUsers = new ArrayList<Users>();

										// go thru all users and only take the
										// ones that are not already checked in
										for (Users u : registerdUsersTemp) {
											if (!u.getIsCheckdIn()) {
												registerdUsers.add(u);
											}
										}
										// break if no users to check in!
										if (registerdUsers.isEmpty()) {
											System.out
													.println("No users to check in!");
											break;
										}

										System.out
												.println("You can check in following users:");
										System.out.println("[0] for cancel");
										int index = 1;
										for (Users u : registerdUsers) {

											System.out.print("[" + index + "] "
													+ u.getUserName() + " ");
											index++;
										}
										System.out.println();
										System.out
												.println("--------------------------------------------");
										// see if user is sure that he wants to
										// check in some one

										// make a generic menu - if there are 20
										// users to check in let admin select
										// from 1 to 20 what user to check in
										// and 0 for canceling this option
										int tempUserToCheckIn = UsersDao
												.inputInt(0, index - 1, input);
										if (tempUserToCheckIn == 0) {
											break;
										}
										String selectedUser = (registerdUsers
												.get(tempUserToCheckIn - 1)
												.getUserName());

										System.out
												.println("You have selected user: "
														+ selectedUser);
										System.out
												.println("Lets make his daily order:");

										boolean isdailyRecordInputValid = true;
										// make daily order for our checked in
										// user and set him a room

										String roomTypeTemp = "";
										String roomNumberTemp = "";
										int usersInput = 0;
										do {

											System.out
													.println("What type of room: [1]for \"one bed\" [2] for \"two bed\" [3] for \"apartment\"");
											System.out
													.println("[0] for cancel");

											userInput = UsersDao.inputInt(1, 3,
													input);

											// check the choice of admin and if
											// no rooms of that kind free let
											// him select other kind

											List<Integer> freeRooms = new ArrayList<Integer>();

											// give a room that is selected
											// "one" "two" bed or apartment
											if (userInput == 1) {

												freeRooms = DailyOrderDao
														.listFreeRooms("one bed");

												if (freeRooms.isEmpty()) {

													System.out
															.println("there are no one bed rooms free! pls select other");
												} else {
													roomTypeTemp = "one bed";
													// sort free rooms and give
													// first free to this user
													// this is my HOTEL I SAY IN
													// WHAT ROOM YOU STAY!
													Collections.sort(freeRooms);
													roomNumberTemp += freeRooms
															.get(0);
													break;
												}

											} else if (userInput == 2) {
												freeRooms = DailyOrderDao
														.listFreeRooms("two beds");

												if (freeRooms.isEmpty()) {
													System.out
															.println("there are no two bed rooms free! pls select other");
												} else {
													roomTypeTemp = "two beds";
													// sort free rooms and give
													// first to user
													Collections.sort(freeRooms);
													roomNumberTemp += freeRooms
															.get(0);
													break;
												}

											} else if (userInput == 3) {
												freeRooms = DailyOrderDao
														.listFreeRooms("apartment");

												if (freeRooms.isEmpty()) {
													System.out
															.println("there are no apartments free! pls select other");
												} else {
													roomTypeTemp = "apartment";
													Collections.sort(freeRooms);
													roomNumberTemp += freeRooms
															.get(0);
													break;
												}

											}

										} while (isdailyRecordInputValid);
										// now select other activities
										String[] activities = { "restourant",
												"gym", "sauna", "pool" };

										// ask for all this stuff
										// and if admin says yes add that to db
										// as service that is used
										for (int i = 0; i < activities.length; i++) {
											System.out
													.println("Is user going to use "
															+ activities[i]
															+ "?");
											System.out
													.println("[1] for yes [2] for no");
											if (UsersDao.inputInt(1, 2, input) == 1)
												activities[i] = "true";
											else
												activities[i] = "false";

										}
										// insert this to database!
										DailyOrderDao.addDailyOrderForUser(
												selectedUser, roomNumberTemp,
												roomTypeTemp, activities[1],
												activities[0], activities[2],
												activities[3]);

										// check user in!
										DailyOrderDao.CheckInUser(selectedUser);
										System.out
												.println(selectedUser
														+ " has beend checkd in today!");

										System.out.println("User: "
												+ selectedUser
												+ " has its daily order set!");

										// ask admin does he wonts to check in
										// more users
										System.out
												.println("Check in some one else: type [1] for YES or [2] for NO");

										if (UsersDao.inputInt(1, 2, input) == 2)
											run = false;

									} while (run);
								} else
									System.out
											.println("Sorry but there are no rooms or appartments free!");

								// check out will be done soon!
							} else if (userInput == 3) {
								boolean run = true;
								do {
									System.out
											.println("--------------------------------------------");
									System.out.println("Check users out");
									System.out
											.println("--------------------------------------------");
									System.out
											.println("[0] for cancel [1] aprove pending users check out [2] check user out");
									int userSelection = UsersDao.inputInt(0, 2,
											input);
									if (userSelection == 0) {
										break;

									} else if (userSelection == 1) {

										List<Users> registerdUsersTemp = UsersDao
												.getUsersFromDBtoUsersList();
										List<Users> registerdUsers = new ArrayList<Users>();

										// go thru all users and only take the
										// ones that are not already checked in
										for (Users u : registerdUsersTemp) {
											if (u.getIsCheckdIn()
													&& u.getUserCheckdOutDate() != null) {
												registerdUsers.add(u);
											}
										}
										// break if no users to check in!
										if (registerdUsers.isEmpty()) {
											System.out
													.println("No users pending to check OUT!");
											break;
										} else {
											System.out
													.println("You can check out following users:");
											System.out
													.println("[0] for cancel");

											int index = 1;
											for (Users u : registerdUsers) {

												System.out
														.print("["
																+ index
																+ "] "
																+ u.getUserName()
																+ " ");
												index++;
											}
											System.out.println();
											System.out
													.println("--------------------------------------------");
											int tempUserToCheckIn = UsersDao
													.inputInt(0, index - 1,
															input);
											if (tempUserToCheckIn == 0) {
												break;
											} else {
												Users userToAprove = registerdUsers
														.get(tempUserToCheckIn - 1);
												System.out
														.println("User: "
																+ userToAprove
																		.getUserName()
																+ " has been aproved and checkd out!");
												System.out
														.println("--------------------------------------------");

												System.out
														.println(UsersDao.CheckOutUser(
																userToAprove
																		.getUserName(),
																true,
																userToAprove
																		.getUserCheckdOutDate()));

											}

										}

									} else if (userSelection == 2) {

										List<Users> registerdUsersTemp = UsersDao
												.getUsersFromDBtoUsersList();
										List<Users> registerdUsers = new ArrayList<Users>();

										// go thru all users and only take the
										// ones that are not already checked in
										for (Users u : registerdUsersTemp) {
											if (u.getIsCheckdIn()
													&& u.getUserCheckdOutDate() == null) {
												registerdUsers.add(u);
											}
										}
										// break if no users to check in!
										if (registerdUsers.isEmpty()) {
											System.out
													.println("No users to check OUT!");
											break;
										} else {

											System.out
													.println("You can check out following users:");
											System.out
													.println("[0] for cancel");

											int index = 1;
											for (Users u : registerdUsers) {

												System.out
														.print("["
																+ index
																+ "] "
																+ u.getUserName()
																+ " ");
												index++;
											}
											System.out.println();
											System.out
													.println("--------------------------------------------");
											int tempUserToCheckIn = UsersDao
													.inputInt(0, index - 1,
															input);
											if (tempUserToCheckIn == 0) {
												break;
											} else {
												String selectedUser = (registerdUsers
														.get(tempUserToCheckIn - 1)
														.getUserName());

												System.out
														.println("You have selected user: "
																+ selectedUser
																+ " to check out!");

												System.out.println(UsersDao
														.CheckOutUser(
																selectedUser,
																true, null));

											}

										}

									}

									System.out
											.println("Check out more users: type [1] for YES or [2] for NO");

									if (UsersDao.inputInt(1, 2, input) == 2)
										run = false;

								} while (run);

							} else if (userInput == 4) {

								boolean run = true;
								do {
									System.out
											.println("--------------------------------------------");
									System.out.println("Edit user contiune?");
									System.out
											.println("--------------------------------------------");
									System.out
											.println("[1] for continue [2] for Cancel");
									if (UsersDao.inputInt(1, 2, input) == 2) {
										break;
									}
									if (UsersDao.getUsersFromDB() == "") {
										System.out
												.println("No useres to edit!");
										break;
									}

									System.out
											.println("--------------------------------------------");
									System.out
											.println("What user do you wont to edit:");
									System.out.println(UsersDao
											.getUsersFromDB());
									System.out
											.println("--------------------------------------------");

									System.out.println("User name: ");
									// clear scanner again...
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
										// ask user how many fields he wonts to
										// edit
										// edit every field he asks
										System.out
												.println("How many fields do you wont to edit?");
										int fNumber = UsersDao.inputInt(1, 7,
												input);

										String field = "";
										String fieldValue = "";
										// clear the scanner...
										input.nextLine();
										for (int i = 0; i < fNumber; i++) {
											// ask admin for every field what
											// value to enter
											System.out
													.println("What field do you wont to edit:");
											do {
												field = input.nextLine();
												// Cannot select field that does
												// exists in db

											} while (!((field
													.equals("userName")
													|| field.equals("password")
													|| field.equals("name")
													|| field.equals("lastName")
													|| field.equals("isAdmin")
													|| field.equals("gender") || field
														.equals("idCard"))));

											boolean editIsOk = true;
											do {

												if (field.equals("userName")) {
													String tempUserNameEdit = "";
													do {
														// ask for user name!
														System.out
																.println("Insert user name:");
														// ignore a line scanner
														// is madness
														tempUserNameEdit = input
																.nextLine();
														if (tempUserNameEdit
																.length() < 4
																|| tempUserNameEdit
																		.length() > 20) {
															System.out
																	.println("User name has to be biger than 4 letters and smaller than 20 ");
															// check if userName
															// is taken
														} else if (UsersDao
																.isAlreadyRegistered(tempUserNameEdit)) {
															System.out
																	.println("That user name is taken!");
														} else {
															break;
														}

													} while (true);
													// Alight with user name
													// SAVE IT
													fieldValue = tempUserNameEdit;
												} else if (field
														.equals("password")) {
													String tempPassword = "";
													do {
														System.out
																.println("password for: "
																		+ tempUserName
																		+ " user.");
														tempPassword = input
																.nextLine();

														if (tempPassword
																.length() < 8) {
															System.out
																	.println("Password is too short!");
														} else if (tempPassword
																.length() > 20) {
															System.out
																	.println("Password is too long!");
														} else
															break;

													} while (true);
													// all set add this stuff!
													fieldValue = tempPassword;
												} else if (field.equals("name")) {

													String tempFirstName = "";
													do {
														System.out
																.println("first name for: "
																		+ tempUserName
																		+ " user.");
														tempFirstName = input
																.nextLine();

														if (tempFirstName
																.length() < 1) {
															System.out
																	.println("Name must have more than 1 letter!");
														} else if (tempFirstName
																.length() > 20) {
															System.out
																	.println("Name is too long long!");
														} else {
															// set whole name to
															// lower case
															tempFirstName
																	.toLowerCase();

															// set first letter
															// of name to
															// upper case
															tempFirstName = Character
																	.toUpperCase(tempFirstName
																			.charAt(0))
																	+ tempFirstName
																			.substring(1);

														}
														break;

													} while (true);
													fieldValue = tempFirstName;

												} else if (field
														.equals("lastName")) {

													String tempLastName = "";
													do {
														System.out
																.println("last name for: "
																		+ tempUserName
																		+ " user.");
														tempLastName = input
																.nextLine();

														if (tempLastName
																.length() < 1) {
															System.out
																	.println("Last name must have more than 1 letter!");
														} else if (tempLastName
																.length() > 20) {
															System.out
																	.println("Last name is too long!");
														} else {

															// set whole last
															// name to lower
															// case
															tempLastName
																	.toLowerCase();

															// set first letter
															// of last name
															// to upper case
															tempLastName = Character
																	.toUpperCase(tempLastName
																			.charAt(0))
																	+ tempLastName
																			.substring(1);

															break;
														}

													} while (true);
													fieldValue = tempLastName;

												} else if (field
														.equals("isAdmin")) {
													// set admin or basic user
													String tempIsAdmin = "";
													System.out
															.println("Give admin rights to: "
																	+ tempUserName
																	+ " user [1] for yes [2] for no!");

													// admin can only select 1
													// or 2 if its
													// one set admin to true if
													// not set it
													// to false
													if (UsersDao.inputInt(1, 2,
															input) == 1)
														tempIsAdmin = "true";
													else
														tempIsAdmin = "false";

													fieldValue = tempIsAdmin;

												} else if (field
														.equals("gender")) {

													System.out
															.println("gender name for: "
																	+ tempUserName
																	+ " user [1] for male [2] for female.");
													String tempGender = "";

													// admin can only select 1
													// or 2 after he
													// selects its set male or
													// female
													// according to his
													// selection
													if (UsersDao.inputInt(1, 2,
															input) == 1) {
														tempGender = "male";
													} else {
														tempGender = "female";
													}

													fieldValue = tempGender;

												} else if (field
														.equals("idCard")) {
													String tempIdCard = "";
													do {
														System.out
																.println("idCard name for: "
																		+ tempUserName
																		+ " user.");
														tempIdCard = input
																.nextLine();

														if (tempIdCard.length() < 8) {
															System.out
																	.println("Id has to be more than 8 letters!");
														} else if (tempIdCard
																.length() > 20) {
															System.out
																	.println("Id has to be less than 20 letters!");
														} else
															break;

													} while (true);
													fieldValue = tempIdCard;

												}

												UsersDao.updateUserInDatabase(
														tempUserName, field,
														fieldValue);
												System.out.println("Field "
														+ field
														+ " updated to value: "
														+ fieldValue);

												break;
											} while (editIsOk);

										}
									}

									System.out
											.println("Edit more: type [1] for YES or [2] for NO");

									if (UsersDao.inputInt(1, 2, input) == 2)
										run = false;

								} while (run);

								// Oline menu
							} else if (userInput == 5) {
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
											.println("[0] for exit [1] for log everyone out! [2] for log some one out!");
									int userInputForOnline = UsersDao.inputInt(
											0, 2, input);
									if (userInputForOnline == 0) {

										run = false;
										break;

									} else if (userInputForOnline == 1) {
										UsersDao.logOutAllUsers();
										System.out
												.println("Every one is now loged out!");
										run = false;
									} else {
										String logOutSomeOne = "";

										System.out
												.println("Who do you wont to log out:");
										// clean scanner again
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
									if (run) {
										System.out
												.println("Logout someone again?: type [1] for YES or [2] for NO");
										if (UsersDao.inputInt(1, 2, input) == 2)
											run = false;

									}
								} while (run);

								// find users
							} else if (userInput == 6) {

								boolean run = true;
								do {
									System.out
											.println("How do you wona search for your user: [1] for by userName [2] for by Name [3] for by ID");
									int userInputForSearch = UsersDao.inputInt(
											1, 3, input);
									if (userInputForSearch == 1) {
										// clear scanner
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

										// clear this mad scanner again..
										input.nextLine();
										System.out
												.println("What is that user Name?");
										System.out.println(UsersDao
												.findUserByName(input
														.nextLine()));

									} else if (userInputForSearch == 3) {
										// clear
										input.nextLine();
										System.out
												.println("What is that user ID number?");
										System.out.println(UsersDao
												.findUserByIDCard(input
														.nextLine()));

									}

									System.out
											.println("Find some one else: type [1] for YES or [2] for NO");
									if (UsersDao.inputInt(1, 2, input) == 2)
										run = false;

								} while (run);

							} else if (userInput == 7) {
								UsersDao.logOut(userSession);
								break;
							}
						}
						System.out.println("You are logged out.");
						UsersDao.logOut(userSession);
						break;
					}
				} else {

					// stay in main menu for user until he logs out or some one
					// logs him out
					while (userSession.getUserName() != null) {
						// every move of user in main menu check if he is still
						// logged in if some one logs him out get out of here
						while (UsersDao.isActive(userSession.getUserName())) {
							System.out.println("User: ["
									+ userSession.getUserName()
									+ "] is logged in.");
							System.out
									.println("--------------------------------------------");
							System.out.println("User main menu:");
							System.out
									.println("--------------------------------------------");
							System.out.println("[1] Check my status");
							System.out.println("[2] Change my order");
							System.out.println("[3] Check out");
							System.out.println("[4] Log out");

							int userInput = UsersDao.inputInt(1, 4, input);

							if (userInput == 1) {
								if (UsersDao.isCheckdIn(userSession
										.getUserName())) {

									System.out.println(UsersDao.CheckOutUser(
											userSession.getUserName(), false,
											null));
									System.out.println("Your daily order:");
									System.out.println(DailyOrderDao
											.getDailyOrder(
													userSession.getUserName(),
													false));
								} else {
									System.out
											.println("You have no status, you are not checkd in!");
								}
							} else if (userInput == 2) {
								if (UsersDao.isCheckdIn(userSession
										.getUserName())) {

									System.out.println("Your current order:");
									System.out.println(DailyOrderDao
											.getDailyOrder(
													userSession.getUserName(),
													false));

									String currentDailyOrder = DailyOrderDao
											.getDailyOrder(
													userSession.getUserName(),
													true);
									String[] dailyOrderSplit = currentDailyOrder
											.split("-");

									boolean run = true;
									String roomNumber = dailyOrderSplit[0];
									String roomType = dailyOrderSplit[1];
									String gym = dailyOrderSplit[2];
									String restaurant = dailyOrderSplit[3];
									String saun = dailyOrderSplit[4];
									String pool = dailyOrderSplit[5];
									int selection;

									do {

										System.out
												.println("What field do you wona change:");
										System.out
												.println("[1]for room type [2]for gym [3]for restaurant [4]for saun [5]for pool");

										selection = UsersDao.inputInt(1, 5,
												input);

										if (selection == 1) {
											if (!DailyOrderDao.listFreeRooms(
													"apartment").isEmpty()
													&& !DailyOrderDao
															.listFreeRooms(
																	"one bed")
															.isEmpty()
													&& !DailyOrderDao
															.listFreeRooms(
																	"two beds")
															.isEmpty()) {
												boolean runOnRooms = true;
												int selectionOfRoom;
												do {
													System.out
															.println("Change room to: [1]for one bed [2]for two beds [3]for apartment");
													selectionOfRoom = UsersDao
															.inputInt(1, 3,
																	input);

													if (selectionOfRoom == 1) {
														List<Integer> freeRooms = DailyOrderDao
																.listFreeRooms("one bed");
														if (freeRooms.isEmpty()) {
															System.out
																	.println("That kind of room not avaible");
														} else {
															// set one bed and
															// room
															// number!
															roomType = "one bed";
															roomNumber = Integer
																	.toString(freeRooms
																			.get(0));
															runOnRooms = false;
														}

													} else if (selectionOfRoom == 2) {

														List<Integer> freeRooms = DailyOrderDao
																.listFreeRooms("two beds");
														if (freeRooms.isEmpty()) {
															System.out
																	.println("That kind of room not avaible");
														} else {
															// set one bed and
															// room
															// number!
															roomType = "two beds";
															roomNumber = Integer
																	.toString(freeRooms
																			.get(0));
															runOnRooms = false;
														}
													} else if (selectionOfRoom == 3) {

														List<Integer> freeRooms = DailyOrderDao
																.listFreeRooms("apartment");
														if (freeRooms.isEmpty()) {
															System.out
																	.println("That kind of room not avaible");
														} else {
															// set one bed and
															// room
															// number!
															roomType = "apartment";
															roomNumber = Integer
																	.toString(freeRooms
																			.get(0));
															runOnRooms = false;
														}
													}

												} while (runOnRooms);
											} else {
												System.out
														.println("Sorry you cannot change your room, there are no rooms free!");
											}
										} else if (selection == 2) {

											String gymTemp = gym;
											if (gym.equals("true"))
												gym = "false";
											else
												gym = "true";
											System.out
													.println("Changed gym from "
															+ gymTemp
															+ " to "
															+ gym);

										} else if (selection == 3) {
											String restaurantTemp = restaurant;
											if (restaurant.equals("true"))
												restaurant = "false";
											else
												restaurant = "true";
											System.out
													.println("Changed gym from "
															+ restaurantTemp
															+ " to "
															+ restaurant);

										} else if (selection == 4) {

											String saunTemp = saun;
											if (saun.equals("true"))
												saun = "false";
											else
												saun = "true";
											System.out
													.println("Changed gym from "
															+ saunTemp
															+ " to "
															+ saun);

										} else if (selection == 5) {

											String poolTemp = pool;
											if (pool.equals("true"))
												pool = "false";
											else
												pool = "true";
											System.out
													.println("Changed gym from "
															+ poolTemp
															+ " to "
															+ pool);
										}
										System.out.println("Your new order:");
										System.out.println("Room type:\t\t["
												+ roomType + "]");
										System.out.println("Using gym:\t\t["
												+ gym + "]");
										System.out
												.println("Using restaurant:\t["
														+ restaurant + "]");
										System.out.println("Using saun:\t\t["
												+ saun + "]");
										System.out.println("Using pool:\t\t["
												+ pool + "]");

										System.out
												.println("--------------------------------------------");
										System.out
												.println("Edit more or save changes: type [1] for EDIT or [2] for SAVE");
										System.out
												.println("--------------------------------------------");

										if (UsersDao.inputInt(1, 2, input) == 2)
											run = false;
									} while (run);

									if (roomType.equals(dailyOrderSplit[1])
											&& gym.equals(dailyOrderSplit[2])
											&& restaurant
													.equals(dailyOrderSplit[3])
											&& saun.equals(dailyOrderSplit[4])
											&& pool.equals(dailyOrderSplit[5])) {
										System.out
												.println("You did not change anything... plonker...");
									} else {

										Calendar today = Calendar.getInstance();
										Calendar checkdIn = Calendar
												.getInstance();
										checkdIn.setTime(UsersDao
												.getCheckedIn(userSession
														.getUserName()));
										// check if user is chekcd in today if
										// yes do not make receipt just change
										// the order!

										boolean sameDay = today
												.get(Calendar.YEAR) == checkdIn
												.get(Calendar.YEAR)
												&& today.get(Calendar.DAY_OF_YEAR) == checkdIn
														.get(Calendar.DAY_OF_YEAR);
										//
										DailyOrderDao.changeDailyOrder(
												userSession.getUserName(),
												roomNumber, roomType, gym,
												restaurant, saun, pool,
												!sameDay);

									}

								} else {
									System.out
											.println("You cannot change your order, you are not checkd in!");
								}

							} else if (userInput == 3) {

								if (UsersDao.isCheckdIn(userSession
										.getUserName())) {

									System.out
											.println("Are you sure that you wont to check out today?");
									System.out
											.println("[1] for yes [2] for no");
									if (UsersDao.inputInt(1, 2, input) == 1) {
										UsersDao.userChecksOut(userSession
												.getUserName());
										System.out
												.println("You have checkd yourself out, admin needs to aprove your action!");
									} else {

									}
								} else {
									System.out
											.println("You are not checkd in!");
								}
							} else if (userInput == 4) {
								UsersDao.logOut(userSession);
								break;

							}

						}

						System.out.println("You are logged out.");
						UsersDao.logOut(userSession);
						break;

					}
				}

			}
		}
	}
}
