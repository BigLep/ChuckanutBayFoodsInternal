package com.chuckanutbay.webapp.common.client;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TrayLabelServiceAsync {

	void getCurrentLotCode(AsyncCallback<String> callback);

	void getQuickbooksItems(AsyncCallback<List<QuickbooksItemDto>> callback);

	void getSalesOrderLineItems(
			AsyncCallback<List<SalesOrderLineItemDto>> callback);

	void getTrayLabelHistroy(AsyncCallback<List<TrayLabelDto>> callback);

	void setTrayLabels(List<TrayLabelDto> newTransitSheets,
			AsyncCallback<List<TrayLabelDto>> callback);

	void updateTrayLabel(TrayLabelDto trayLabel, AsyncCallback<Void> callback);

}
