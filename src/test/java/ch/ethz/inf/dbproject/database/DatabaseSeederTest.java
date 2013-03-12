package test.java.ch.ethz.inf.dbproject.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import ch.ethz.inf.dbproject.database.DatabaseHelper;
import ch.ethz.inf.dbproject.database.DatabaseSeeder;
import ch.ethz.inf.dbproject.database.MySQLConnection;

public class DatabaseSeederTest {

	private static final List<String> EXPECTED_TABLES = Arrays.asList(new String[] { "users", "projects" });

	@Before
	public void reset() throws SQLException{
		DatabaseSeeder.resetAndSeed();
	}
	
	@AfterClass
	public static void resetAll() throws SQLException{
		DatabaseSeeder.resetAndSeed();
	}
	
	@Test
	public void testReset() throws SQLException {
		assertTrue("database not should be empty", DatabaseHelper.getTables().size() > 0);
		DatabaseSeeder.reset();
		assertTrue("database should be empty", DatabaseHelper.getTables().size() == 0);
	}

	@Test
	public void testSeed() throws SQLException {
		DatabaseSeeder.reset();
		assertTrue("database should be empty", DatabaseHelper.getTables().size() == 0);
		DatabaseSeeder.seed();
		assertTrue("database not should be empty", DatabaseHelper.getTables().size() > 0);
	}

	@Test
	public void testResetAndSeed() throws SQLException {
		List<String> expectedTables = EXPECTED_TABLES;
		List<String> existingTables = DatabaseHelper.getTables();
		for (String name : expectedTables) {
			assertTrue("table " + name + " should exist", existingTables.contains(name));
		}
		for (String name : existingTables) {
			assertTrue("table " + name + " should not exist", expectedTables.contains(name));
		}
	}

}
