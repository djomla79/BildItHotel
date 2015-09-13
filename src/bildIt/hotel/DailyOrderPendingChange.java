package bildIt.hotel;

import java.util.Date;

/**
 * @author Ognjen Lazic
 *
 */
public class DailyOrderPendingChange {

	private int id;
	private int roomNumber;
	private int orderID;
	private Date date;
	private boolean gym;
	private boolean restaurant;
	private boolean saun;
	private boolean pool;

	public DailyOrderPendingChange() {

	}

	public DailyOrderPendingChange(int id, int roomNumber, int orderID,
			Date date, boolean gym, boolean restourant, boolean pool) {

		this.id = id;
		this.roomNumber = roomNumber;
		this.orderID = orderID;
		this.date = date;
		this.gym = gym;
		this.restaurant = restourant;
		this.pool = pool;

	}

}
