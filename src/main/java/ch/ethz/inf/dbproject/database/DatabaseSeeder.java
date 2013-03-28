package ch.ethz.inf.dbproject.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.ethz.inf.launch.Main;

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
		Statement s = con.createStatement();

		executeSqlFile(s, "schema.sql");
		executeSqlFile(s, "data.sql");

		s.close();
	}

	private static void executeSqlFile(Statement s, String filename) throws SQLException {
		String[] statements = readFile(filename);
		for (String statement : statements) {
			if (statement.trim().length() > 0)
				s.execute(statement);
		}
	}

	private static String[] readFile(String file) {
		String sql = "";
		try {
			StringBuilder b = new StringBuilder();
			InputStream is = DatabaseSeeder.class.getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = br.readLine();
			while (line != null) {
				b.append(line);
				b.append('\n');
				line = br.readLine();
			}
			sql = b.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sql.split(";");
	}

	protected static void reset() throws SQLException {
		Statement s = con.createStatement();
		s.execute("SET foreign_key_checks = 0");
		for (String tableName : DatabaseHelper.getTables()) {
			s.execute("drop table " + tableName);
		}
		s.execute("SET foreign_key_checks = 1");
		s.close();
	}

}
