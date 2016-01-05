package io.tourist.token.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.tourist.token.model.TokenCounterNode;
import io.tourist.token.serializer.TokenCounterNodeSerializer;

/**
 * The console token counter node writer class.
 */
public abstract class IOTokenCounterNodeWriter implements TokenCounterNodeWriter {

	/** The Constant AWAIT_TERMINATION_TIME_IN_SECONDS. */
	private static final long AWAIT_TERMINATION_TIME_IN_SECONDS = 5L;

	/** The serializer. */
	private TokenCounterNodeSerializer serializer;

	/** The asynchronous flag. */
	private boolean async = false;

	/** The writer. */
	private Writer writer;

	/** The executor service. */
	private ExecutorService executorService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.token.writer.TokenCounterNodeWriter#write(io.tourist.token.
	 * model.TokenCounterNode)
	 */
	@Override
	public final void write(final TokenCounterNode tokenCounterNode) {
		if (this.async) {
			this.asyncWrite(tokenCounterNode);
		} else {
			this.syncWrite(tokenCounterNode);
		}
	};

	/**
	 * Sync write.
	 *
	 * @param tokenCounterNode
	 *            the token counter node
	 */
	private void syncWrite(final TokenCounterNode tokenCounterNode) {
		try {
			writer.write(serializer.serialize(tokenCounterNode).toString());
			writer.flush();
		} catch (IOException e) {
			throw new TokenCounterNodeWriterException("Unable to write a token counter node", e);
		}
	}

	/**
	 * Async write.
	 *
	 * @param tokenCounterNode
	 *            the token counter node
	 */
	private void asyncWrite(final TokenCounterNode tokenCounterNode) {
		this.executorService.execute(new Runnable() {
			@Override
			public void run() {
				syncWrite(tokenCounterNode);
			}
		});
	}

	/**
	 * Inits the file node writer.
	 */
	public final void init() {
		if (this.async && this.executorService == null) {
			// if not executor service is configured create a new single thread
			// executor
			this.executorService = Executors.newSingleThreadExecutor();
		}
		try {
			this.writer = createWriter();
		} catch (IOException e) {
			throw new TokenCounterNodeWriterException("Unable to initialize a file token counter node writer", e);
		}
	}

	/**
	 * Destroy.
	 */
	public final void destroy() {
		if (this.async) {
			this.executorService.shutdown();
			try {
				this.executorService.awaitTermination(AWAIT_TERMINATION_TIME_IN_SECONDS, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// do nothing
			}
		}
		try {
			this.writer.close();
		} catch (IOException e) {
			throw new TokenCounterNodeWriterException("Unable to close a file token counter node writer", e);
		}
	}

	/**
	 * Sets the serializer.
	 *
	 * @param serializer
	 *            the new serializer
	 */
	public final void setSerializer(final TokenCounterNodeSerializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * Sets the asynchronous flag.
	 *
	 * @param async
	 *            the new asynchronous flag
	 */
	public final void setAsync(final boolean async) {
		this.async = async;
	}

	/**
	 * Sets the executor service.
	 *
	 * @param executorService
	 *            the new executor service
	 */
	public final void setExecutorService(final ExecutorService executorService) {
		this.executorService = executorService;
	}

	/**
	 * Creates the writer.
	 *
	 * @return the writer
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public abstract Writer createWriter() throws IOException;
}
