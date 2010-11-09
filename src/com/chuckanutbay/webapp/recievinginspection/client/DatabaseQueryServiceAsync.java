package com.chuckanutbay.webapp.recievinginspection.client;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.recievinginspection.shared.InventoryItemDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DatabaseQueryServiceAsync {

	void getInventoryItems(
			AsyncCallback<List<InventoryItemDto>> callback);

}
