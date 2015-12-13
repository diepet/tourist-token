package io.tourist.token.writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import io.tourist.token.model.TokenCounterNode;
import io.tourist.token.serializer.TokenCounterNodeSerializer;

public class FileTokenCounterNodeWriterTests {
	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Mock
	private TokenCounterNodeSerializer serializer;

	@Before
	public void setUp() {
		EasyMock.reset(this.serializer);
		EasyMock.expect(this.serializer.serialize((TokenCounterNode) EasyMock.anyObject()))
				.andReturn(String.format("some serialized node%n")).anyTimes();
		EasyMock.replay(this.serializer);
	}

	@Test
	public void testWriter() throws IOException {
		File tempFile = this.testFolder.newFile();
		FileTokenCounterNodeWriter writer = new FileTokenCounterNodeWriter();
		writer.setFile(tempFile.getAbsolutePath());
		writer.setSerializer(this.serializer);
		writer.init();
		writer.write(new TokenCounterNode());
		writer.destroy();

		FileReader fr = new FileReader(tempFile);
		BufferedReader br = new BufferedReader(fr);
		String firstLine = br.readLine();
		String secondLine = br.readLine();
		br.close();
		Assert.assertEquals("some serialized node", firstLine);
		Assert.assertNull(secondLine);
	}

	@Test
	public void testWriterAppend() throws IOException {
		File tempFile = this.testFolder.newFile();
		FileWriter fw = new FileWriter(tempFile);
		fw.write(String.format("an old line%n"));
		fw.close();

		FileTokenCounterNodeWriter writer = new FileTokenCounterNodeWriter();
		writer.setFile(tempFile.getAbsolutePath());
		writer.setSerializer(this.serializer);
		writer.setAppend(true);
		writer.init();
		writer.write(new TokenCounterNode());
		writer.destroy();

		FileReader fr = new FileReader(tempFile);
		BufferedReader br = new BufferedReader(fr);
		String firstLine = br.readLine();
		String secondLine = br.readLine();
		String thirdLine = br.readLine();
		br.close();
		Assert.assertEquals("an old line", firstLine);
		Assert.assertEquals("some serialized node", secondLine);
		Assert.assertNull(thirdLine);
	}

	@Test
	public void testWriterAsync() throws IOException {
		File tempFile = this.testFolder.newFile();
		FileTokenCounterNodeWriter writer = new FileTokenCounterNodeWriter();
		writer.setFile(tempFile.getAbsolutePath());
		writer.setSerializer(this.serializer);
		writer.setAsync(true);
		writer.init();
		writer.write(new TokenCounterNode());
		writer.destroy();

		FileReader fr = new FileReader(tempFile);
		BufferedReader br = new BufferedReader(fr);
		String firstLine = br.readLine();
		String secondLine = br.readLine();
		br.close();
		Assert.assertEquals("some serialized node", firstLine);
		Assert.assertNull(secondLine);
	}

	@Test(expected = TokenCounterNodeWriterException.class)
	public void testInvalidFile() throws IOException {
		File tempFile = this.testFolder.newFolder();
		FileTokenCounterNodeWriter writer = new FileTokenCounterNodeWriter();
		writer.setFile(tempFile.getAbsolutePath());
		writer.setSerializer(this.serializer);
		writer.init();
		Assert.fail("An exception should be raised before");
	}

	@Test(expected = TokenCounterNodeWriterException.class)
	public void testWriteError() throws IOException {
		File tempFile = this.testFolder.newFile();
		FileTokenCounterNodeWriter writer = new FileTokenCounterNodeWriter();
		writer.setFile(tempFile.getAbsolutePath());
		writer.setSerializer(this.serializer);
		writer.init();
		writer.destroy();
		writer.write(new TokenCounterNode());
		Assert.fail("An exception should be raised before");
	}
}
