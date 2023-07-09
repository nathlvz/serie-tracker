package mx.com.beni.serietracker.commoncatalogservice.model;

/**
 * Model that represents the response if an operation was done successful or
 * not.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public record OperationResult<T>(
		/**
		 * The message of the operation.
		 */
		String message,

		/**
		 * Indicates if the operation was done successful or not.
		 */
		boolean operationSuccessful,

		/**
		 * The operation's result.
		 */
		T payload) {

}
