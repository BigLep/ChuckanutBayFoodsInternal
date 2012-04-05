package com.chuckanutbay.webapp.timeclock.client;

public class TimeClockUtil {
	public static final int MIN_IN_MILLISECONDS = 60 * 1000; // 60 sec per min * 1000 milliseconds per sec;
	public static final int TEN_SECONDS_IN_MILLISECONDS = 10 * 1000; // 10 sec * 1000 milliseconds per sec;
	public static final int ZERO_KEY_CODE = 48;
	public static final int ONE_KEY_CODE = 49;
	public static final int TWO_KEY_CODE = 50;
	public static final int THREE_KEY_CODE = 51;
	public static final int FOUR_KEY_CODE = 52;
	public static final int FIVE_KEY_CODE = 53;
	public static final int SIX_KEY_CODE = 54;
	public static final int SEVEN_KEY_CODE = 55;
	public static final int EIGHT_KEY_CODE = 56;
	public static final int NINE_KEY_CODE = 57;
	public static final int ENTER_KEY_CODE = 13;
	
	public static int stringToInt(String string) {
		return new Integer(string);
	}
	
	public static String removeLastChar(String string) {
		return string.substring(0, string.length() - 1);
	}
	
	public static String getLastChar(String string) {
		return string.substring(string.length() - 1);
	}
}
