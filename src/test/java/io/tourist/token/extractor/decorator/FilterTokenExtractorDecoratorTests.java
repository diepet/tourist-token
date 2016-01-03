package io.tourist.token.extractor.decorator;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.Shot;
import io.tourist.token.extractor.TokenExtractor;

public class FilterTokenExtractorDecoratorTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private TokenExtractor tokenExtractor;

	@Test
	public void testSet() {
		// initalize set
		Set<String> tokenSet = new HashSet<String>();
		tokenSet.add("A");
		tokenSet.add("B");
		tokenSet.add("C");

		// initialize decorator
		FilterTokenExtractorDecorator decorator = new FilterTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.setTokenSet(tokenSet);
		String tokenDecorated;

		// test tokens
		mockTokenExtractor("A");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("A", tokenDecorated);

		mockTokenExtractor("B");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("B", tokenDecorated);

		mockTokenExtractor("C");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("C", tokenDecorated);

		mockTokenExtractor("D");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertNull(tokenDecorated);

	}

	@Test
	public void testSetWithDefaultToken() {
		// initalize set
		Set<String> tokenSet = new HashSet<String>();
		tokenSet.add("A");
		tokenSet.add("B");
		tokenSet.add("C");

		// initialize decorator
		FilterTokenExtractorDecorator decorator = new FilterTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.setTokenSet(tokenSet);
		decorator.setDefaultToken("DEFAULT");
		String tokenDecorated;

		// test tokens
		mockTokenExtractor("A");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("A", tokenDecorated);

		mockTokenExtractor("B");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("B", tokenDecorated);

		mockTokenExtractor("C");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("C", tokenDecorated);

		mockTokenExtractor("D");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("DEFAULT", tokenDecorated);

	}

	@Test(expected = NullPointerException.class)
	public void testNullSet() {

		// initialize decorator
		FilterTokenExtractorDecorator decorator = new FilterTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.setDefaultToken("DEFAULT");

		// test tokens
		mockTokenExtractor("A");
		decorator.extractToken(null);
		Assert.fail("Should not execute here, an exception should be raised before");

	}

	private void mockTokenExtractor(String token) {
		EasyMock.reset(this.tokenExtractor);
		EasyMock.expect(this.tokenExtractor.extractToken((Shot) EasyMock.anyObject())).andReturn(token).anyTimes();
		EasyMock.replay(this.tokenExtractor);
	}

}
