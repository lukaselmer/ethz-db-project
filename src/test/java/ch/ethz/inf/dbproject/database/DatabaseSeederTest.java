package ch.ethz.inf.dbproject.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import ch.ethz.inf.dbproject.database.DatabaseHelper;
import ch.ethz.inf.dbproject.database.DatabaseSeeder;
import ch.ethz.inf.dbproject.exceptions.InvalidStateException;

public class DatabaseSeederTest {

	private static final List<String> EXPECTED_TABLES = Arrays.asList(new String[] { "city", "category", "user", "project", "stretch_goal",
			"funding_amount", "fund", "comment" });

	@Before
	public void reset() throws SQLException {
		DatabaseSeeder.resetAndSeed();
	}

	@AfterClass
	public static void resetAll() throws SQLException {
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

	@Test
	public void testInserts() throws SQLException, InvalidStateException {
		assertEquals("users should be created", 3, DatabaseHelper.getCount("user"));
		assertEquals("funding amounts should be created", 8, DatabaseHelper.getCount("funding_amount"));
	}

}
