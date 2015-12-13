package io.tourist.token.extractor;

import io.tourist.core.api.Shot;

/**
 * The self token extractor: the picture of the shot will be itself the token.
 */
public final class SelfTokenExtractor implements TokenExtractor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.token.extractor.TokenExtractor#extractToken(io.tourist.core.
	 * api.Shot)
	 */
	@Override
	public String extractToken(final Shot shot) {
		return shot.getPicture();
	}

}
