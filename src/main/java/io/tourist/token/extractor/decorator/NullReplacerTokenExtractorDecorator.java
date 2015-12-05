package io.tourist.token.extractor.decorator;

/**
 * The null replace token extractor decorator.
 */
public class NullReplacerTokenExtractorDecorator extends TokenExtractorDecorator {

	/** The Constant DEFAULT_NULL_REPLACER. */
	private static final String DEFAULT_NULL_REPLACER = "";

	/** The replace null with. */
	private String nullReplacer = DEFAULT_NULL_REPLACER;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.tourist.token.extractor.decorator.TokenExtractorDecorator#
	 * decorateToken(java.lang.String)
	 */
	@Override
	public final String decorateToken(final String token) {
		return token != null ? token : this.nullReplacer;
	}

	/**
	 * Sets the string that will be replaced if token is null.
	 *
	 * @param nullReplacer
	 *            the string that will be replaced if token is null
	 */
	public final void setNullReplacer(final String nullReplacer) {
		this.nullReplacer = nullReplacer;
	}

}
