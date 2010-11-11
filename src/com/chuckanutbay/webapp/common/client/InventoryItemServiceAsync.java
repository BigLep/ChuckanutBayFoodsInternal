package com.chuckanutbay.webapp.common.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InventoryItemServiceAsync {

	void getInventoryItems(AsyncCallback<List<InventoryItemDto>> callback);

}
