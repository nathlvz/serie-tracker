package mx.com.beni.serietracker.commoncatalogservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.CollectionUtils;

import mx.com.beni.serietracker.commoncatalogservice.entity.CountryEntity;

/**
 * Test class for CountryRepositoryJdbcImpl
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@ExtendWith({ MockitoExtension.class })
@DisplayName("Tests for CountryRepositoryJdbcImpl")
public class CountryRepositoryJdbcImplTest {

	/**
	 * Mock for JdbcTemplate
	 */
	@Mock
	private JdbcTemplate jdbcTemplate;

	/**
	 * Mock for GeneratedKeyHolder
	 */
	@Mock
	private KeyHolder keyHolder;

	/**
	 * The class that we need to test.
	 */
	@InjectMocks
	private CountryRepositoryJdbcImpl countryRepositoryImpl;

//	private static List<Country> COUNTRIES_EXPECTED;

//	@BeforeAll
//	static void setupMockData() {
//		CountryRepositoryImplTest.COUNTRIES_EXPECTED = new ArrayList<>();
//		COUNTRIES_EXPECTED.add(new Country(1, "Mexico", LocalDateTime.of(2023, Month.APRIL, 30, 12, 30, 12, 100)));
//	}

//	@BeforeEach
//	void setupMockTest() {
//		this.jdbcTemplate = mock(JdbcTemplate.class);
//		this.countryRepositoryImpl = new CountryRepositoryImpl(this.jdbcTemplate);
//	}

	@Nested
	@DisplayName("Test cases for [getAll()] method")
	class CasesForGetAll {

		private static final String GET_ALL_COUNTRIES = "select country_id, name, registration_date from country";

		@Test
		@SuppressWarnings("unchecked")
		@DisplayName("[Case: List with elements]")
		void test_get_all_case_list_with_elements() {
			// A-A-A Pattern
			// Arrange
			final var countriesExpected = new ArrayList<>();
			final var idExpected = 1;
			final var nameExpected = "Mexico";
			final var registrationDateExpected = LocalDateTime.of(2023, Month.APRIL, 30, 12, 30, 12, 100);
			countriesExpected.add(new CountryEntity(idExpected, nameExpected, registrationDateExpected));

			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.query(eq(GET_ALL_COUNTRIES), any(RowMapper.class)))
					.thenReturn(countriesExpected);

			// Act
			final var countriesRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.getAll();

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).query(eq(GET_ALL_COUNTRIES),
					any(RowMapper.class));

			assertThat(countriesRetrived).hasSize(1);

			final var country = CollectionUtils.firstElement(countriesRetrived);
			assertAll(() -> {
				assertEquals(idExpected, country.countryId());
			}, () -> {
				assertEquals(nameExpected, country.name());
			}, () -> {
				assertEquals(registrationDateExpected, country.registrationDate());
			});
		}

		@Test
		@SuppressWarnings("unchecked")
		@DisplayName("[Case: Empty List]")
		void test_get_all_case_empty_list() {
			
			// Arrange
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate
					.query(eq(GET_ALL_COUNTRIES),
							any(RowMapper.class))).thenReturn(Collections.emptyList());
			
			// Act
			final var countriesRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.getAll();
			
			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1))
				.query(eq(GET_ALL_COUNTRIES),
					any(RowMapper.class));
			
			assertAll(
					()->assertNotNull(countriesRetrived),
					()-> assertThat(countriesRetrived).isEmpty()
			);
		}

		@Test
		@SuppressWarnings({ "unchecked", "serial" })
		@DisplayName("[Case: Exception thrown]")
		void test_get_all_case_exception_thrown()  {
			// Arrange
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate 
					.query(eq(GET_ALL_COUNTRIES), any(RowMapper.class)))
			.thenThrow(	new DataAccessException("Error!") { });
