package io.tourist.token.model;

import java.util.List;
import java.util.Map;

import io.tourist.core.api.Tour;

/**
 * The token counter node class.
 */
public final class TokenCounterNode {

	/** The tour. */
	private Tour tour;

	/** The token counted map. */
	private Map<String, MutableInteger> tokenCountedMap;

	/** The children nodes. */
	private List<TokenCounterNode> children;

	/**
	 * Gets the tour.
	 *
	 * @return the tour
	 */
	public Tour getTour() {
		return tour;
	}

	/**
	 * Sets the tour.
	 *
	 * @param tour
	 *            the new tour
	 */
	public void setTour(final Tour tour) {
		this.tour = tour;
	}

	/**
	 * Gets the token counted map.
	 *
	 * @return the token counted map
	 */
	public Map<String, MutableInteger> getTokenCountedMap() {
		return tokenCountedMap;
	}

	/**
	 * Sets the token counted map.
	 *
	 * @param tokenCountedMap
	 *            the token counted map
	 */
	public void setTokenCountedMap(final Map<String, MutableInteger> tokenCountedMap) {
		this.tokenCountedMap = tokenCountedMap;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<TokenCounterNode> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children
	 *            the new children
	 */
	public void setChildren(final List<TokenCounterNode> children) {
		this.children = children;
	}
}
