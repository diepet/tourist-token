package io.tourist.token.extractor.decorator;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.Shot;
import io.tourist.token.extractor.TokenExtractor;

public class LowerTokenExtractorDecoratorTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private TokenExtractor tokenExtractor;

	@Test
	public void testLower() {
		mockTokenExtractor("SOMEtoken");
		LowerTokenExtractorDecorator decorator = new LowerTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		String tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("sometoken", tokenDecorated);
	}

	@Test(expected = NullPointerException.class)
	public void testLowerNull() {
		mockTokenExtractor(null);
		LowerTokenExtractorDecorator decorator = new LowerTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.extractToken(null);
		Assert.fail("Should not execute this");
	}

	private void mockTokenExtractor(String token) {
		EasyMock.reset(this.tokenExtractor);
		EasyMock.expect(this.tokenExtractor.extractToken((Shot) EasyMock.anyObject())).andReturn(token).anyTimes();
		EasyMock.replay(this.tokenExtractor);
	}

}
