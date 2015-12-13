package io.tourist.token.writer;

import io.tourist.token.model.TokenCounterNode;
import io.tourist.token.serializer.TokenCounterNodeSerializer;

/**
 * The console token counter node writer class.
 */
public class ConsoleTokenCounterNodeWriter implements TokenCounterNodeWriter {

	/** The serializer. */
	private TokenCounterNodeSerializer serializer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.token.writer.TokenCounterNodeWriter#write(io.tourist.token.
	 * model.TokenCounterNode)
	 */
	@Override
	public synchronized void write(final TokenCounterNode tokenCounterNode) {
		System.out.println(this.serializer.serialize(tokenCounterNode));
	}

	/**
	 * Sets the serializer.
	 *
	 * @param serializer
	 *            the new serializer
	 */
	public void setSerializer(TokenCounterNodeSerializer serializer) {
		this.serializer = serializer;
	}
}
