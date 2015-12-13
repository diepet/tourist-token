package io.tourist.token.writer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.tourist.token.model.TokenCounterNode;
import io.tourist.token.serializer.TokenCounterNodeSerializer;

public class ConsoleTokenCounterNodeWriterTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private TokenCounterNodeSerializer serializer;

	private PrintStream previousPrintStream;

	private ByteArrayOutputStream baos;

	@Before
	public void setUp() {
		this.previousPrintStream = System.out;
		baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
		EasyMock.reset(this.serializer);
		EasyMock.expect(this.serializer.serialize((TokenCounterNode) EasyMock.anyObject()))
				.andReturn("some serialized node").anyTimes();
		EasyMock.replay(this.serializer);
	}

	@After
	public void tearDown() {
		System.setOut(this.previousPrintStream);
		baos = null;
	}

	@Test
	public void testWriter() {
		ConsoleTokenCounterNodeWriter writer = new ConsoleTokenCounterNodeWriter();
		writer.setSerializer(this.serializer);
		writer.write(new TokenCounterNode());
		Assert.assertEquals(String.format("some serialized node%n"), baos.toString());
	}

}
