package io.tourist.token.serializer;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.core.api.Tour;
import io.tourist.token.model.MutableInteger;
import io.tourist.token.model.TokenCounterNode;

public class TextTokenCounterNodeSerializerTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private ProceedingJoinPoint proceedingJoinPointDummyMethod;

	@Mock
	private Signature signatureDummyMethod;

	@Mock
	private Tour tourDummyMethod;

	@Mock
	private ProceedingJoinPoint proceedingJoinPointInnerDummyMethod;

	@Mock
	private Signature signatureInnerDummyMethod;

	@Mock
	private Tour tourInnerDummyMethod;

	@Test
	public void testSingleMethod() {

		// init mocks
		initMocksSingleMethod(null);

		// init tree nodes
		TokenCounterNode root = new TokenCounterNode();
		Map<String, MutableInteger> rootTokenMap = new LinkedHashMap<String, MutableInteger>();
		rootTokenMap.put("select", new MutableInteger(5));
		rootTokenMap.put("insert", new MutableInteger(8));
		root.setTokenCountedMap(rootTokenMap);
		root.setTour(tourDummyMethod);

		// serialize root node
		TextTokenCounterNodeSerializer serializer = new TextTokenCounterNodeSerializer();
		String rootSerialized = (String) serializer.serialize(root);

		// assertions
		String[] lines = rootSerialized.split(String.format("%n"));
		Assert.assertNotNull(lines);
		Assert.assertEquals(1, lines.length);
		Assert.assertEquals("[some.package.SomeClass.dummyMethod()][select=5, insert=8][1245 ms]", lines[0]);
	}

	@Test
	public void testSingleMethodFailure() {

		// init mocks
		initMocksSingleMethod(new RuntimeException());

		// init tree nodes
		TokenCounterNode root = new TokenCounterNode();
		Map<String, MutableInteger> rootTokenMap = new LinkedHashMap<String, MutableInteger>();
		rootTokenMap.put("select", new MutableInteger(5));
		rootTokenMap.put("insert", new MutableInteger(8));
		root.setTokenCountedMap(rootTokenMap);
		root.setTour(tourDummyMethod);

		// serialize root node
		TextTokenCounterNodeSerializer serializer = new TextTokenCounterNodeSerializer();
		String rootSerialized = (String) serializer.serialize(root);

		// assertions
		String[] lines = rootSerialized.split(String.format("%n"));
		Assert.assertNotNull(lines);
		Assert.assertEquals(1, lines.length);
		Assert.assertEquals("[some.package.SomeClass.dummyMethod()][java.lang.RuntimeException]", lines[0]);
	}

	@Test
	public void testInnerMethod() {

		// init mocks
		initMocksInnerMethod(null, null);

		// init tree nodes
		TokenCounterNode root = new TokenCounterNode();
		Map<String, MutableInteger> tokenMap = new LinkedHashMap<String, MutableInteger>();
		tokenMap.put("select", new MutableInteger(5));
		tokenMap.put("insert", new MutableInteger(8));
		root.setTokenCountedMap(tokenMap);
		root.setTour(tourDummyMethod);

		TokenCounterNode firstChild = new TokenCounterNode();
		tokenMap = new LinkedHashMap<String, MutableInteger>();
		tokenMap.put("select", new MutableInteger(9));
		tokenMap.put("update", new MutableInteger(3));
		firstChild.setTokenCountedMap(tokenMap);
		firstChild.setTour(tourInnerDummyMethod);

		TokenCounterNode secondChild = new TokenCounterNode();
		tokenMap = new LinkedHashMap<String, MutableInteger>();
		tokenMap.put("select", new MutableInteger(6));
		tokenMap.put("update", new MutableInteger(3));
		secondChild.setTokenCountedMap(tokenMap);
		secondChild.setTour(tourInnerDummyMethod);

		root.setChildren(new LinkedList<TokenCounterNode>());
		root.getChildren().add(firstChild);
		root.getChildren().add(secondChild);

		// serialize root node
		TextTokenCounterNodeSerializer serializer = new TextTokenCounterNodeSerializer();
		String rootSerialized = (String) serializer.serialize(root);

		// assertions
		String[] lines = rootSerialized.split(String.format("%n"));
		Assert.assertNotNull(lines);
		Assert.assertEquals(3, lines.length);
		Assert.assertEquals("[some.package.SomeClass.dummyMethod()][select=5, insert=8][1245 ms]", lines[0]);
		Assert.assertEquals("\t[some.other.package.SomeOtherClass.innerDummyMethod()][select=9, update=3][6789 ms]",
				lines[1]);
		Assert.assertEquals("\t[some.other.package.SomeOtherClass.innerDummyMethod()][select=6, update=3][6789 ms]",
				lines[2]);
	}

	@Test
	public void testInnerMethodFailure() {

		// init mocks
		initMocksInnerMethod(null, new IOException());

		// init tree nodes
		TokenCounterNode root = new TokenCounterNode();
		Map<String, MutableInteger> tokenMap = new LinkedHashMap<String, MutableInteger>();
		tokenMap.put("select", new MutableInteger(5));
		tokenMap.put("insert", new MutableInteger(8));
		root.setTokenCountedMap(tokenMap);
		root.setTour(tourDummyMethod);

		TokenCounterNode firstChild = new TokenCounterNode();
		tokenMap = new LinkedHashMap<String, MutableInteger>();
		tokenMap.put("select", new MutableInteger(9));
		tokenMap.put("update", new MutableInteger(3));
		firstChild.setTokenCountedMap(tokenMap);
		firstChild.setTour(tourInnerDummyMethod);

		root.setChildren(new LinkedList<TokenCounterNode>());
		root.getChildren().add(firstChild);

		// serialize root node
		TextTokenCounterNodeSerializer serializer = new TextTokenCounterNodeSerializer();
		String rootSerialized = (String) serializer.serialize(root);

		// assertions
		String[] lines = rootSerialized.split(String.format("%n"));
		Assert.assertNotNull(lines);
		Assert.assertEquals(2, lines.length);
		Assert.assertEquals("[some.package.SomeClass.dummyMethod()][select=5, insert=8][1245 ms]", lines[0]);
		Assert.assertEquals("\t[some.other.package.SomeOtherClass.innerDummyMethod()][java.io.IOException]", lines[1]);
	}

	private void initMocksSingleMethod(Throwable failCause) {
		EasyMock.reset(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod);
		EasyMock.expect(this.proceedingJoinPointDummyMethod.getSignature()).andReturn(this.signatureDummyMethod)
				.anyTimes();
		EasyMock.expect(this.signatureDummyMethod.getDeclaringTypeName()).andReturn("some.package.SomeClass")
				.anyTimes();
		EasyMock.expect(this.signatureDummyMethod.getName()).andReturn("dummyMethod").anyTimes();
		EasyMock.expect(this.tourDummyMethod.getProceedingJoinPoint()).andReturn(this.proceedingJoinPointDummyMethod)
				.anyTimes();
		EasyMock.expect(this.tourDummyMethod.getFailCause()).andReturn(failCause).anyTimes();
		EasyMock.expect(this.tourDummyMethod.getDuration()).andReturn(1245l).anyTimes();
		EasyMock.replay(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod);
	}

	private void initMocksInnerMethod(Throwable tourDummyMethodFailCause, Throwable tourInnerDummyMethodFailCause) {

		EasyMock.reset(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod,
				this.proceedingJoinPointInnerDummyMethod, this.signatureInnerDummyMethod, this.tourInnerDummyMethod);
		EasyMock.expect(this.proceedingJoinPointDummyMethod.getSignature()).andReturn(this.signatureDummyMethod)
				.anyTimes();
		EasyMock.expect(this.proceedingJoinPointInnerDummyMethod.getSignature())
				.andReturn(this.signatureInnerDummyMethod).anyTimes();
		EasyMock.expect(this.signatureDummyMethod.getDeclaringTypeName()).andReturn("some.package.SomeClass")
				.anyTimes();
		EasyMock.expect(this.signatureDummyMethod.getName()).andReturn("dummyMethod").anyTimes();
		EasyMock.expect(this.signatureInnerDummyMethod.getDeclaringTypeName())
				.andReturn("some.other.package.SomeOtherClass").anyTimes();
		EasyMock.expect(this.signatureInnerDummyMethod.getName()).andReturn("innerDummyMethod").anyTimes();
		EasyMock.expect(this.tourDummyMethod.getProceedingJoinPoint()).andReturn(this.proceedingJoinPointDummyMethod)
				.anyTimes();
		EasyMock.expect(this.tourDummyMethod.getFailCause()).andReturn(tourDummyMethodFailCause).anyTimes();
		EasyMock.expect(this.tourDummyMethod.getDuration()).andReturn(1245l).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getProceedingJoinPoint())
				.andReturn(this.proceedingJoinPointInnerDummyMethod).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getFailCause()).andReturn(tourInnerDummyMethodFailCause).anyTimes();
		EasyMock.expect(this.tourInnerDummyMethod.getDuration()).andReturn(6789l).anyTimes();
		EasyMock.replay(this.proceedingJoinPointDummyMethod, this.signatureDummyMethod, this.tourDummyMethod,
				this.proceedingJoinPointInnerDummyMethod, this.signatureInnerDummyMethod, this.tourInnerDummyMethod);
	}

}
