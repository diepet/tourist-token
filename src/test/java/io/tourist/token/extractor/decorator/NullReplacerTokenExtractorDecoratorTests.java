package io.tourist.token.extractor.decorator;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.Shot;
import io.tourist.token.extractor.TokenExtractor;

public class NullReplacerTokenExtractorDecoratorTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private TokenExtractor tokenExtractor;

	@Test
	public void testNotNullToken() {
		mockTokenExtractor("Some token");
		NullReplacerTokenExtractorDecorator decorator = new NullReplacerTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.setNullReplacer("Something else");
		String tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("Some token", tokenDecorated);
	}

	@Test
	public void testNullToken() {
		mockTokenExtractor(null);
		NullReplacerTokenExtractorDecorator decorator = new NullReplacerTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.setNullReplacer("Some null token replace");
		String tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("Some null token replace", tokenDecorated);
	}

	private void mockTokenExtractor(String token) {
		EasyMock.reset(this.tokenExtractor);
		EasyMock.expect(this.tokenExtractor.extractToken((Shot) EasyMock.anyObject())).andReturn(token).anyTimes();
		EasyMock.replay(this.tokenExtractor);
	}

}
