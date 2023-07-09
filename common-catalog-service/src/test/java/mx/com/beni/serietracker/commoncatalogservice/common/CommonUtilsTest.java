package mx.com.beni.serietracker.commoncatalogservice.common;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import lombok.extern.slf4j.Slf4j;

/**
 * Test class for CommonUtilTest
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
// @formatter:off
@Slf4j
@DisplayName("Tests for CommonUtils")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
// @formatter:on
public class CommonUtilsTest {

	// @formatter:off
	@DisplayName("Test cases for [createPaginatedQuery(final String query, " 
			+ "final String defaultOrderColumn, "
			+ "final Pageable pageable)] method")
	// @formatter:on
	@org.junit.jupiter.api.Order(1)
	@Nested
	class CasesForCreatePaginatedQueryMethod {

		private static final String QUERY = "select column_a, column_b, column_c from testing_table";

		// REMEMBER: Tests in nested classes are always executed after tests in the
		// enclosing
		// class. That cannot be changed.
		// Ordering of test methods only applies to methods within a single test class.
		@Test
		@DisplayName("[Case: default order]")
		void test_create_paginated_query_case_default_order() {
			// Arrange
			final var queryExpected = "select column_a, column_b, column_c from testing_table "
					+ "order by column_a asc limit 200 offset 0";
			final var defaultOrderColumn = "column_a";
			final var pageable = PageRequest.of(0, 200);

			// Act
			final var queryRetrived = CommonUtils.createPaginatedQuery(QUERY, defaultOrderColumn, pageable);

			// Assert
			assertEquals(queryExpected, queryRetrived);
		}

		@Test
		@DisplayName("[Case: custom order]")
		void test_create_paginated_query_case_custom_order() {
			// Arrange
			final var queryExpected = "select column_a, column_b, column_c from testing_table "
					+ "order by column_a asc, column_b desc, column_c asc limit 200 offset 400";
			final var defaultOrderColumn = "column_a";
			final var pageable = PageRequest.of(2, 200,
					Sort.by(Order.asc("column_a"), Order.desc("column_b"), Order.asc("column_c")));
			// Act
			final var queryRetrived = CommonUtils.createPaginatedQuery(QUERY, defaultOrderColumn, pageable);

			// Assert
			assertEquals(queryExpected, queryRetrived);
		}

		@ParameterizedTest
		@NullAndEmptySource
		@DisplayName("[Case: Exception thrown - param query is incorrect]")
		void test_create_paginated_query_case_query_is_null_or_empty(final String query) {
			log.info("The value of the query is: {}", query);

			// Arrange
			final var defaultOrderColumn = "column_a";
			final var pageable = PageRequest.of(2, 200,
					Sort.by(Order.asc("column_a"), Order.desc("column_b"), Order.asc("column_c")));

			// Act && Assert
			assertThatThrownBy(() -> {
				CommonUtils.createPaginatedQuery(query, defaultOrderColumn, pageable);
			}).isExactlyInstanceOf(IllegalArgumentException.class).extracting(Throwable::getMessage)
					.isEqualTo("Is necessary a query not null or empty.");
		}

		@ParameterizedTest
		@NullAndEmptySource
		@DisplayName("[Case: Exception thrown - param defaultOrderColumn is incorrect]")
		void test_create_paginated_query_case_default_order_column_is_null_or_empty(final String orderColumn) {
			log.info("The value of the defaultOrderColumn is: {}", orderColumn);

			// Arrange
			final var query = "select column_a, column_b, column_c from testing_table ";
			final var pageable = PageRequest.of(2, 200,
					Sort.by(Order.asc("column_a"), Order.desc("column_b"), Order.asc("column_c")));

			// Act && Assert
			assertThatThrownBy(() -> {
				CommonUtils.createPaginatedQuery(query, orderColumn, pageable);
			}).isExactlyInstanceOf(IllegalArgumentException.class).extracting(Throwable::getMessage)
					.isEqualTo("Is necessary that the param defaultOrderColumn not be null or empty.");
		}

		@Test
		@DisplayName("[Case: Exception thrown - param pageable is null]")
		void test_create_paginated_query_case_pageable_is_null() {
			// Arrange
			final var query = "select column_a, column_b, column_c from testing_table ";
			final var defaultOrderColumn = "column_a";

			// Act && Assert
			assertThatThrownBy(() -> {
				CommonUtils.createPaginatedQuery(query, defaultOrderColumn, null);
			}).isExactlyInstanceOf(IllegalArgumentException.class).extracting(Throwable::getMessage)
					.isEqualTo("Is necessary that the param pageable not be null.");
		}

	}

}
