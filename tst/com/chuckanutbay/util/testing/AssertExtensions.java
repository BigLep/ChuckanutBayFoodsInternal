package com.chuckanutbay.util.testing;

import org.junit.Assert;

import com.google.common.base.Function;

/**
 * Extensions for {@link Assert}.
 */
public class AssertExtensions {

	/**
	 * Asserts that a object can be converted to one type and back and get an equal object.
	 * This is intended to test {@link Function} pairs.
	 * @param obj
	 * @param function1
	 * @param function2
	 */
	public static <T1,T2> void assertFunctions(T1 obj, Function<T1,T2> function1, Function<T2,T1> function2) {
		T1 newObj = function2.apply(function1.apply(obj));
		if (!obj.equals(newObj)) {
			throw new AssertionError(obj + " was not the result when applying " + function1 + " and " + function2 + " to " + newObj + ".");
		}
	}
	
}
