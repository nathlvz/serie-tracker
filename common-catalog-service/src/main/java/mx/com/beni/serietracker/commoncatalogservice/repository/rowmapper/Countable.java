package mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper;

/**
 * Segregated interface, only provides a count method for get the total of
 * records in a table.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public interface Countable {

	/**
	 * Counts the total of records.
	 * 
	 * @return total of records.
	 */
	long count();

}
