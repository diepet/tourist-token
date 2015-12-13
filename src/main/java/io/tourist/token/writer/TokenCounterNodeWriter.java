package io.tourist.token.writer;

import io.tourist.token.model.TokenCounterNode;

/**
 * The token counter node writer inteface.
 */
public interface TokenCounterNodeWriter {

	/**
	 * Write.
	 *
	 * @param tokenCounterNode
	 *            the token counter node
	 */
	void write(TokenCounterNode tokenCounterNode);

}
