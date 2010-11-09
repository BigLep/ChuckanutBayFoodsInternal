package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	private List<InventoryItemDto> qbItemList = null;
	private List<InventoryLotDto> itemInInventoryList = null;
	private LotCodeManagerPanel senderObject = null;
	private DatabaseQueryServiceAsync dbQueryService = GWT.create(DatabaseQueryService.class);
	
	public AsyncCallback<List<InventoryItemDto>> qbItemListCallback = new AsyncCallback<List<InventoryItemDto>>() {
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
    public AsyncCallback<List<InventoryLotDto>> itemInInventoryCallback = new AsyncCallback<List<InventoryLotDto>>() {
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
    public AsyncCallback<Void> voidCallback = new AsyncCallback<Void>() {
    	public void onFailure(Throwable caught) {
        Window.alert("system failed");
    	}

      	public void onSuccess(Void result) {
      	}
    };

    public void dbGetQBItems(List<InventoryItemDto> argQBItemList, LotCodeManagerPanel sender) {
    	qbItemList = argQBItemList;
    	senderObject = sender;
        dbQueryService.getInventoryItems(qbItemListCallback);
      }
    
    public void dbGetCheckedInIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getUnusedIngredientLots(itemInInventoryCallback);
      }
    
    public void dbGetInUseIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getInUseIngredientLots(itemInInventoryCallback);
      } 
    
    public void dbGetFullIngredientHistory(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getFullIngredientHistory(itemInInventoryCallback);
      }
    
    public void dbGetLotCodeMatchIngredients(String lotCode, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getLotCodeMatchIngredients(lotCode, itemInInventoryCallback);
      }
    
    public void dbGetDateMatchInUseIngredients(Date date, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	itemInInventoryList = argItemInInventoryList;
    	senderObject = sender;
        dbQueryService.getDateMatchInUseIngredients(date, itemInInventoryCallback);
      }
    
    public void dbSetCheckedInIngredients(List<InventoryLotDto> checkedInIngredients) {
        dbQueryService.setUnusedIngredientLots(checkedInIngredients, voidCallback);
      }

    public void dbSetInUseIngredients(List<InventoryLotDto> inUseIngredients) {
        dbQueryService.setInUseIngredientLots(inUseIngredients, voidCallback);
      }
    
    public void dbSetUsedUpIngredients(List<InventoryLotDto> usedUpIngredients) {
        dbQueryService.setUsedUpInventoryLots(usedUpIngredients, voidCallback);
      }
    
}
