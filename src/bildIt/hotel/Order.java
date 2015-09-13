package bildIt.hotel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bildIt.hotel.dao.impl.ConnectionUtil;

/**
 * 
 * Author Mladen Todorovic
 * 
 * */
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

		lista = new ArrayList<>();

		this.customerUserName = customerUserName;
		this.startDate = startDate;
		this.endDate = endDate;

		lista.add(1, new Order(customerUserName, startDate, endDate));

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

	/** Metoda za unos podataka u bazu/tabela - 'order' */
	public void saveToBase() throws ClassNotFoundException, SQLException {

		ConnectionUtil con = new ConnectionUtil();

		Connection connection = con.connect();

		/** Ubacivanje podataka u bazu podataka */
		PreparedStatement unos = connection
				.prepareStatement(" INSERT INTO `order`(customerUserName, startDate, endDate) VALUES (?, ?, ?) ");
		try {

			/** unos podataka iz liste u bazu */
			for (Order list : lista) {

				unos.setString(1, list.getCustomerUserName());
				unos.setDate(2, (java.sql.Date) list.getStartDate());
				unos.setDate(3, (java.sql.Date) list.getEndDate());
				unos.executeUpdate();

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
	}

	/** Metoda za ispis podataka iz baze/tabela - 'order' */
	public void loadFromBase() throws ClassNotFoundException, SQLException {

		ConnectionUtil con = new ConnectionUtil();

		PreparedStatement preparedStatement = null;

		String sql = "SELECT * FROM order;";

		try {

			Connection connection = con.connect();

			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getString("customerUserName"));
				System.out.println(rs.getDate("startDate"));
				System.out.println(rs.getDate("endDate"));
				System.out.println();
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
	}
}