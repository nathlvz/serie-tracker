package mx.com.beni.serietracker.commoncatalogservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import lombok.extern.slf4j.Slf4j;
import mx.com.beni.serietracker.commoncatalogservice.configuration.ConfigurationForApplication;
import mx.com.beni.serietracker.commoncatalogservice.configuration.ConfigurationForDataSource;
import mx.com.beni.serietracker.commoncatalogservice.entity.CountryEntity;

/**
 * Integration test for the class CountryRepositoryJdbcImpl
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */

// @formatter:off
@ActiveProfiles(profiles = { "jdbc:jdbctemplate" })
@Slf4j
//@ExtendWith({ SpringExtension.class })
//@ContextConfiguration(classes = { ConfigurationForApplication.class, ConfigurationForDataSource.class, CountryRepositoryJdbcImpl.class })
//@SpringBootTest
@SpringJUnitConfig(classes = { 
		ConfigurationForApplication.class, 
		ConfigurationForDataSource.class,
		CountryRepositoryJdbcImpl.class 
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @formatter:on
public class CountryRepositoryJdbcImplIntTest {

	private static final int TOTAL_OF_COUNTRIES_EXPECTED = 252;

	@Autowired
	private CountryRepositoryJdbcImpl countryRepositoryImpl;

//	@MockBean
//	private JdbcTemplate jdbcTemplate;

//	@Test
//	void test_get_all_countries_successful_case() {
//		CountryRepositoryImpl repository = new CountryRepositoryImpl(jdbcTemplate);
//		when(this.jdbcTemplate.query(eq("select country_id, name, registration_date from country"),
//				any(RowMapper.class))).thenReturn(Collections.emptyList());
//		
//		assertThat(repository.getAllCountries()).size().isEqualTo(0);
//	}

	@Test
	@org.junit.jupiter.api.Order(1)
//	void test_get_all_countries_successful_case(@Autowired CountryRepositoryImpl countryRepositoryImpl) {
	void test_get_all_successful_case() {
		final var countries = this.countryRepositoryImpl.getAll();
		log.info("Total of countries retrieved: {}", countries.size());
		assertAll(() -> {
			assertThat(countries).isExactlyInstanceOf(ArrayList.class);
		}, () -> {
			assertEquals(TOTAL_OF_COUNTRIES_EXPECTED, countries.size(), "Incorrect size of countries!");
		});
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void test_get_page_first_page_limit_5() throws Exception {
		final var pageRequest = PageRequest.of(0, 3, Sort.by(Order.desc("name")));
		final var page = this.countryRepositoryImpl.getPage(pageRequest);
		final var countries = page.getContent();

		assertTrue(countries.size() == 3);
		assertAll(() -> {
			assertEquals(3, page.getSize());
		}, () -> {
			final var zimbabwe = countries.get(0);
			assertEquals(252, zimbabwe.countryId());
			assertEquals("Zimbabwe", zimbabwe.name());
			assertNotNull(zimbabwe.registrationDate());
		}, () -> {
			final var zambia = countries.get(1);
			assertEquals(251, zambia.countryId());
			assertEquals("Zambia", zambia.name());
			assertNotNull(zambia.registrationDate());
		}, () -> {
			final var yemen = countries.get(2);
			assertEquals(250, yemen.countryId());
			assertEquals("Yemen", yemen.name());
			assertNotNull(yemen.registrationDate());
		});
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void test_insert_new_country() {
		final var countryIdExpected = TOTAL_OF_COUNTRIES_EXPECTED + 1;
		final var countryNameExpected = "New country";
		final var newCountry = new CountryEntity(0, countryNameExpected, null);
		final var countryRetrived = this.countryRepositoryImpl.insert(newCountry);
		assertAll(() -> {
			assertNotNull(countryRetrived);
		}, () -> {
			assertEquals(countryIdExpected, countryRetrived.countryId());
		}, () -> {
			assertEquals(countryNameExpected, countryRetrived.name());
		}, () -> {
			assertNotNull(countryRetrived.registrationDate());
		});
	}

//	@Configuration
//	public static class ConfigurationForDatasource {
//
//		// @formatter:off
//		@Bean
//		HikariConfig hikariConfig() {
//			final var hikariConfig = new HikariConfig();
//			hikariConfig.setJdbcUrl(
//					"jdbc:h2:mem:test-common-catalog-db"
//					+ ";MODE=PostgreSQL"
//					+ ";DATABASE_TO_LOWER=TRUE"
//					+ ";DEFAULT_NULL_ORDERING=HIGH"
//					+ ";DB_CLOSE_DELAY=2"
//					+ ";DB_CLOSE_ON_EXIT=FALSE");
//			hikariConfig.setUsername("usrtesting");
//			hikariConfig.setPassword("passwordtesting");
//			hikariConfig.setDriverClassName("org.h2.Driver");
//			return hikariConfig;
//		}
//		// @formatter:on
//
//		@Bean
//		DataSource dataSource(final HikariConfig hikariConfig) {
//			return new HikariDataSource(hikariConfig);
//		}
//
//		@Bean
//		JdbcTemplate jdbcTemplate(final DataSource dataSource) {
//			return new JdbcTemplate(dataSource);
//		}
//
//		@Bean
//		PlatformTransactionManager transactionManager(final DataSource dataSource) {
//			return new JdbcTransactionManager(dataSource);
//		}
//
//		@Bean
//		DataSource dataSource() {
//			return new HikariDataSource(this.hikariConfig());
//		}
//
//		@Bean
//		PlatformTransactionManager transactionManager()  {
//			return new JdbcTransactionManager(this.dataSource());
//		}
//
//		@Bean
//		JdbcTemplate jdbcTemplate() {
//			return new JdbcTemplate(this.dataSource());
//		}
//	}

}
