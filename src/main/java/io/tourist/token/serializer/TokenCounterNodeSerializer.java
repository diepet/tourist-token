package io.tourist.token.serializer;

import java.io.Serializable;

import io.tourist.token.model.TokenCounterNode;

/**
 * The token counter node serializer interface.
 */
public interface TokenCounterNodeSerializer {

	/**
	 * Serialize a token counter node.
	 *
	 * @param node
	 *            the node
	 * @return the serialized version of the node
	 */
	Serializable serialize(TokenCounterNode node);

}
