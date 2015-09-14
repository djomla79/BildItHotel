package bildIt.hotel;

import java.sql.Date;
import java.util.ArrayList;

public class Order {

	private String customerUserName;
	private Date startDate;
	private Date endDate;
	private boolean isPaid = false;

	ArrayList<Order> lista;

	/** Prazan/default konstruktor */
	public Order() {
	}

	/** Konstruktor sa parametrima */
	public Order(String customerUserName, Date startDate, Date endDate) {

		// lista = new ArrayList<>();

		this.customerUserName = customerUserName;
		this.startDate = startDate;
		this.endDate = endDate;

		// lista.add(1, new Order(customerUserName, startDate, endDate));

	}

	/** Geteri i seteri */
	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	/** Overrajdana toString metoda */
	@Override
	public String toString() {

		if (isPaid()) {
			return "Racun je placen.";
		} else {
			return "Racun nije placen.";
		}

	}

}