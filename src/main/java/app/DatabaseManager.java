package app;

import java.sql.*;

public class DatabaseManager {

	private Connection connection;
	private static String port = "3306";
	private static String host = "127.0.0.1";
	private static String dbName = "biblioteca";
	private static String user = "root";
	private static String password = "";
	private static String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

	public DatabaseManager() {
		super();
		this.connect();
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public Connection connect() {
		try {
			return connection = DriverManager.getConnection(url, user, password);
			// System.out.println("Connessione al database " + this.dbName + " riuscita.");
		} catch (SQLException e) {
			// System.err.println("Errore durante la connessione al database: " +
			// this.dbName + e.getMessage());
		}
		return connection;
	}

	public void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Connessione al database chiusa.");
			}
		} catch (SQLException e) {
			System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
		}
	}
}
