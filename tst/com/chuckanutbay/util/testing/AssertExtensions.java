package com.chuckanutbay.util.testing;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
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
	/**
	 * Asserts that a collection contains the same number of elements as the size parameter.
	 * @param collection
	 * @param size
	 */
	public static void assertSize(Collection<?> collection, int size) {
		if (collection == null) {
			throw new AssertionError("The provided collection is null");
		}
		if (collection.size() != size) {
			throw new AssertionError(collection + " contains " + collection.size() + " objects when " + size + " were expected");
		}
	}
	
	/**
	 * Asserts that the collection doesn't contain any elements.
	 * @param collection
	 */
	public static void assertEmpty(Collection<?> collection) {
		assertSize(collection, 0);
	}
	
	/**
	 * Asserts that the date is on the specified day and month.
	 * @param date
	 * @param month
	 * @param day
	 */
	public static void assertDateEquals(Date date, int month, int day) {
		DateTime dt = new DateTime(date);
		if (dt.getDayOfMonth() != day) {
			throw new AssertionError(date + " day of the month is not " + day);
		}
		if (dt.getMonthOfYear() != month) {
			throw new AssertionError(date + " month of the year is not " + month);
		}
	}
	
	public static void assertFileExists(String filePath) {
		File file = new File(filePath);
		if (!file.exists())	{
			throw new AssertionError(filePath + " doesn't exist");
		}
	}
}
