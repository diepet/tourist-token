package io.tourist.token.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.tourist.core.api.Shot;

/**
 * The regex token extractor class.
 */
public final class RegexTokenExtractor implements TokenExtractor {

	/** The default group number of the regex used to extract the token. */
	private static final int DEFAULT_GROUP = 1;

	/** The regex pattern. */
	private Pattern pattern;

	/** The group number of the regex used to extract the token. */
	private int group = DEFAULT_GROUP;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.token.extractor.TokenExtractor#extractToken(io.tourist.core.
	 * api.Shot)
	 */
	@Override
	public String extractToken(final Shot shot) {
		final Matcher matcher = this.pattern.matcher(shot.getPicture());
		String token = null;
		if (matcher.matches()) {
			token = matcher.group(this.group);
		}
		return token;
	}

	/**
	 * Sets the regex.
	 *
	 * @param regex
	 *            the new regex
	 */
	public void setRegex(final String regex) {
		this.pattern = Pattern.compile(regex);
	}

	/**
	 * Sets the group number of the regex used to extract the token.
	 *
	 * @param group
	 *            the new group number of the regex used to extract the token
	 */
	public void setGroup(final int group) {
		this.group = group;
	}

}
