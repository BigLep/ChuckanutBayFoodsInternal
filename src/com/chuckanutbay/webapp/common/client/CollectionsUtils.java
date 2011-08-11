package com.chuckanutbay.webapp.common.client;

import java.util.Collection;

public class CollectionsUtils {
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}
}
