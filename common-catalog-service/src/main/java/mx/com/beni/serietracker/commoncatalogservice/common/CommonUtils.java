package mx.com.beni.serietracker.commoncatalogservice.common;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * An utility class with many constants with common messages for info logs,
 * error logs, etc, this class contains many utility methods.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@Slf4j
@UtilityClass
public class CommonUtils {

	/**
	 * Utility method for create paginated queries, return a new sorted and
	 * paginated query.
	 * 
	 * @param query              The principal query that will be paginated.
	 * @param defaultOrderColumn The default column with a default order (asc by
	 *                           default).
	 * @param pageable           The request that contains how many results will be
	 *                           shown, the sort and the columns.
	 * @throws IllegalArgumentException if any of the parameters is null or empty.
	 * @return a new paginated query
	 */
	public static String createPaginatedQuery(final String query, final String defaultOrderColumn,
			final Pageable pageable) {
		if (!StringUtils.hasLength(query)) {
			throw new IllegalArgumentException("Is necessary a query not null or empty.");
		}
		if (!StringUtils.hasLength(defaultOrderColumn)) {
			throw new IllegalArgumentException("Is necessary that the param defaultOrderColumn not be null or empty.");
		}
		if (Objects.isNull(pageable)) {
			throw new IllegalArgumentException("Is necessary that the param pageable not be null.");
		}
		var paginatedQuery = new StringBuilder(query).append(" order by ");
		final var sort = pageable.getSort();
		final var pageSize = pageable.getPageSize();
		final var offset = pageable.getOffset();
		if (pageable.getSort().isEmpty()) {
			paginatedQuery.append(defaultOrderColumn).append(" asc ");
		} else {
			final var sortSql = sort.stream().map((final var order) -> {
				return order.getProperty() + " " + order.getDirection().name().toLowerCase();
			}).collect(Collectors.joining(", "));
			paginatedQuery.append(sortSql).append(" ");
		}
		final var finalQuery = paginatedQuery.append("limit ").append(pageSize).append(" offset ").append(offset)
				.toString();
		log.debug("The created paginated query is: {}", finalQuery);
		return finalQuery;
	}

}
