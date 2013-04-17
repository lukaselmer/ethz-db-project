package ch.ethz.inf.dbproject.model.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.City;

public class CityAccess extends AbstractAccess {

	private static CityAccess instance = null;
	public static synchronized CityAccess getInstance() {
		if (instance == null) {
			instance = new CityAccess();
		}
		return instance;
	}
	
	private PreparedStatement pstmt_deleteCity;
	private PreparedStatement pstmt_insertCity;
	private PreparedStatement pstmt_updateCity;
	private PreparedStatement pstmt_getCityById;
	private PreparedStatement pstmt_getCityByName;
	
	protected void initStatements() throws SQLException {
		pstmt_insertCity = this.sqlConnection.getConnection().prepareStatement("insert into city (name) values (?)", Statement.RETURN_GENERATED_KEYS);
		pstmt_deleteCity = this.sqlConnection.getConnection().prepareStatement("delete from city where id = ?");
		pstmt_updateCity = this.sqlConnection.getConnection().prepareStatement("update city set name = ? where id = ?");
		pstmt_getCityById = this.sqlConnection.getConnection().prepareStatement("select * from city where id = ?");
		pstmt_getCityByName = this.sqlConnection.getConnection().prepareStatement("select * from city where name = ?");
	}

	public final int insertCity (final String name) {
		
		try {
			pstmt_insertCity.setString(1, name);
			
			pstmt_insertCity.execute();

		    ResultSet rs = pstmt_insertCity.getGeneratedKeys();
			rs.next();
			
			return rs.getInt(1);
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	
	public final void updateCity (final int id, final String name) {
		
		try {
			pstmt_updateCity.setString(1, name);
			pstmt_updateCity.setInt(2, id);
			
			pstmt_updateCity.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final City getCityById(final int id) {
		City c = null;

		try {
			pstmt_getCityById.setInt(1, id);
			ResultSet rs = pstmt_getCityById.executeQuery();
			if (rs.next())
				c = new City(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}
	
	public final City getCityByName(final String name) {
		City c = null;

		try {
			pstmt_getCityByName.setString(1, name);
			ResultSet rs = pstmt_getCityByName.executeQuery();
			if (rs.next())
				c = new City(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public final void deleteCity(final int id) {	
		try {
			pstmt_deleteCity.setInt(1, id);
			pstmt_deleteCity.execute();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final List<City> getAllCities() {
		final List<City> cities = new ArrayList<City>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from city");

			while (rs.next()) {
				cities.add(new City(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return cities;
	}
	
	public final List<String> getAllCityNames() {
		List<String> names = new ArrayList<String>();
		for (City c : getAllCities()) {
			names.add(c.getName());
		}
		return names;
	}
}
