package mx.com.beni.serietracker.commoncatalogservice.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model that represents a country, this object is hadle by all business
 * operations.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CountryModel {

	/**
	 * The country's identifier.
	 */
	@Positive(message = "The countryId can not be 0 or negative.", groups = Update.class)
	private long countryId;

	/**
	 * The country's name.
	 */
	@Size(min = 1, max = 200, message = "The name can not be more than 200 characters", groups = { Add.class, Update.class })
	@NotEmpty(message = "The name can not be null or empty.", groups = { Add.class, Update.class })
	private String name;

	/**
	 * The country's registration date.
	 */
	private LocalDateTime registrationDate;

	public static interface Add {
	}

	public static interface Update {
	}

}
