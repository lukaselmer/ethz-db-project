package ch.ethz.inf.dbproject.model.access;

import java.sql.SQLException;

import ch.ethz.inf.dbproject.database.DatabaseHelper;
import ch.ethz.inf.dbproject.database.DatabaseSeeder;
import ch.ethz.inf.dbproject.database.MySQLConnection;
import ch.ethz.inf.dbproject.exceptions.InvalidStateException;

public abstract class AbstractAccess {

	static {
		try {
			try {
				// TODO make this better?
				if (DatabaseHelper.getTables().size() == 0 || DatabaseHelper.getCount("project") == 0)
					DatabaseSeeder.resetAndSeed();
			} catch (SQLException e) {
				e.printStackTrace();
				DatabaseSeeder.resetAndSeed();
			} catch (InvalidStateException e) {
				e.printStackTrace();
				DatabaseSeeder.resetAndSeed();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected MySQLConnection sqlConnection;

	protected AbstractAccess() {
		sqlConnection = MySQLConnection.getInstance();
		try {
			initStatements();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	abstract protected void initStatements() throws SQLException;
}
