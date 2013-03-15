package ch.ethz.inf.dbproject.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import ch.ethz.inf.dbproject.database.MySQLConnection;

public class MySQLConnectionTest {

	@Test
	public void testGetInstance() throws SQLException {
		MySQLConnection c = MySQLConnection.getInstance();
		assertNotNull(c);
		Connection con = c.getConnection();
		assertNotNull(con);
		assertTrue("Database connection is not valid!", con.isValid(1));
		assertFalse("Database is not writable!", con.isReadOnly());
	}

}
