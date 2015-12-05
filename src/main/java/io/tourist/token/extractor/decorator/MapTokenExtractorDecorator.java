package io.tourist.token.extractor.decorator;

import java.util.Map;

/**
 * The map token extractor decorator.
 */
public final class MapTokenExtractorDecorator extends TokenExtractorDecorator {

	/** The token map. */
	private Map<String, String> tokenMap;

	/** The default token. */
	private String defaultToken;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.tourist.token.extractor.decorator.TokenExtractorDecorator#
	 * decorateToken(java.lang.String)
	 */
	@Override
	public String decorateToken(final String token) {
		final String mappedToken = this.tokenMap.get(token);
		return mappedToken != null ? mappedToken : this.defaultToken;
	}

	/**
	 * Sets the token map.
	 *
	 * @param tokenMap
	 *            the token map
	 */
	public void setTokenMap(final Map<String, String> tokenMap) {
		this.tokenMap = tokenMap;
	}

	/**
	 * Sets the default token. If a token is not mapped than the default token
	 * will be returned.
	 *
	 * @param defaultToken
	 *            the new default token
	 */
	public void setDefaultToken(final String defaultToken) {
		this.defaultToken = defaultToken;
	}

}
