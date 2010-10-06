package com.chuckanutbay.LotCodeManager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MyIconBundle extends ClientBundle {
	public static final MyIconBundle INSTANCE = GWT.create(MyIconBundle.class); 
		   @Source("./icons/database.png")
		   public ImageResource databaseIcon();
		   @Source("./icons/date_magnify.png")
		   public ImageResource dateIcon();
		   @Source("./icons/magnifier.png")
		   public ImageResource searchIcon();
		   @Source("./icons/table.png")
		   public ImageResource listIcon();
		   @Source("./icons/cross.png")
		   public ImageResource cancelIcon();
		   @Source("./icons/error_go.png")
		   public ImageResource warningIcon();
		   @Source("./icons/tick.png")
		   public ImageResource checkmarkIcon();
		   @Source("./icons/add.png")
		   public ImageResource addIcon();
		   @Source("./icons/database_add.png")
		   public ImageResource saveIcon();
		   @Source("./icons/delete.png")
		   public ImageResource deleteIcon();
		   @Source("./icons/arrow_rotate_anticlockwise.png")
		   public ImageResource refreshIcon();
		   @Source("./icons/printer.png")
		   public ImageResource printIcon();
		   @Source("./icons/progress_indicator.gif")
		   public ImageResource progressIcon();
		   @Source("./icons/arrow_right.png")
		   public ImageResource nextIcon();
		   @Source("./icons/arrow_undo.png")
		   public ImageResource backIcon();
}
