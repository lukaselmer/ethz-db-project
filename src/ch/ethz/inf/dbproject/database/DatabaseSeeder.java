package ch.ethz.inf.dbproject.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Resets and seeds the database with some test data
 */
public class DatabaseSeeder {
	private final Connection con = MySQLConnection.getInstance().getConnection();

	public void resetAndSeed() throws SQLException {
		reset();
		seed();
	}

	private void seed() throws SQLException {
		createUsers();
	}

	private void createUsers() throws SQLException {
		con.nativeSQL("create table users (id int not null, name varchar(255), primary key ( id ))");
		con.nativeSQL("create table projects (id int not null, name varchar(255), primary key ( id ))");
	}

	private void reset() throws SQLException {
	}

}
