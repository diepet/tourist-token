package io.tourist.token.extractor.decorator;

/**
 * The upper case token extractor decorator.
 */
public final class UpperTokenExtractorDecorator extends TokenExtractorDecorator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.tourist.token.extractor.decorator.TokenExtractorDecorator#
	 * decorateToken(java.lang.String)
	 */
	@Override
	public String decorateToken(final String token) {
		return token.toUpperCase();
	}
}
