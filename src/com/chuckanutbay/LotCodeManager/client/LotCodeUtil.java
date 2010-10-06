package com.chuckanutbay.LotCodeManager.client;

import java.util.List;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class LotCodeUtil {
	static List<QBItem> qbItemList = null;
	static List<ItemInInventory> itemInInventoryList = null;
	static LotCodeManagerPanel senderObject = null;
    static DatabaseQueryServiceAsync dbQueryService = GWT.create(DatabaseQueryService.class);
    static AsyncCallback<List<QBItem>> qbItemListCallback = new AsyncCallback<List<QBItem>>() {
    	public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    		log("failure on server");
    	}

		public void onSuccess(List<QBItem> dbQBItemList) {
			log("success on server");
			if (qbItemList != null) {
				qbItemList.clear();
			}
			qbItemList.addAll(dbQBItemList);
	        if(qbItemList != null && qbItemList.size() > 0) {
	        	log("A good return is coming");
	        }
	        senderObject.populateFlexTable();
		}
    };
    static AsyncCallback<List<ItemInInventory>> itemInInventoryCallback = new AsyncCallback<List<ItemInInventory>>() {
    	public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    	}

      	public void onSuccess(List<ItemInInventory> dbItemInInventoryList) {
      		log("success on server");
			if (itemInInventoryList != null) {
				itemInInventoryList.clear();
			}
			itemInInventoryList.addAll(dbItemInInventoryList);
	        if(itemInInventoryList != null && itemInInventoryList.size() > 0) {
	        	log("A good return is coming");
	        }
	        senderObject.populateFlexTable();
		}
    };
    static AsyncCallback<Void> voidCallback = new AsyncCallback<Void>() {
    	public void onFailure(Throwable caught) {
        Window.alert("system failed");
    	}

      	public void onSuccess(Void result) {
      	}
    };
	
    static public void log(String message) {
    	GWT.log(message);
    }
    
	static public void makeButtonWithIcon(Button button, ImageResource icon, String text) {
		HorizontalPanel buttonPanel = new HorizontalPanel();
		Image formatedIcon = new Image(icon);
		HTML formatedText = new HTML(text);
		buttonPanel.setSpacing(2);
		buttonPanel.add(formatedIcon);
		buttonPanel.add(formatedText);
		buttonPanel.setStyleName("center");
		button.setHTML(buttonPanel.getElement().getString());
	}
	
	static public void makePopupVisible(DecoratedPopupPanel popup, Widget sender, int top, int left) {
        int adjustedLeft = sender.getAbsoluteLeft() + left;
        int adjustedTop = sender.getAbsoluteTop() + top;
        popup.setPopupPosition(adjustedLeft, adjustedTop);
        popup.setWidth("150px");
        popup.show();
	}
	
	public static MyIconBundle icons = (MyIconBundle) GWT.create(MyIconBundle.class);

    public static DateTimeFormat dateFormat = DateTimeFormat.getShortDateFormat();

    public static void dbGetQBItems(List<QBItem> argQBItemList, LotCodeManagerPanel sender) {
    	qbItemList = argQBItemList;
    	senderObject = sender;
        dbQueryService.getQBItems(qbItemListCallback);
      }
    
    public static void dbGetCheckedInIngredients(List<ItemInInventory> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getCheckedInIngredients(itemInInventoryCallback);
      }
    
    public static void dbGetInUseIngredients(List<ItemInInventory> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getInUseIngredients(itemInInventoryCallback);
      } 
    
    public static void dbGetFullIngredientHistory(List<ItemInInventory> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getFullIngredientHistory(itemInInventoryCallback);
      }
    
    public static void dbGetLotCodeMatchIngredients(String lotCode, List<ItemInInventory> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getLotCodeMatchIngredients(lotCode, itemInInventoryCallback);
      }
    
    public static void dbGetDateMatchInUseIngredients(Date date, List<ItemInInventory> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getDateMatchInUseIngredients(date, itemInInventoryCallback);
      }
    
    public static void dbSetCheckedInIngredients(List<ItemInInventory> checkedInIngredients) {
        dbQueryService.setCheckedInIngredients(checkedInIngredients, voidCallback);
      }

    public static void dbSetInUseIngredients(List<ItemInInventory> inUseIngredients) {
        dbQueryService.setInUseIngredients(inUseIngredients, voidCallback);
      }
    
    public static void dbSetUsedUpIngredients(List<ItemInInventory> usedUpIngredients) {
        dbQueryService.setUsedUpIngredients(usedUpIngredients, voidCallback);
      }
}
