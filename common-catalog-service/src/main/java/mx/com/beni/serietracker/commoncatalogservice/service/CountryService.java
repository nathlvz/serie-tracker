package mx.com.beni.serietracker.commoncatalogservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.com.beni.serietracker.commoncatalogservice.model.CountryModel;
import mx.com.beni.serietracker.commoncatalogservice.model.OperationResult;

/**
 * Main contract for handle all operations of country.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public interface CountryService {

	/**
	 * Adds a new country.
	 * 
	 * @param model The country to be added.
	 * @return The result of the operation.
	 */
	OperationResult<CountryModel> addNewCountry(CountryModel model);

	/**
	 * Updates an existing country.
	 * 
	 * @param model The country to be updated.
	 * @return The result of the operation.
	 */
	OperationResult<CountryModel> updateCountry(CountryModel model);

	/**
	 * Deletes an existing country.
	 * 
	 * @param id The identifier
	 * @return The result of the operation.
	 */
	OperationResult<Void> deleteCountry(long id);

	/**
	 * Get an specific country by its identifier.
	 * 
	 * @param id The identifier
	 * @return The country filtered by its id.
	 */
	CountryModel get(long id);

	/**
	 * Get a slice of a set of countries.
	 * 
	 * @param pageRequest The specification of the slice of data.
	 * 
	 * @return A page with the slice of countries.
	 */
	Page<CountryModel> getCountriesPaginated(Pageable pageRequest);

}