//			.thenAnswer((InvocationOnMock invocation) -> {
//				throw new DataAccessException("Error!") { };
//			});
						
			// Act
			final var countriesRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.getAll();
						
			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).query(eq(GET_ALL_COUNTRIES), 
					any(RowMapper.class));
			assertThat(countriesRetrived).isEmpty();
		}

	}

	@Nested
	@DisplayName("Test cases for [get(final Long countryId)] method")
	class CasesForGet {

		private static final String GET_COUNTRY = "select country_id, name, registration_date from country where country_id = ?";

		@Test
		@SuppressWarnings("unchecked")
		@DisplayName("[Case: Country found]")
		void test_get_case_country_found() {
			// Arrange
			final var countryIdExpected = 1L;
			final var countryNameExpected = "Mexico";
			final var registrationDateExpected = LocalDateTime.of(2023, Month.JANUARY, 23, 10, 45, 23);
			final var countryMexico = new CountryEntity(countryIdExpected, countryNameExpected,
					registrationDateExpected);

			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.queryForObject(eq(GET_COUNTRY), any(Object[].class),
					any(int[].class), any(RowMapper.class))).thenReturn(countryMexico);

			// Act
			final var wrapperValue = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.get(1L);

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).queryForObject(eq(GET_COUNTRY),
					any(Object[].class), any(int[].class), any(RowMapper.class));

			assertAll(() -> {
				assumeTrue(wrapperValue::isPresent, "The country is null or inexistent!");
			}, () -> {
				final var value = wrapperValue.get();
				assertAll(() -> {
					assertEquals(countryIdExpected, value.countryId());
				}, () -> {
					assertEquals(countryNameExpected, value.name());
				}, () -> {
					assertEquals(registrationDateExpected, value.registrationDate());
				});
			});
		}

		@Test
		@SuppressWarnings("unchecked")
		@DisplayName("[Case: Country not found]")
		void test_get_case_country_not_found() {
			//Arrange
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.queryForObject(eq(GET_COUNTRY), any(Object[].class),
					any(int[].class), any(RowMapper.class))).thenReturn(null);
			
			// Act
			final var wrapperValue = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.get(10000L);
			
			//Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).queryForObject(eq(GET_COUNTRY), any(Object[].class),
					any(int[].class), any(RowMapper.class));
			assertTrue(wrapperValue.isEmpty());
		}

		@Test
		@SuppressWarnings({ "unchecked", "serial" })
		@DisplayName("[Case: Exception thrown]")
		void test_get_case_exception_thrown() {
			//Arrange
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.queryForObject(eq(GET_COUNTRY), any(Object[].class),
					any(int[].class), any(RowMapper.class))).thenThrow(new DataAccessException("Error!") { });
			
			// Act
			final var wrapperValue = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.get(10000L);
			
			//Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).queryForObject(eq(GET_COUNTRY), any(Object[].class),
					any(int[].class), any(RowMapper.class));
			assertTrue(wrapperValue.isEmpty());
		}
	}

	@Nested
	@DisplayName("Test cases for [count()] method")
	class CasesForCount {

		private static final String COUNT_COUNTRIES = "select count(*) from country";

		@Test
		@DisplayName("[Case: Count successful]")
		void test_count_case_count_successful() {
			// Arrange
			final var countCountriesExpected = 100L;
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.queryForObject(eq(COUNT_COUNTRIES), eq(long.class)))
					.thenReturn(countCountriesExpected);

			// Act
			final var countRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.count();

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).queryForObject(eq(COUNT_COUNTRIES),
					eq(long.class));
			assertEquals(countCountriesExpected, countRetrived, "Count of countries is incorrect!");
		}

		@Test
		@SuppressWarnings("serial")
		@DisplayName("[Case: Exception thrown]")
		void test_count_case_exception_thrown() {
			// Arrange
			final var countCountriesErrorExpected = -1;
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.queryForObject(eq(COUNT_COUNTRIES), eq(long.class)))
					.thenThrow(new DataAccessException("Error!") {
					});

			// Act
			final var countRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.count();

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).queryForObject(eq(COUNT_COUNTRIES),
					eq(long.class));
			assertEquals(countCountriesErrorExpected, countRetrived);
		}

	}

	@Nested
	@DisplayName("Test cases for [delete(final Long key)] method")
	class CasesForDelete {

		private static final String DELETE_COUNTRY = "delete from country where country_id = ?";

		@Test
		@DisplayName("[Case: Country deleted successful]")
		void test_delete_case_country_deleted_successful() {
			// Arrange
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.update(eq(DELETE_COUNTRY), 
					any(Object[].class), any(int[].class))).thenReturn(1);

			// Act
			final var countryDeleted = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.delete(10L);

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).update(eq(DELETE_COUNTRY), 
					any(Object[].class), any(int[].class));
			assertTrue(countryDeleted);
		}

		@Test
		@DisplayName("[Case: Country not deleted]")
		void test_delete_case_country_not_deleted() {
			// Arrange
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.update(eq(DELETE_COUNTRY), 
					any(Object[].class), any(int[].class))).thenReturn(0);

			// Act
			final var countryDeleted = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.delete(10L);

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).update(eq(DELETE_COUNTRY), 
					any(Object[].class), any(int[].class));
			assertFalse(countryDeleted);
		}

		@Test
		@SuppressWarnings("serial")
		@DisplayName("[Case: Exception thrown]")
		void test_delete_case_exception_thrown() {
			// Arrange
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.update(eq(DELETE_COUNTRY), 
					any(Object[].class), any(int[].class))).thenThrow(new DataAccessException("Error!") {});

			// Act
			final var countryDeleted = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.delete(10L);

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).update(eq(DELETE_COUNTRY), 
					any(Object[].class), any(int[].class));
			assertFalse(countryDeleted);
		}

		@Nested
		@DisplayName("Test cases for [insert(final Country entity)] method")
		class CasesForInsert {

			@Test
			@DisplayName("[Case: Insert new country successful]")
			void test_insert_case_insert_new_country_successful() {
				// Arrange
				final var countryIdExpected = 1L;
				final var countryNameExpected = "Mexico";
				final var registrationDateExpected = LocalDateTime.of(2023, Month.APRIL, 23, 11, 30, 23);
				final Map<String, Object> keysExpected = Map.of("country_id", countryIdExpected, "name",
						countryNameExpected, "registration_date", registrationDateExpected);
				final var country = new CountryEntity(0, countryNameExpected, registrationDateExpected);
				when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.update(any(PreparedStatementCreator.class),
						any(KeyHolder.class))).thenReturn(1);

				when(CountryRepositoryJdbcImplTest.this.keyHolder.getKeys()).thenReturn(keysExpected);

				// Act
				final var countryRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.insert(country);

				// Assert
				verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1))
						.update(any(PreparedStatementCreator.class), any(KeyHolder.class));

				verify(CountryRepositoryJdbcImplTest.this.keyHolder, times(1)).getKeys();

				assertAll(() -> {
					assertEquals(countryIdExpected, countryRetrived.countryId());
				}, () -> {
					assertEquals(countryNameExpected, countryRetrived.name());
				}, () -> {
					assertNotNull(countryRetrived.registrationDate());
				});
			}

			@Test
			@SuppressWarnings("serial")
			@DisplayName("[Case: Return null after exception thrown]")
			void test_insert_case_return_null_after_exception_thrown() {
				// Arrange
				final var country = new CountryEntity(0, "Mexico", LocalDateTime.of(2023, Month.APRIL, 23, 11, 30, 23));
				when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.update(any(PreparedStatementCreator.class),
						any(KeyHolder.class))).thenThrow(new DataAccessException("Error!") {
						});

				// Act
				final var countryRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.insert(country);

				// Assert
				verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1))
						.update(any(PreparedStatementCreator.class), any(KeyHolder.class));
				verify(CountryRepositoryJdbcImplTest.this.keyHolder, never()).getKey();
				assertNull(countryRetrived);
			}

		}
	}

	@Nested
	@DisplayName("Test cases for [update(final Country entity)] method")
	class CasesForUpdate {

		private static final String UPDATE_COUNTRY = "update country set name = ? where country_id = ?";

		@Test
		@DisplayName("[Case: Country updated successful]")
		void test_update_case_country_updated_successful() {
			// Arrange
			final var countryIdExpected = 1L;
			final var countryNameExpected = "Mexico";
			final var registrationDateExpected = LocalDateTime.of(2023, Month.APRIL, 23, 11, 30, 23);

			final var country = new CountryEntity(countryIdExpected, countryNameExpected, registrationDateExpected);
			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.update(eq(UPDATE_COUNTRY), any(Object[].class),
					any(int[].class))).thenReturn(1);

			// Act
			final var countryRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.update(country);

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).update(eq(UPDATE_COUNTRY),
					any(Object[].class), any(int[].class));

			assertAll(() -> {
				assertEquals(countryIdExpected, countryRetrived.countryId());
			}, () -> {
				assertEquals(countryNameExpected, countryRetrived.name());
			}, () -> {
				assertEquals(registrationDateExpected, countryRetrived.registrationDate());
			});
		}

		@Test
		@SuppressWarnings("serial")
		@DisplayName("[Case: Country not updated]")
		void test_update_case_country_not_updated() {
			// Arrange
			final var countryIdExpected = 1L;
			final var countryNameExpected = "Mexico";
			final var registrationDateExpected = LocalDateTime.of(2023, Month.APRIL, 23, 11, 30, 23);
			final var country = new CountryEntity(countryIdExpected, countryNameExpected, registrationDateExpected);

			when(CountryRepositoryJdbcImplTest.this.jdbcTemplate.update(eq(UPDATE_COUNTRY), any(Object[].class),
					any(int[].class))).thenThrow(new DataAccessException("Error") {
					});

			// Act
			final var countryRetrived = CountryRepositoryJdbcImplTest.this.countryRepositoryImpl.update(country);

			// Assert
			verify(CountryRepositoryJdbcImplTest.this.jdbcTemplate, times(1)).update(eq(UPDATE_COUNTRY),
					any(Object[].class), any(int[].class));
			assertNull(countryRetrived);

		}

	}

}
