package com.chuckanutbay.util;

/**
 * Utils/extensions for {@link System}.
 */
public class SystemUtilsExtensions {

	/**
	 * @param key
	 * @return System property or environment variable that matches the provided key.
	 */
	public static String getConfigValue(String key) {
		String value = System.getProperty(key);
		if (value != null) {
			return value;
		}
		return System.getenv(key);
	}

}
