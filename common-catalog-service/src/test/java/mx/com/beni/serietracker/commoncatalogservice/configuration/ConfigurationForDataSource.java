package mx.com.beni.serietracker.commoncatalogservice.configuration;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
@TestConfiguration
public class ConfigurationForDataSource {

	// @formatter:off
	@Bean
	DataSource datasourceH2() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.generateUniqueName(false)
				.setName("testing-common-catalog-db")
				.addScript("classpath:scripts-common-catalog-db/001-ddl-country.sql")
				.addScript("classpath:scripts-common-catalog-db/001-dml-country.sql")
				.build();
	}
	// @formatter:on

//	// @formatter:off
//	@Bean
//	HikariConfig hikariConfig() {
//		final var hikariConfig = new HikariConfig();
//		hikariConfig.setJdbcUrl("jdbc:h2:mem:test-common-catalog-db" 
//				+ ";MODE=PostgreSQL" 
//				+ ";DATABASE_TO_LOWER=TRUE"
//				+ ";DEFAULT_NULL_ORDERING=HIGH" 
//				+ ";DB_CLOSE_DELAY=2" 
//				+ ";DB_CLOSE_ON_EXIT=FALSE");
//		hikariConfig.setUsername("usrtesting");
//		hikariConfig.setPassword("passwordtesting");
//		hikariConfig.setDriverClassName("org.h2.Driver");
//		return hikariConfig;
//	}
//	// @formatter:on
//
//	@Bean
//	DataSource dataSource(final HikariConfig hikariConfig) {
//		return new HikariDataSource(hikariConfig);
//	}

	@Bean
	JdbcTemplate jdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

//
	@Bean
	PlatformTransactionManager transactionManager(final DataSource dataSource) {
		return new JdbcTransactionManager(dataSource);
	}

}
