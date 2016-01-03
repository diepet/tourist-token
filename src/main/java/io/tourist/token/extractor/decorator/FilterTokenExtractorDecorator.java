package io.tourist.token.extractor.decorator;

import java.util.Set;

/**
 * The filter token extractor decorator.
 */
public final class FilterTokenExtractorDecorator extends TokenExtractorDecorator {

	/** The token set. */
	private Set<String> tokenSet;

	/** The default token. */
	private String defaultToken;

	@Override
	public String decorateToken(final String token) {
		return this.tokenSet.contains(token) ? token : this.defaultToken;
	}

	/**
	 * Sets the token set. Only the tokens included in this set will be
	 * returned.
	 *
	 * @param tokenSet
	 *            the new token set
	 */
	public void setTokenSet(final Set<String> tokenSet) {
		this.tokenSet = tokenSet;
	}

	/**
	 * Sets the default token. If a token is not contained in the configured
	 * token set than the default token will be returned.
	 *
	 * @param defaultToken
	 *            the new default token
	 */
	public void setDefaultToken(final String defaultToken) {
		this.defaultToken = defaultToken;
	}

}
