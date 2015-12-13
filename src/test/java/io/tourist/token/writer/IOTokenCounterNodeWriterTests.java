package io.tourist.token.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IOTokenCounterNodeWriterTests {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private Writer mockedWriter;

	@Mock
	private ExecutorService executorService;

	@Before
	public void setUp() throws IOException, InterruptedException {
		this.initMocks();
	}

	@Test(expected = TokenCounterNodeWriterException.class)
	public void testDestroyWriterException() {
		IOTokenCounterNodeWriter nodeWriter = new IOTokenCounterNodeWriter() {
			@Override
			public Writer createWriter() throws IOException {
				return mockedWriter;
			}
		};
		nodeWriter.setExecutorService(this.executorService);
		nodeWriter.setAsync(true);
		nodeWriter.init();
		nodeWriter.destroy();
	}

	private void initMocks() throws IOException, InterruptedException {
		EasyMock.reset(this.mockedWriter, this.executorService);
		this.executorService.shutdown();
		EasyMock.expectLastCall();
		EasyMock.expect(this.executorService.awaitTermination(EasyMock.anyLong(), (TimeUnit) EasyMock.anyObject()))
				.andThrow(new InterruptedException("interrupted exception")).anyTimes();
		this.mockedWriter.close();
		EasyMock.expectLastCall().andThrow(new IOException("close exception")).anyTimes();
		EasyMock.replay(this.mockedWriter, this.executorService);
	}

}
