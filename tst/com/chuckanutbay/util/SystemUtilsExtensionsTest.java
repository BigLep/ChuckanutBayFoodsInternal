package com.chuckanutbay.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @see SystemUtilsExtensions
 */
public class SystemUtilsExtensionsTest {

	/**
	 * @see SystemUtilsExtensions#getConfigValue(String)
	 */
	@Test
	public void testGetConfigValue() {
		final String randomKey = "randomKey" + System.currentTimeMillis();
		assertNull(SystemUtilsExtensions.getConfigValue(randomKey));

		final String value = "value";
		System.setProperty(randomKey, value);
		assertEquals(value, SystemUtilsExtensions.getConfigValue(randomKey));

		// Doesn't test environment variables because there's no way to set them programaticlaly.
	}
}
