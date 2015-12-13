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

	/** The Constant LEVEL_SEPARATOR. */
	private static final String LEVEL_SEPARATOR = "\t";

	/** The Constant TOKEN_SEPARATOR. */
	private static final String TOKEN_SEPARATOR = ", ";

	/** The Constant METHOD_FORMAT. */
	private static final String METHOD_FORMAT = "[%s.%s()]";

	/** The Constant DURATION_FORMAT. */
	private static final String DURATION_FORMAT = "[%d ms]";

	/** The Constant EXCEPTION_FORMAT. */
	private static final String EXCEPTION_FORMAT = "[%s]";

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
		final List<TokenCounterNode> children = node.getChildren();

		if (tour.getFailCause() == null) {
			this.serializeSuccessfulNode(buffer, node, level);
		} else {
			this.serializeFailureNode(buffer, node, level);
		}

		if (children != null) {
			final int nextLevel = level + 1;
			for (final TokenCounterNode child : children) {
				this.serialize(buffer, child, nextLevel);
			}
		}
	}

	/**
	 * Serialize successful node.
	 *
	 * @param buffer
	 *            the buffer
	 * @param node
	 *            the node
	 * @param level
	 *            the level
	 */
	private void serializeSuccessfulNode(final StringBuilder buffer, final TokenCounterNode node, final int level) {
		final Tour tour = node.getTour();
		final Signature signature = tour.getProceedingJoinPoint().getSignature();
		final Set<Entry<String, MutableInteger>> tokenMapEntrySet = node.getTokenCountedMap().entrySet();

		// tab space * level
		repeat(buffer, LEVEL_SEPARATOR, level);
		// write method name
		buffer.append(String.format(METHOD_FORMAT, signature.getDeclaringTypeName(), signature.getName()));
		// write token map
		buffer.append("[");
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
		buffer.append("]");
		// write duration
		buffer.append(String.format(DURATION_FORMAT, tour.getDuration()));
		// write new line
		buffer.append(String.format("%n"));
	}

	/**
	 * Serialize failure node.
	 *
	 * @param buffer
	 *            the buffer
	 * @param node
	 *            the node
	 * @param level
	 *            the level
	 */
	private void serializeFailureNode(final StringBuilder buffer, final TokenCounterNode node, final int level) {
		final Tour tour = node.getTour();
		final Signature signature = tour.getProceedingJoinPoint().getSignature();

		// tab space * level
		repeat(buffer, LEVEL_SEPARATOR, level);
		// write method name
		buffer.append(String.format(METHOD_FORMAT, signature.getDeclaringTypeName(), signature.getName()));
		// write duration
		buffer.append(String.format(EXCEPTION_FORMAT, tour.getFailCause().getClass().getName()));
		// write new line
		buffer.append(String.format("%n"));
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
