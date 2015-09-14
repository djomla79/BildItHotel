package bildIt.hotel;

import java.util.Date;

/**
 * @author Ognjen Lazic
 *
 */
public class DailyOrder {

	private int id;
	private int roomNumber;
	private int orderID;
	private Date date;
	private boolean gym;
	private boolean restaurant;
	private boolean saun;
	private boolean pool;

	public DailyOrder() {

	}

	public DailyOrder(int id, int roomNumber, int orderID, Date date,
			boolean gym, boolean restourant, boolean pool) {

		this.id = id;
		this.roomNumber = roomNumber;
		this.orderID = orderID;
		this.date = date;
		this.gym = gym;
		this.restaurant = restourant;
		this.pool = pool;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isGym() {
		return gym;
	}

	public void setGym(boolean gym) {
		this.gym = gym;
	}

	public boolean isRestaurant() {
		return restaurant;
	}

	public void setRestaurant(boolean restaurant) {
		this.restaurant = restaurant;
	}

	public boolean isSaun() {
		return saun;
	}

	public void setSaun(boolean saun) {
		this.saun = saun;
	}

	public boolean isPool() {
		return pool;
	}

	public void setPool(boolean pool) {
		this.pool = pool;
	}

}
