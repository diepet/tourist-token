package io.tourist.token.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * The console token counter node writer class.
 */
public class FileTokenCounterNodeWriter extends IOTokenCounterNodeWriter implements TokenCounterNodeWriter {

	/** The file. */
	private String file;

	/** The append. */
	private boolean append;

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
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * Sets the append.
	 *
	 * @param append
	 *            the new append
	 */
	public void setAppend(boolean append) {
		this.append = append;
	}

}
