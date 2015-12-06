package io.tourist.token.model;

import java.io.Serializable;

/**
 * The mutable integer class.
 */
public final class MutableInteger implements Serializable, Comparable<MutableInteger> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8210259536067280470L;

	/** The value. */
	private int value = 1;

	/**
	 * Instantiates a new mutable integer.
	 */
	public MutableInteger() {
		super();
	}

	/**
	 * Instantiates a new mutable integer.
	 *
	 * @param value
	 *            the value
	 */
	public MutableInteger(final int value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(final int value) {
		this.value = value;
	}

	/**
	 * Increment.
	 */
	public void increment() {
		++this.value;
	}

	/**
	 * Increment by.
	 *
	 * @param increment
	 *            the increment
	 */
	public void incrementBy(final int increment) {
		this.value += increment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean result = false;
		if (obj instanceof MutableInteger) {
			result = this.value == ((MutableInteger) obj).value;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final MutableInteger other) {
		int result = 1;
		if (other != null) {
			if (this.value < other.value) {
				result = -1;
			} else if (this.value == other.value) {
				result = 0;
			}
		}
		return result;
	}
}
