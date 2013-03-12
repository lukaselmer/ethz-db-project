package ch.ethz.inf.dbproject.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Driver;

/**
 * A Wrapper around an SQL Connection
 */
public final class MySQLConnection {

	private final Connection connection;

	/**
	 * Singleton instance: We want to avoid re-establishing connections across
	 * web server requests.
	 */
	private static MySQLConnection instance = null;

	public static synchronized MySQLConnection getInstance() {
		if (instance == null) {
			instance = new MySQLConnection();
		}
		return instance;
	}

	private MySQLConnection() {
		Connection connection = null;

		try {
			new Driver();

			try {
				/*
				 * How to create the dev database:
				 * 
				 * > mysql -u root
				 * 
				 * > create user dmdb@localhost identified by '1234';
				 * 
				 * > create database dmdb2013;
				 * 
				 * > grant all on dmdb2013.* to dmdb@localhost;
				 * 
				 * > quit;
				 */


				String uri = System.getenv("DATABASE_URL");
				if (uri == null || uri.equals("")) {
					uri = "mysql://dmdb:1234@localhost:3306/dmdb2013";
					Logger.getLogger("global").log(Level.INFO, "Using default database settings: " + uri);
				}

				URI dbUri = new URI(uri);

				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

				connection = DriverManager.getConnection(dbUrl, username, password);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				Logger.getLogger("global").log(Level.SEVERE,
						"Your DATABASE_URL env variable seems to be badly formatted: " + System.getenv("DATABASE_URL"));

			}
		} catch (final SQLException e) {
			/**
			 * Make sure that we really see this error.
			 */
			System.err.println("Could not connect to MYSQL. Is the server running?");
			JOptionPane.showMessageDialog(null, "Could not connect to MYSQL. Is the server running?\n" + "Error in "
					+ this.getClass().getName() + ".", "Critical Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		this.connection = connection;
	}

	public final Connection getConnection() {
		return this.connection;
	}

	public static Connection getCon() {
		return getInstance().getConnection();
	}
}