package io.tourist.token.listener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import io.tourist.core.api.CameraRoll;
import io.tourist.core.api.Shot;
import io.tourist.core.api.Tour;
import io.tourist.core.event.TourEventListenerAdapter;
import io.tourist.token.extractor.TokenExtractor;
import io.tourist.token.model.MutableInteger;
import io.tourist.token.model.TokenCounterNode;
import io.tourist.token.writer.TokenCounterNodeWriter;

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

	/** The thread local token counter node root. */
	private ThreadLocal<TokenCounterNode> threadLocalNodeRoot = new ThreadLocal<TokenCounterNode>();

	/** The token counter node writer. */
	private TokenCounterNodeWriter tokenCounterNodeWriter;

	/**
	 * Sets the token extractor.
	 *
	 * @param tokenExtractor
	 *            the new token extractor
	 */
	public void setTokenExtractor(final TokenExtractor tokenExtractor) {
		this.tokenExtractor = tokenExtractor;
	}

	/**
	 * Sets the token counter node writer.
	 *
	 * @param tokenCounterNodeWriter
	 *            the new token counter node writer
	 */
	public void setTokenCounterNodeWriter(final TokenCounterNodeWriter tokenCounterNodeWriter) {
		this.tokenCounterNodeWriter = tokenCounterNodeWriter;
	}

	/**
	 * On tourist travel started.
	 *
	 * @param tour
	 *            the tour
	 */
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
		// reset thread local node root
		this.threadLocalNodeRoot.remove();
	}

	/**
	 * On tour started.
	 *
	 * @param tour
	 *            the tour
	 */
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
		final Map<String, MutableInteger> tokenCountedMap = new HashMap<String, MutableInteger>();
		node.setTokenCountedMap(tokenCountedMap);
		// push node to stack
		final Stack<TokenCounterNode> nodeStack = this.threadLocalNodeStack.get();
		nodeStack.push(node);
	}

	/**
	 * On tour ended.
	 *
	 * @param tour
	 *            the tour
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.tourist.core.event.TourEventListenerAdapter#onTourEnded(io.tourist.
	 * core.api.Tour)
	 */
	@Override
	public void onTourEnded(final Tour tour) {
		// pop node from stack
		final Stack<TokenCounterNode> nodeStack = this.threadLocalNodeStack.get();
		final TokenCounterNode node = nodeStack.pop();
		// get node map and increment its token counting from camera roll
		final Map<String, MutableInteger> tokenCountedMap = node.getTokenCountedMap();
		incrementTokenCountedMapFromCameraRoll(tour.getCameraRoll(), tokenCountedMap);
		// update parent node relationship and incrementing its node token
		// counted map
		if (!nodeStack.isEmpty()) {
			final TokenCounterNode parentNode = nodeStack.peek();
			// add current node to its parent
			addCurrentNodeToParentNode(parentNode, node);
			// update parent node token counted map adding the current map
			// values
			incrementNodeTokenCountedMap(parentNode, tokenCountedMap);
		} else {
			// if stack is empty then the current node is the root node to write
			this.threadLocalNodeRoot.set(node);
		}
	}

	/**
	 * On tour failed.
	 *
	 * @param tour
	 *            the tour
	 */
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
		final TokenCounterNode node = nodeStack.pop();
		// update parent node relationship
		if (!nodeStack.isEmpty()) {
			final TokenCounterNode parentNode = nodeStack.peek();
			// add current node to its parent
			addCurrentNodeToParentNode(parentNode, node);
		} else {
			// if stack is empty then the current node is the root node to write
			this.threadLocalNodeRoot.set(node);
		}
	}

	/**
	 * On tourist travel ended.
	 *
	 * @param tour
	 *            the tour
	 */
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
		// write token counter root node
		final TokenCounterNode rootNode = this.threadLocalNodeRoot.get();
		this.tokenCounterNodeWriter.write(rootNode);
		// de-allocate token counter root node
		this.threadLocalNodeRoot.remove();
	}

	/**
	 * Increment node token counted map from camera roll.
	 *
	 * @param cameraRoll
	 *            the camera roll
	 * @param tokenCountedMap
	 *            the token counted map
	 */
	private void incrementTokenCountedMapFromCameraRoll(final CameraRoll cameraRoll,
			final Map<String, MutableInteger> tokenCountedMap) {
		final List<Shot> shotList = cameraRoll.getShotList();
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
