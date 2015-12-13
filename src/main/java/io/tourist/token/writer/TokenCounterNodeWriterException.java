package io.tourist.token.writer;

/**
 * The token counter node writer exception.
 */
public class TokenCounterNodeWriterException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4814872698632031770L;

	/**
	 * Instantiates a new token counter node writer exception.
	 */
	public TokenCounterNodeWriterException() {
		super();
	}

	/**
	 * Instantiates a new token counter node writer exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public TokenCounterNodeWriterException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new token counter node writer exception.
	 *
	 * @param message
	 *            the message
	 */
	public TokenCounterNodeWriterException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new token counter node writer exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public TokenCounterNodeWriterException(final Throwable cause) {
		super(cause);
	}

}
