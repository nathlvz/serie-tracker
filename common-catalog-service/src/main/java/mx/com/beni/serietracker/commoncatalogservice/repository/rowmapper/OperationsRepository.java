package mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper;

/**
 * Segregated interface for handle transaction operations like delete, insert
 * and update.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 * @param <T>
 * @param <ID>
 */
public interface OperationsRepository<T, ID> extends Searchable<T, ID> {

	/**
	 * Insert a new record.
	 * 
	 * @param entity The record's data.
	 * @return The new record stored.
	 */
	T insert(T entity);

	/**
	 * Updates a record.
	 * 
	 * @param entity The new record's data
	 * @return The new record's data updated.
	 */
	T update(T entity);

	/**
	 * Deletes a record by its identifier.
	 * 
	 * @param key The identifier
	 * @return true if the record was deleted, else false.
	 */
	boolean delete(ID key);

}
