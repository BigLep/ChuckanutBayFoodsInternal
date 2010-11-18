package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.client.InventoryItemServiceAsync;
import com.chuckanutbay.webapp.common.client.InventoryLotServiceAsync;
import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	private List<InventoryItemDto> inventoryItemList = null;
	private List<InventoryLotDto> inventoryLotList = null;
	private LotCodeManagerPanel senderObject = null;
	private InventoryLotServiceAsync inventoryLotService;
	private InventoryItemServiceAsync inventoryItemService;
	
	public RpcHelper() {
		inventoryLotService = ServiceUtils.createInventoryLotService();
		inventoryItemService = ServiceUtils.createInventoryItemService();
	}
	
	public AsyncCallback<List<InventoryItemDto>> inventoryItemServiceCallback = new AsyncCallback<List<InventoryItemDto>>() {
    	public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    		log("failure on server");
    	}

		public void onSuccess(List<InventoryItemDto> dbQBItemList) {
			log("success on server");
			if (inventoryItemList != null) {
				inventoryItemList.clear();
			}
			inventoryItemList.addAll(dbQBItemList);
	        if(inventoryItemList != null && inventoryItemList.size() > 0) {
	        	log("A good return is coming");
	        }
	        senderObject.populateFlexTable();
		}
    };
    public AsyncCallback<List<InventoryLotDto>> inventoryLotServiceCallback = new AsyncCallback<List<InventoryLotDto>>() {
    	public void onFailure(Throwable caught) {
    		Window.alert("system failed");
    	}

      	public void onSuccess(List<InventoryLotDto> dbItemInInventoryList) {
      		log("success on server");
			if (inventoryLotList != null) {
				inventoryLotList.clear();
			}
			inventoryLotList.addAll(dbItemInInventoryList);
	        if(inventoryLotList != null && inventoryLotList.size() > 0) {
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
    	inventoryItemList = argQBItemList;
    	senderObject = sender;
    	inventoryItemService.getInventoryItems(inventoryItemServiceCallback);
      }
    
    public void dbGetCheckedInIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	inventoryLotList = argItemInInventoryList;
    	senderObject = sender;
        inventoryLotService.getUnusedIngredientLots(inventoryLotServiceCallback);
      }
    
    public void dbGetInUseIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	inventoryLotList = argItemInInventoryList;
    	senderObject = sender;
        inventoryLotService.getInUseIngredientLots(inventoryLotServiceCallback);
      } 
    
    public void dbGetFullIngredientHistory(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	inventoryLotList = argItemInInventoryList;
    	senderObject = sender;
        inventoryLotService.getFullIngredientHistory(inventoryLotServiceCallback);
      }
    
    public void dbGetLotCodeMatchIngredients(String lotCode, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	inventoryLotList = argItemInInventoryList;
    	senderObject = sender;
        inventoryLotService.getLotCodeMatchIngredients(lotCode, inventoryLotServiceCallback);
      }
    
    public void dbGetDateMatchInUseIngredients(Date date, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
    	inventoryLotList = argItemInInventoryList;
    	senderObject = sender;
        inventoryLotService.getDateMatchInUseIngredients(date, inventoryLotServiceCallback);
      }
    
    public void dbSetCheckedInIngredients(List<InventoryLotDto> checkedInIngredients) {
        inventoryLotService.setUnusedIngredientLots(checkedInIngredients, voidCallback);
      }

    public void dbSetInUseIngredients(List<InventoryLotDto> inUseIngredients) {
        inventoryLotService.setInUseIngredientLots(inUseIngredients, voidCallback);
      }
    
    public void dbSetUsedUpIngredients(List<InventoryLotDto> usedUpIngredients) {
        inventoryLotService.setUsedUpInventoryLots(usedUpIngredients, voidCallback);
      }
    
}
