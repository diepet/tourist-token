package io.tourist.token.serializer;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.aspectj.lang.Signature;

import io.tourist.core.api.Tour;
import io.tourist.token.model.MutableInteger;
import io.tourist.token.model.TokenCounterNode;

/**
 * The text counter node serializer.
 */
public class TextTokenCounterNodeSerializer implements TokenCounterNodeSerializer {

	private static final String LEVEL_SEPARATOR = "\t";
	private static final String TOKEN_SEPARATOR = ", ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.tourist.token.serializer.TokenCounterNodeSerializer#serialize(io.
	 * tourist.token.model.TokenCounterNode)
	 */
	@Override
	public Serializable serialize(final TokenCounterNode node) {
		StringBuilder buffer = new StringBuilder();

		serialize(buffer, node, 0);

		return buffer.toString();
	}

	/**
	 * Serialize the node.
	 *
	 * @param buffer
	 *            the buffer
	 * @param node
	 *            the node
	 * @param level
	 *            the current level node
	 */
	private void serialize(final StringBuilder buffer, final TokenCounterNode node, final int level) {

		final Tour tour = node.getTour();
		final Signature signature = tour.getProceedingJoinPoint().getSignature();
		final Set<Entry<String, MutableInteger>> tokenMapEntrySet = node.getTokenCountedMap().entrySet();
		final List<TokenCounterNode> children = node.getChildren();

		repeat(buffer, LEVEL_SEPARATOR, level);
		buffer.append("[");
		buffer.append(signature.getDeclaringTypeName());
		buffer.append(".");
		buffer.append(signature.getName());
		buffer.append("()]=[");
		for (final Entry<String, MutableInteger> entry : tokenMapEntrySet) {
			buffer.append(entry.getKey());
			buffer.append("=");
			buffer.append(entry.getValue());
			buffer.append(TOKEN_SEPARATOR);
		}
		if (tokenMapEntrySet.size() > 0) {
			// remove last (useless) token separator
			buffer.setLength(buffer.length() - TOKEN_SEPARATOR.length());
		}
		buffer.append("]").append(String.format("%n"));
		if (children != null) {
			final int nextLevel = level + 1;
			for (final TokenCounterNode child : children) {
				serialize(buffer, child, nextLevel);
			}
		}
	}

	/**
	 * Append string n times in the buffer.
	 *
	 * @param buffer
	 *            the buffer
	 * @param s
	 *            the string to repeat
	 * @param n
	 *            the number of times to repeat
	 */
	private void repeat(final StringBuilder buffer, final String s, int n) {
		for (int i = 0; i < n; i++) {
			buffer.append(s);
		}
	}

}
