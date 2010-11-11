package com.chuckanutbay.webapp.recievinginspection.client;

import static com.chuckanutbay.webapp.recievinginspection.client.RecievingInspectionUtil.log;

import java.util.List;

import com.chuckanutbay.webapp.common.client.InventoryItemService;
import com.chuckanutbay.webapp.common.client.InventoryItemServiceAsync;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.RecievingInspectionDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	private List<InventoryItemDto> qbItemList = null;
	private List<RecievingInspectionDto> recievingInspectionList = null;
	private RecievingInspection senderObject = null;
	private InventoryItemServiceAsync inventoryItemService = GWT.create(InventoryItemService.class);
	
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
	        senderObject.populateListBoxes();
		}
    };
    

    public void dbGetQBItems(List<InventoryItemDto> argQBItemList, RecievingInspection sender) {
    	log("sendingRequest");
    	qbItemList = argQBItemList;
    	senderObject = sender;
        inventoryItemService.getInventoryItems(qbItemListCallback);
      }
    
   
}
