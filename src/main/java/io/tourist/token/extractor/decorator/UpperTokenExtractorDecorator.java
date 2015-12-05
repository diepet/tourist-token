package io.tourist.token.extractor.decorator;

import io.tourist.core.api.Shot;

/**
 * The upper case token extractor decorator.
 */
public final class UpperTokenExtractorDecorator extends TokenExtractorDecorator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.token.extractor.decorator.TokenExtractorDecorator#extractToken
	 * (io.tourist.core.api.Shot)
	 */
	@Override
	public String extractToken(final Shot shot) {
		final String token = super.extractToken(shot);
		return token != null ? token.toUpperCase() : null;
	}
}