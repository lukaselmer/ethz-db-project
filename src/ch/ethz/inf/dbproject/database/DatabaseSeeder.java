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
		createTables(s);
		seedData(s);
		s.close();
	}

	private static void createTables(Statement s) throws SQLException {
		s.execute("create table users (id int not null, name varchar(255), primary key ( id ))");
		s.execute("create table projects (id int not null, user_id int not null, name varchar(255), "
				+ "primary key ( id ), key index_projects_on_user_id (user_id))");
	}

	private static void seedData(Statement s) throws SQLException {
		s.execute("insert into users values (1, 'Fred'), (2, 'Ivo'), (3, 'Luke')");
		s.execute("insert into projects values (1, 3, 'HSR'), (2, 2, 'ETH'), (3, 1, 'World domination'), (4, 1, 'Foo')");
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
