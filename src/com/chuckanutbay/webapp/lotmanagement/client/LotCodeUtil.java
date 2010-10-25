package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.List;
import java.util.Date;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
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
	static List<InventoryItemDto> qbItemList = null;
	static List<InventoryLotDto> itemInInventoryList = null;
	static LotCodeManagerPanel senderObject = null;
    static DatabaseQueryServiceAsync dbQueryService = GWT.create(DatabaseQueryService.class);
    static AsyncCallback<List<InventoryItemDto>> qbItemListCallback = new AsyncCallback<List<InventoryItemDto>>() {
    	public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    		log("failure on server");
    	}

		public void onSuccess(List<InventoryItemDto> dbQBItemList) {
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
    static AsyncCallback<List<InventoryLotDto>> itemInInventoryCallback = new AsyncCallback<List<InventoryLotDto>>() {
    	public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    	}

      	public void onSuccess(List<InventoryLotDto> dbItemInInventoryList) {
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

    public static void dbGetQBItems(List<InventoryItemDto> argQBItemList, LotCodeManagerPanel sender) {
    	qbItemList = argQBItemList;
    	senderObject = sender;
        dbQueryService.getInventoryItems(qbItemListCallback);
      }
    
    public static void dbGetCheckedInIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getCheckedInIngredientLots(itemInInventoryCallback);
      }
    
    public static void dbGetInUseIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getInUseIngredientLots(itemInInventoryCallback);
      } 
    
    public static void dbGetFullIngredientHistory(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getFullIngredientHistory(itemInInventoryCallback);
      }
    
    public static void dbGetLotCodeMatchIngredients(String lotCode, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getLotCodeMatchIngredients(lotCode, itemInInventoryCallback);
      }
    
    public static void dbGetDateMatchInUseIngredients(Date date, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getDateMatchInUseIngredients(date, itemInInventoryCallback);
      }
    
    public static void dbSetCheckedInIngredients(List<InventoryLotDto> checkedInIngredients) {
        dbQueryService.setCheckedInIngredientLots(checkedInIngredients, voidCallback);
      }

    public static void dbSetInUseIngredients(List<InventoryLotDto> inUseIngredients) {
        dbQueryService.setInUseIngredientLots(inUseIngredients, voidCallback);
      }
    
    public static void dbSetUsedUpIngredients(List<InventoryLotDto> usedUpIngredients) {
        dbQueryService.setUsedUpInventoryLots(usedUpIngredients, voidCallback);
      }
}
