package io.tourist.token.extractor;

import java.util.regex.PatternSyntaxException;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.Shot;

public class RegexTokenExtractorTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	public Shot shot;

	@Test
	public void testRegex() {
		String token;

		// initialize token extractor
		RegexTokenExtractor tokenExtractor = new RegexTokenExtractor();
		tokenExtractor.setRegex("aa (\\w+) cc");

		// mock shots and test token extractor
		mockShot("aa xxxx cc");
		token = tokenExtractor.extractToken(this.shot);
		Assert.assertEquals("xxxx", token);

		mockShot("aa xxxx cd");
		token = tokenExtractor.extractToken(this.shot);
		Assert.assertNull(token);

	}

	@Test
	public void testRegexChangingGroup() {
		String token;

		// initialize token extractor
		RegexTokenExtractor tokenExtractor = new RegexTokenExtractor();
		tokenExtractor.setGroup(2);
		tokenExtractor.setRegex("aa (\\w+) (\\w+) dd");

		// mock shots and test token extractor
		mockShot("aa xxxx cc dd");
		token = tokenExtractor.extractToken(this.shot);
		Assert.assertEquals("cc", token);

		mockShot("aa xxxx cc df");
		token = tokenExtractor.extractToken(this.shot);
		Assert.assertNull(token);

	}

	@Test(expected = PatternSyntaxException.class)
	public void testInvalidRegex() {
		RegexTokenExtractor tokenExtractor = new RegexTokenExtractor();
		tokenExtractor.setRegex("[");
		Assert.fail("Should not execute here, an exception should be raised before");
	}

	private void mockShot(String picture) {
		EasyMock.reset(this.shot);
		EasyMock.expect(this.shot.getPicture()).andReturn(picture).anyTimes();
		EasyMock.replay(this.shot);
	}
}
