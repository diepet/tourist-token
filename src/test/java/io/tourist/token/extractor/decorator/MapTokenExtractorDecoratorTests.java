package io.tourist.token.extractor.decorator;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.Shot;
import io.tourist.token.extractor.TokenExtractor;

public class MapTokenExtractorDecoratorTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private TokenExtractor tokenExtractor;

	@Test
	public void testMap() {
		// initalize map
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("A", "MAPPED A");
		tokenMap.put("B", "MAPPED B");
		tokenMap.put("C", "MAPPED C");

		// initialize decorator
		MapTokenExtractorDecorator decorator = new MapTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.setTokenMap(tokenMap);
		String tokenDecorated;

		// test tokens
		mockTokenExtractor("A");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("MAPPED A", tokenDecorated);

		mockTokenExtractor("B");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("MAPPED B", tokenDecorated);

		mockTokenExtractor("C");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("MAPPED C", tokenDecorated);

		mockTokenExtractor("D");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertNull(tokenDecorated);

	}

	@Test
	public void testMapWithDefaultToken() {
		// initalize map
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("A", "MAPPED A");
		tokenMap.put("B", "MAPPED B");
		tokenMap.put("C", "MAPPED C");

		// initialize decorator
		MapTokenExtractorDecorator decorator = new MapTokenExtractorDecorator();
		decorator.setTokenExtractor(this.tokenExtractor);
		decorator.setTokenMap(tokenMap);
		decorator.setDefaultToken("DEFAULT");
		String tokenDecorated;

		// test tokens
		mockTokenExtractor("A");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("MAPPED A", tokenDecorated);

		mockTokenExtractor("B");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("MAPPED B", tokenDecorated);

		mockTokenExtractor("C");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("MAPPED C", tokenDecorated);

		mockTokenExtractor("D");
		tokenDecorated = decorator.extractToken(null);
		Assert.assertEquals("DEFAULT", tokenDecorated);

	}

	@Test(expected = NullPointerException.class)
	public void testNullMap() {

		// initialize decorator
		MapTokenExtractorDecorator decorator = new MapTokenExtractorDecorator();
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
