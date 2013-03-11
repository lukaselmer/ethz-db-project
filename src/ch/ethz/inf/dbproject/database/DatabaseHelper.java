package ch.ethz.inf.dbproject.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

	public static List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		ResultSet rs = MySQLConnection.getCon().getMetaData().getTables(null, null, "%", null);
		while (rs.next()) {
			tables.add(rs.getString(3));
		}
		return tables;
	}
}
