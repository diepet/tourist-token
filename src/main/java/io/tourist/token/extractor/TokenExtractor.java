package io.tourist.token.extractor;

import io.tourist.core.api.Shot;

/**
 * The TokenExtractor interface.
 */
public interface TokenExtractor {

	/**
	 * Extract token.
	 *
	 * @param shot
	 *            the shot where the token will be extracted
	 * @return the extracted token
	 */
	String extractToken(Shot shot);

}
