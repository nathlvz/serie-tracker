package mx.com.beni.serietracker.commoncatalogservice.repository.rowmapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Segregated interface, provides only one method for paginate records from a
 * table.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public interface PaginatedRecord<T> {

	/**
	 * Get a slice of a set of records from a table.
	 * 
	 * @param pageRequest The specification of how the data is paginated.
	 * @return a page with the slice of data.
	 */
	Page<T> getPage(Pageable pageRequest);
}
