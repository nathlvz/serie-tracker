package mx.com.beni.serietracker.commoncatalogservice.configuration;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Common beans for application, utility beans, complementary configuration,etc.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@Configuration
public class ConfigurationForApplication {

	/**
	 * Main bean that get the returning keys after a transaction.
	 * 
	 * @return a new instance of KeyHolder.
	 */
	@Bean
	@Scope(value = SCOPE_PROTOTYPE)
	KeyHolder keyHolder() {
		return new GeneratedKeyHolder();
	}

}
