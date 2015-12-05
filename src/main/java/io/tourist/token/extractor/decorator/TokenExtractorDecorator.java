package io.tourist.token.extractor.decorator;

import io.tourist.core.api.Shot;
import io.tourist.token.extractor.TokenExtractor;

/**
 * The token extractor decorator class.
 */
public abstract class TokenExtractorDecorator implements TokenExtractor {

	/** The token extractor. */
	private TokenExtractor tokenExtractor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.token.extractor.TokenExtractor#extractToken(io.tourist.core.
	 * api.Shot)
	 */
	@Override
	public String extractToken(final Shot shot) {
		return tokenExtractor.extractToken(shot);
	}

	/**
	 * Sets the token extractor.
	 *
	 * @param tokenExtractor
	 *            the new token extractor
	 */
	public final void setTokenExtractor(final TokenExtractor tokenExtractor) {
		this.tokenExtractor = tokenExtractor;
	}

}
