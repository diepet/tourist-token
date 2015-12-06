package io.tourist.token.listener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import io.tourist.core.api.Shot;
import io.tourist.core.api.Tour;
import io.tourist.core.event.TourEventListenerAdapter;
import io.tourist.token.extractor.TokenExtractor;
import io.tourist.token.model.MutableInteger;
import io.tourist.token.model.TokenCounterNode;

/**
 * The token counter tour event listener class.
 *
 * @see TokenCounterTourEventEvent
 */
public final class TokenCounterTourEventListener extends TourEventListenerAdapter {

	/** The token extractor. */
	private TokenExtractor tokenExtractor;

	/** The thread local node stack. */
	private ThreadLocal<Stack<TokenCounterNode>> threadLocalNodeStack = new ThreadLocal<Stack<TokenCounterNode>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.core.event.TourEventListenerAdapter#onTouristTravelStarted(io.
	 * tourist.core.api.Tour)
	 */
	@Override
	public void onTouristTravelStarted(final Tour tour) {
		// allocate new stack
		this.threadLocalNodeStack.set(new Stack<TokenCounterNode>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.core.event.TourEventListenerAdapter#onTourStarted(io.tourist.
	 * core.api.Tour)
	 */
	@Override
	public void onTourStarted(final Tour tour) {
		// create a new node
		final TokenCounterNode node = new TokenCounterNode();
		node.setTour(tour);
		// push node to stack
		final Stack<TokenCounterNode> nodeStack = this.threadLocalNodeStack.get();
		nodeStack.add(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.core.event.TourEventListenerAdapter#onTourEnded(io.tourist.
	 * core.api.Tour)
	 */
	@Override
	public void onTourEnded(final Tour tour) {
		// count tokens and build map
		final Map<String, MutableInteger> tokenCountedMap = this.countTokens(tour);
		// pop node from stack
		final Stack<TokenCounterNode> nodeStack = this.threadLocalNodeStack.get();
		final TokenCounterNode node = nodeStack.pop();
		// set map to popped node
		node.setTokenCountedMap(tokenCountedMap);
		if (!nodeStack.isEmpty()) {
			final TokenCounterNode parentNode = nodeStack.peek();
			// add current node to its parent
			addCurrentNodeToParentNode(parentNode, node);
			// update parent node token counted map adding the current map
			// values
			incrementNodeTokenCountedMap(parentNode, tokenCountedMap);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.core.event.TourEventListenerAdapter#onTourFailed(io.tourist.
	 * core.api.Tour)
	 */
	@Override
	public void onTourFailed(final Tour tour) {
		// pop node from stack
		final Stack<TokenCounterNode> nodeStack = this.threadLocalNodeStack.get();
		nodeStack.pop();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.core.event.TourEventListenerAdapter#onTouristTravelEnded(io.
	 * tourist.core.api.Tour)
	 */
	@Override
	public void onTouristTravelEnded(final Tour tour) {
		// de-allocate stack
		threadLocalNodeStack.remove();

		// TODO add writer
	}

	/**
	 * Count tokens.
	 *
	 * @param tour
	 *            the tour
	 * @return the map
	 */
	private Map<String, MutableInteger> countTokens(final Tour tour) {
		final List<Shot> shotList = tour.getCameraRoll().getShotList();
		final Map<String, MutableInteger> tokenCountedMap = new HashMap<String, MutableInteger>();
		String token;
		MutableInteger currentValue;
		for (final Shot shot : shotList) {
			token = this.tokenExtractor.extractToken(shot);
			if (token != null) {
				currentValue = tokenCountedMap.get(token);
				if (currentValue == null) {
					tokenCountedMap.put(token, new MutableInteger());
				} else {
					currentValue.increment();
				}
			}
		}
		return tokenCountedMap;
	}

	/**
	 * Adds the current node to parent node.
	 *
	 * @param parentNode
	 *            the parent node
	 * @param childNode
	 *            the child node
	 */
	private void addCurrentNodeToParentNode(final TokenCounterNode parentNode, final TokenCounterNode childNode) {
		// relate nodes
		List<TokenCounterNode> children = parentNode.getChildren();
		if (children == null) {
			children = new LinkedList<TokenCounterNode>();
			parentNode.setChildren(children);
		}
		children.add(childNode);
	}

	/**
	 * Increment node token counted map.
	 *
	 * @param parentNode
	 *            the parent node
	 * @param tokenCountedMap
	 *            the token counted map
	 */
	private void incrementNodeTokenCountedMap(final TokenCounterNode parentNode,
			final Map<String, MutableInteger> tokenCountedMap) {
		final Map<String, MutableInteger> parentTokenCountedMap = parentNode.getTokenCountedMap();
		String token;
		MutableInteger valueToIncrement;
		for (final Entry<String, MutableInteger> entry : tokenCountedMap.entrySet()) {
			token = entry.getKey();
			valueToIncrement = parentTokenCountedMap.get(token);
			if (valueToIncrement != null) {
				valueToIncrement.incrementBy(entry.getValue().getValue());
			} else {
				parentTokenCountedMap.put(token, new MutableInteger(entry.getValue().getValue()));
			}
		}
	}
}
