package mx.com.beni.serietracker.commoncatalogservice.repository;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.com.beni.serietracker.commoncatalogservice.common.CommonUtils;
import mx.com.beni.serietracker.commoncatalogservice.entity.CountryEntity;
import mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper.CountryRowMapper;

/**
 * Implementation of repository class that handle all about country table,
 * perform operations like getAll, insert, update, delete, etc using the JDBC
 * API.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@Slf4j
@Profile(value = "jdbc:jdbctemplate")
@Repository
@RequiredArgsConstructor
public class CountryRepositoryJdbcImpl implements CountryRepository {

	/**
	 * Necessary dependency for handle all queries and transactions that we need.
	 */
	private final JdbcTemplate jdbcTemplate;

	/**
	 * Object that captures the returning keys.
	 */
	private final KeyHolder keyHolder;

	/**
	 * Constant that indicates a query for get all countries.
	 */
	private static final String GET_ALL_COUNTRIES = "select country_id, name, registration_date from country";

	/**
	 * Constant that indicates a query for get an specific country.
	 */
	private static final String GET_COUNTRY = "select country_id, name, registration_date from country where country_id = ?";

	/**
	 * Constant that indicates a query for count all the countries.
	 */
	private static final String COUNT_COUNTRIES = "select count(*) from country";

	/**
	 * Constant that indicates a query for check the existence of a specific
	 * country.
	 */
	private static final String EXISTS_COUNTRY = "select count(*) from country where country_id = ?";

	/**
	 * Constant that indicates a statement for delete a specific country.
	 */
	private static final String DELETE_COUNTRY = "delete from country where country_id = ?";

	/**
	 * Constant that indicates a statement for insert a new country.
	 */
	private static final String INSERT_COUNTRY = "insert into country (name, registration_date) values (?, ?)";

	/**
	 * Constant that indicates a statement for update an existing country.
	 */
	private static final String UPDATE_COUNTRY = "update country set name = ? where country_id = ?";

	/**
	 * Error message that will be thrown when a problem occurs when consulting all
	 * countries.
	 */
	private static final String ERROR_MESSAGE_GET_ALL = "A problem occurred when consulting all countries, cause: {}";

	/**
	 * Error message that will be thrown when a problem occurs when consulting an
	 * specific country.
	 */
	private static final String ERROR_MESSAGE_GET = "A problem occurred when consulting an specific country, cause: {}";

	/**
	 * Error message that will be thrown when a problem occurs when count all
	 * countries.
	 */
	private static final String ERROR_MESSAGE_COUNT = "A problem occurred when count all countries, cause: {}";

	/**
	 * Error message that will be thrown when a problem occurs when check the
	 * existence of a country.
	 */
	private static final String ERROR_MESSAGE_EXISTS = "A problem occurred when check the existence of a country, cause: {}";

	/**
	 * Error message that will be thrown when a problem occurs when get a paginated
	 * records of countries.
	 */
	private static final String ERROR_MESSAGE_GET_PAGE = "A problem occurred when get a page of paginated records of countries, cause: {}";

	/**
	 * Error message that will be thrown when a problem occurs when deletes an
	 * specific country.
	 */
	private static final String ERROR_MESSAGE_DELETE = "A problem occurred when deletes an specific country, cause: {}";

	/**
	 * Error message that will be thrown when a problem occurs when insert a new
	 * country.
	 */
	private static final String ERROR_MESSAGE_INSERT = "A problem occurred when insert a new country, cause: {}";

	/**
	 * Error message that will be thrown when a problem occurs when updates a
	 * country.
	 */
	private static final String ERROR_MESSAGE_UPDATE = "A problem occurred when updates a country, cause: {}";

	/**
	 * Get all countries in the database without any filter or condition.
	 */
	@Override
	public List<CountryEntity> getAll() {
		try {
			return this.jdbcTemplate.query(GET_ALL_COUNTRIES, new CountryRowMapper());
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_GET_ALL, exception);
			return Collections.emptyList();
		}
	}

	/**
	 * Get the country specified by its country identifier
	 * 
	 * @param countryId The country identifier.
	 * @return An optional with the country.
	 */
	@Override
	public Optional<CountryEntity> get(final Long countryId) {
		try {
			return Optional.ofNullable(this.jdbcTemplate.queryForObject(GET_COUNTRY, new Object[] {countryId},
					new int[] { Types.BIGINT }, new CountryRowMapper()));
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_GET, exception);
			return Optional.empty();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		try {
			return this.jdbcTemplate.queryForObject(COUNT_COUNTRIES, long.class);
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_COUNT, exception);
			return -1L;
		}
	}

	/**
	 * Get paginated countries in the database.
	 * 
	 * @param pageRequest The configuration for paginate the search.
	 * @return A page with a custom total of countries.
	 */
	@Override
	public Page<CountryEntity> getPage(final Pageable pageRequest) {
		try {
			var paginatedQuery = CommonUtils.createPaginatedQuery(GET_ALL_COUNTRIES, "country_id", pageRequest);
			final var countries = this.jdbcTemplate.query(paginatedQuery, new CountryRowMapper());
			return new PageImpl<>(countries);
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_GET_PAGE, exception);
			return new PageImpl<>(Collections.emptyList());
		}
	}

	/**
	 * Delete a country by its key(id).
	 */
	@Override
	public boolean delete(final Long key) {
		try {
			return this.jdbcTemplate.update(DELETE_COUNTRY, new Object[] { key }, new int[] { Types.BIGINT }) > 0;
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_DELETE, exception);
			return false;
		}
	}

	/**
	 * Save a new country record.
	 */
	@Override
	public CountryEntity insert(final CountryEntity entity) {
		try {
			final var registrationDate = Timestamp.valueOf(LocalDateTime.now());
			this.jdbcTemplate.update((final Connection con) -> {
				final var preparedStatement = con.prepareStatement(INSERT_COUNTRY, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, entity.name());
				preparedStatement.setTimestamp(2, registrationDate);
				return preparedStatement;
			}, this.keyHolder);
			final var keys = this.keyHolder.getKeys();
			return new CountryEntity((Long) keys.get("country_id"), entity.name(), registrationDate.toLocalDateTime());
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_INSERT, exception);
			return null;
		}
	}

	/**
	 * Update a new country record.
	 */
	@Override
	public CountryEntity update(final CountryEntity entity) {
		try {
			this.jdbcTemplate.update(UPDATE_COUNTRY, new Object[] { entity.name(), entity.countryId() },
					new int[] { Types.VARCHAR, Types.BIGINT });
			return new CountryEntity(entity.countryId(), entity.name(), entity.registrationDate());
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_UPDATE, exception);
			return null;
		}
	}

	@Override
	public boolean exist(final Long key) {
		try {
			return this.jdbcTemplate.queryForObject(EXISTS_COUNTRY, new Object[] {key}, new int[] {Types.BIGINT}, Long.class) == 1;
		} catch (DataAccessException exception) {
			log.error(ERROR_MESSAGE_EXISTS);
			return false;
		}
	}

}
