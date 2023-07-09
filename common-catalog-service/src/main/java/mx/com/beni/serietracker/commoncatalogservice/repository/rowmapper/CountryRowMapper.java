package mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.com.beni.serietracker.commoncatalogservice.entity.CountryEntity;

/**
 * Implementation of RowMapper for map Country Entities.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public class CountryRowMapper implements RowMapper<CountryEntity> {

	/**
	 * The column's name country_id
	 */
	private static final String COLUM_COUNTRY_ID = "country_id";

	/**
	 * The column's name name
	 */
	private static final String COLUM_NAME = "name";

	/**
	 * The column's name registration_date
	 */
	private static final String COLUM_REGISTRATION_DATE = "registration_date";

	/**
	 * Method overrided that perform the map extracting the information of the
	 * columns stored in ResultSet object and put them in an new Record of type
	 * Country.
	 */
	// @formatter:off
	@Override
	public CountryEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new CountryEntity(resultSet.getLong(COLUM_COUNTRY_ID), 
				resultSet.getString(COLUM_NAME),
				resultSet.getTimestamp(COLUM_REGISTRATION_DATE)
					.toLocalDateTime());
	}
	// @formatter:on

}
