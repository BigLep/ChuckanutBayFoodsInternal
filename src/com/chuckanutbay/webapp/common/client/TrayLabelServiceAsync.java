package com.chuckanutbay.webapp.common.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TrayLabelServiceAsync {

	void getCurrentLotCode(AsyncCallback<String> callback);

	void getQuickbooksItems(
			AsyncCallback<Map<String, QuickbooksItemDto>> callback);

	void getSalesOrderLineItems(
			AsyncCallback<List<SalesOrderLineItemDto>> callback);

	void getTrayLabelHistroy(AsyncCallback<List<TrayLabelDto>> callback);

	void setTrayLabels(List<TrayLabelDto> newTransitSheets,
			AsyncCallback<Void> callback);

	void updateTrayLabel(TrayLabelDto trayLabel, AsyncCallback<Void> callback);

	void printTrayLabels(Set<TrayLabelDto> trayLabels,
			AsyncCallback<Void> callback);

}
