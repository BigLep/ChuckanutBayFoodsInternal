package com.chuckanutbay.util;

/**
 * Extension utilities for {@link Object}.
 */
public class ObjectUtilsExtensions {

	/**
	 * @param <T>
	 * @param obj
	 * @param defaultObj
	 * @return obj if it is not null; defaultObj if obj is null.
	 */
	public static <T> T defaultIfNull(T obj, T defaultObj) {
		return obj != null ? obj : defaultObj;
	}

}
