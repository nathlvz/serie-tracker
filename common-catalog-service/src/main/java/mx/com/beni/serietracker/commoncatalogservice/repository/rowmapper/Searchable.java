package mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper;

import java.util.List;
import java.util.Optional;

/**
 * Segregated interface, provides methods for search records in a table.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public interface Searchable<T, ID> {

	/**
	 * Return all the records from a table.
	 * 
	 * @return list of records
	 */
	List<T> getAll();

	/**
	 * Get only a specific record from a table by its key.
	 * 
	 * @param key The identifier.
	 * @return specific record filtered by its key.
	 */
	Optional<T> get(ID key);

	/**
	 * Validates if a record exists in the database.
	 * 
	 * @param key The identifier
	 * @return true if exists, else false.
	 */
	boolean exist(ID key);

}
