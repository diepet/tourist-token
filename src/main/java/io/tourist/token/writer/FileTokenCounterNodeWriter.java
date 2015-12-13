package io.tourist.token.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * The console token counter node writer class.
 */
public final class FileTokenCounterNodeWriter extends IOTokenCounterNodeWriter implements TokenCounterNodeWriter {

	/** The file. */
	private String file;

	/** The append. */
	private boolean append;

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.tourist.token.writer.IOTokenCounterNodeWriter#createWriter()
	 */
	@Override
	public Writer createWriter() throws IOException {
		return new FileWriter(this.file, this.append);
	}

	/**
	 * Sets the file.
	 *
	 * @param file
	 *            the new file
	 */
	public void setFile(final String file) {
		this.file = file;
	}

	/**
	 * Sets the append.
	 *
	 * @param append
	 *            the new append
	 */
	public void setAppend(final boolean append) {
		this.append = append;
	}

}
