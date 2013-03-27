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

	protected static void seed() throws SQLException {
//		Statement s = con.createStatement();
//		createTables(s);
//		seedData(s);
//		s.close();
		
		// TODO: Load createTable sql script

		
		// TODO: Load insertData sql script
		
	}

//	private static void createTables(Statement s) throws SQLException {
//		createTable(s, "users", "name varchar(255) not null");
//		createTable(s, "projects", "user_id int not null, name varchar(255) not null", "key index_projects_on_user_id (user_id), unique (name)");
//		createTable(s, "cities", "name varchar(255) not null", "unique (name)");
//	}
//
//	private static void createTable(Statement s, String table, String vars, String additions) throws SQLException {
//		additions = additions == null ? "" : ", " + additions;
//		s.execute("create table " + table + " (id int not null, " + vars + ", primary key ( id )" + additions + ")");
//	}
//
//	private static void createTable(Statement s, String table, String vars) throws SQLException {
//		createTable(s, table, vars, null);
//	}
//
//	private static void seedData(Statement s) throws SQLException {
//		s.execute("insert into users values (1, 'Fred'), (2, 'Ivo'), (3, 'Luke')");
//		s.execute("insert into cities values (1, 'Zurich'), (2, 'Bern'), (3, 'Geneva')");
//		s.execute("insert into projects values (1, 3, 'HSR'), (2, 2, 'ETH'), (3, 1, 'World domination'), (4, 1, 'Foo')");
//	}

	protected static void reset() throws SQLException {
		Statement s = con.createStatement();
		for (String tableName : DatabaseHelper.getTables()) {
			s.execute("drop table " + tableName);
		}
		s.close();
	}

}
