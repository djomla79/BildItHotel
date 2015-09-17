package bildIt.hotel;

import java.util.Date;

/**
 * 
 * @author Bojan Aleksic
 */
public class Users {

	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private boolean isAdmin;
	private String gender;
	private String idCard;
	private boolean isOnline;
	private boolean isCheckdIn;
	private Date dateOfCheckingIn;
	private Date userCheckdOutDate;

	public Users() {

	}

	public Users(String userName) {
		this.userName = userName;
	}

	public Users(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Users(String userName, String password, String firstName,
			String lastName, boolean isAdmin, String gender, String idCard,
			boolean isOnline) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = isAdmin;
		this.gender = gender;
		this.idCard = idCard;
		this.isOnline = isOnline;

	}

	public Users(String userName, String password, String firstName,
			String lastName, boolean isAdmin, String gender, String idCard,
			boolean isOnline, boolean isCheckdIn, Date dateOfCheckingIn,
			Date userCheckdOutDate) {

		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isAdmin = isAdmin;
		this.gender = gender;
		this.idCard = idCard;
		this.isOnline = isOnline;
		this.isCheckdIn = isCheckdIn;
		this.dateOfCheckingIn = dateOfCheckingIn;
		this.userCheckdOutDate = userCheckdOutDate;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public void setCheckdIn(boolean isCheckdIn) {
		this.isCheckdIn = isCheckdIn;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

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

	public String getIdCard() {
		return idCard;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public boolean getIsCheckdIn() {
		return isCheckdIn;
	}

	public void setIsCheckdIn(boolean isCheckdIn) {
		this.isCheckdIn = isCheckdIn;
	}

	public Date getDateOfCheckingIn() {
		return dateOfCheckingIn;
	}

	public void setDateOfCheckingIn(Date dateOfCheckingIn) {
		this.dateOfCheckingIn = dateOfCheckingIn;
	}

	public Date getUserCheckdOutDate() {
		return userCheckdOutDate;
	}

	public void setUserCheckdOutDate(Date userCheckdOutDate) {
		this.userCheckdOutDate = userCheckdOutDate;
	}

}