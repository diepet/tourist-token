package io.tourist.token.model;

import org.junit.Assert;
import org.junit.Test;

public class MutableIntegerTests {

	@Test
	public void testMutableInteger() {
		MutableInteger mi = new MutableInteger();
		Assert.assertEquals(1, mi.getValue());
		Assert.assertEquals(1, mi.hashCode());
		mi.increment();
		Assert.assertEquals(2, mi.getValue());
		Assert.assertEquals(2, mi.hashCode());
		mi.setValue(10);
		Assert.assertEquals(10, mi.getValue());
		Assert.assertEquals(10, mi.hashCode());
	}

	@Test
	public void testMutableIntegerCompare() {
		MutableInteger first = new MutableInteger();
		MutableInteger second = new MutableInteger();
		int compare;

		compare = first.compareTo(null);
		Assert.assertEquals(1, compare);
		compare = first.compareTo(second);
		Assert.assertEquals(0, compare);
		compare = first.compareTo(second);
		first.setValue(10);
		second.setValue(20);
		compare = first.compareTo(second);
		Assert.assertEquals(-1, compare);
		first.setValue(20);
		compare = first.compareTo(second);
		Assert.assertEquals(0, compare);
		first.setValue(30);
		compare = first.compareTo(second);
		Assert.assertEquals(1, compare);
	}

	@Test
	public void testMutableIntegerEquals() {
		MutableInteger first = new MutableInteger();
		MutableInteger second = new MutableInteger();
		boolean compare;

		compare = first.equals(null);
		Assert.assertFalse(compare);
		compare = first.equals(second);
		Assert.assertTrue(compare);
		first.setValue(10);
		second.setValue(20);
		compare = first.equals(second);
		Assert.assertFalse(compare);
		first.setValue(20);
		compare = first.equals(second);
		Assert.assertTrue(compare);

	}

	@Test
	public void testMutableIntegerIncrementBy() {
		MutableInteger mi = new MutableInteger();
		mi.incrementBy(10);
		Assert.assertEquals(11, mi.getValue());
		mi.setValue(20);
		mi.incrementBy(5);
		Assert.assertEquals(25, mi.getValue());
	}

	@Test
	public void testMutableIntegerConstructors() {
		MutableInteger mi = new MutableInteger(30);
		Assert.assertEquals(30, mi.getValue());
	}
}
