package com.chuckanutbay.webapp.common.client;

public class GwtUtils {
	
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.equals("");
	}
	
	public static boolean isNotNullOrEmpty(String string) {
		return !isNullOrEmpty(string);
	}
	
}
