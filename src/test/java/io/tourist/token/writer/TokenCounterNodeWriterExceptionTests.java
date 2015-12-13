package io.tourist.token.writer;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class TokenCounterNodeWriterExceptionTests {

	@Test
	public void testConstructors() {

		IOException ioEx = new IOException();

		TokenCounterNodeWriterException ex = new TokenCounterNodeWriterException();
		Assert.assertNull(ex.getMessage());
		Assert.assertNull(ex.getCause());

		ex = new TokenCounterNodeWriterException("some error");
		Assert.assertEquals("some error", ex.getMessage());
		Assert.assertNull(ex.getCause());

		ex = new TokenCounterNodeWriterException("some error", ioEx);
		Assert.assertEquals("some error", ex.getMessage());
		Assert.assertEquals(ioEx, ex.getCause());

		ex = new TokenCounterNodeWriterException(ioEx);
		Assert.assertEquals("java.io.IOException", ex.getMessage());
		Assert.assertEquals(ioEx, ex.getCause());
	}

}
