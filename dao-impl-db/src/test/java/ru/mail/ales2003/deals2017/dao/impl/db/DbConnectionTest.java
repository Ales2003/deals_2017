package ru.mail.ales2003.deals2017.dao.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectionTest {

	/*public static void main(String[] args) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/deals-2017", "postgres", "postgres");
			System.out.println("Соединение установлено.");

			Statement createStatement = con.createStatement();
			createStatement.execute("select * from customer order by id");

			ResultSet resultSet = createStatement.getResultSet();
			resultSet.next();
			int id = resultSet.getInt("id");

			boolean next = resultSet.next();
			int id2 = resultSet.getInt("id");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}*/
}
