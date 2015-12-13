package io.tourist.token.extractor;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.Shot;

public class SelfTokenExtractorTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	public Shot shot;

	@Test
	public void testRegex() {
		String token;

		// initialize token extractor
		SelfTokenExtractor tokenExtractor = new SelfTokenExtractor();

		// mock shots and test token extractor
		mockShot("some shot");
		token = tokenExtractor.extractToken(this.shot);
		Assert.assertEquals("some shot", token);

	}

	private void mockShot(String picture) {
		EasyMock.reset(this.shot);
		EasyMock.expect(this.shot.getPicture()).andReturn(picture).anyTimes();
		EasyMock.replay(this.shot);
	}
}
