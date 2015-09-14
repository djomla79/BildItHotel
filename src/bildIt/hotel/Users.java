package bildIt.hotel;

/**
 * 
 * @author Bojan Aleksic
 */
public class Users {

	/** Declare data fields for users */
	private static String userName;
	private static String password;
	private String firstName;
	private String lastName;
	@SuppressWarnings("unused")
	private boolean isAdmin;
	private String gender;
	private String idCard;
	private String isOnline;

	/** Instantiate static Users object with userName and password */
	public static Users user = new Users(userName, password);

	/** An empty constructor */
	public Users() {

	}

	public Users(String userName) {
		this.userName = userName;
	}

	/** Constructor with specified user name and password */
	public Users(String userName, String password) {
		Users.userName = userName;
		Users.password = password;
	}

	/** Constructor with specified all parameters */
	public Users(String userName, String password, String firstName,
			String lastName, boolean isAdmin, String gender, String idCard,
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

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
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

	public String getIdCard() {
		return idCard;
	}

	public String isOnline() {
		return isOnline;
	}

}