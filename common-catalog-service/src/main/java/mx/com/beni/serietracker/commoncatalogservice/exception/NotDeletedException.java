package mx.com.beni.serietracker.commoncatalogservice.exception;

/**
 * Exception that indicates that a record was not deleted.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
public class NotDeletedException extends RuntimeException {

	private static final long serialVersionUID = 9072910657662655128L;

	/**
	 * Constructs a new runtime exception with the specified detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message the detail message.
	 */
	public NotDeletedException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message and
	 * cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is <i>not</i>
	 * automatically incorporated in this runtime exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval by the
	 *                {@link #getMessage()} method).
	 * 
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method). (A {@code null} value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 */
	public NotDeletedException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
