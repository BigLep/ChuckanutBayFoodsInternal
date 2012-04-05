package com.chuckanutbay.businessobjects.util;

import com.chuckanutbay.businessobjects.QuickbooksItem;

public class BusinessObjectsUtil {
	
	public static double getCakesPerCase(QuickbooksItem qbItem) {
		return getCakesPerCase(qbItem.getId());
	}
	
	public static double getCakesPerCase(String qbItemId) {
		int dashIndex = qbItemId.lastIndexOf("-");
		return Double.parseDouble(qbItemId.substring(dashIndex + 1));
	}
	
}
