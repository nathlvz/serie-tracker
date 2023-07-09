package mx.com.beni.serietracker.commoncatalogservice.exception;

import lombok.NoArgsConstructor;

/**
 * Exception that indicates that a record was not saved.
 * 
 * @author Benjamin Natanael Ocotzi Alvarez (beni)
 */
@NoArgsConstructor
public class UnregisteredRecordException extends RuntimeException {

	private static final long serialVersionUID = -5521095179752221298L;

	/**
	 * Constructs a new runtime exception with the specified detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call to
	 * {@link #initCause}.
	 *
	 * @param message the detail message.
	 */
	public UnregisteredRecordException(final String message) {
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
	public UnregisteredRecordException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
