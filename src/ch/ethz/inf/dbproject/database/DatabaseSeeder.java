package ch.ethz.inf.dbproject.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Resets and seeds the database with some test data
 */
public class DatabaseSeeder {
	private static final Connection con = MySQLConnection.getInstance().getConnection();

	public static void resetAndSeed() throws SQLException {
		reset();
		seed();
	}

	// TODO: this method is public because of the test. Fix it somehow?
	public static void seed() throws SQLException {
		Statement s = con.createStatement();
		s.execute("create table users (id int not null, name varchar(255), primary key ( id ))");
		s.execute("create table projects (id int not null, name varchar(255), primary key ( id ))");
		s.close();
	}

	// TODO: this method is public because of the test. Fix it somehow?
	public static void reset() throws SQLException {
		Statement s = con.createStatement();
		for (String tableName : DatabaseHelper.getTables()) {
			s.execute("drop table " + tableName);
		}
		s.close();
	}

}
