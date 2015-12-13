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
	public TokenCounterNodeWriterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new token counter node writer exception.
	 *
	 * @param message
	 *            the message
	 */
	public TokenCounterNodeWriterException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new token counter node writer exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public TokenCounterNodeWriterException(Throwable cause) {
		super(cause);
	}

}
