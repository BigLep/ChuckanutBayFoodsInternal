package com.chuckanutbay.webapp.common.shared;

import com.google.gwt.user.client.ui.Image;

public class IconUtil {
	public static final String DATABASE = "icons/database.png";
	public static final String DATE = "icons/date_magnify.png";
	public static final String SEARCH = "icons/magnifier.png";
	public static final String PRINT = "icons/printer.png";
	public static final String PROGRESS = "icons/progress_indicator.gif";
	public static final String NEXT = "icons/arrow_right.png";
	public static final String BACK = "icons/arrow_undo.png";
	public static final String ARROW_UP = "icons/arrow_up.png";
	public static final String REFRESH = "icons/arrow_rotate_anticlockwise.png";
	public static final String DELETE = "icons/delete.png";
	public static final String SAVE = "icons/database_add.png";
	public static final String ADD = "icons/add.png";
	public static final String LIST = "icons/table.png";
	public static final String CHECKMARK = "icons/tick.png";
	public static final String WARNING = "icons/error_go.png";
	public static final String CANCEL = "icons/cross.png";
	
	public static Image newImage(String image) {
		return new Image(image);
	}
	
}
