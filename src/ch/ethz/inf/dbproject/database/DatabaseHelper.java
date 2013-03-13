package ch.ethz.inf.dbproject.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.exceptions.InvalidStateException;

public class DatabaseHelper {

	public static List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		ResultSet rs = MySQLConnection.getInstance().getConnection().getMetaData().getTables(null, null, "%", null);
		while (rs.next()) {
			tables.add(rs.getString(3));
		}
		return tables;
	}

	public static int getCount(String table) throws SQLException, InvalidStateException {
		// Possible SQL injection voulnerability => should escape table name
		ResultSet rs = MySQLConnection.getInstance().getConnection().createStatement().executeQuery("select count(*) from " + table);

		if (!rs.next())
			throw new InvalidStateException("Query unsuccessful. Does the table " + table + " exist?");

		return rs.getInt(1);

	}
}
