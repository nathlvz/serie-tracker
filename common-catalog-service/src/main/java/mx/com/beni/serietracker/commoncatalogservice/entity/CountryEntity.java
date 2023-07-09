package mx.com.beni.serietracker.commoncatalogservice.entity;

import java.time.LocalDateTime;

/**
 * Record that represents an entity for Country.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public record CountryEntity(
		/**
		 * The country's identifier.
		 */
		long countryId,
		/**
		 * The country's name.
		 */
		String name,
		/**
		 * The country's registration date.
		 */
		LocalDateTime registrationDate) {
}
