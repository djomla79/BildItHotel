package bildIt.hotel.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bildIt.hotel.Order;

/**
 * 
 * Author Mladen Todorovic
 * 
 * */
public class OrderDao {

	ArrayList<Order> lista;

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
