package io.tourist.token.extractor;

import java.util.regex.Pattern;

import io.tourist.core.api.Shot;

public final class RegexTokenExtractor implements TokenExtractor {

	private String regex;

	private Pattern pattern;

	@Override
	public String extractToken(Shot shot) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRegex(String regex) {
		this.regex = regex;
		this.pattern = Pattern.compile(regex);
	}

}
