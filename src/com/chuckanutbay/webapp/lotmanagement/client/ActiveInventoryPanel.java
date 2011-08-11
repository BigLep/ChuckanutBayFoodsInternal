package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryLotService;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.ACTIVE_INVENTORY_ITEMS_TITLE;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DEFAULT_HEADERS;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.createInventoryLotServiceCallback;

import java.util.Date;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ActiveInventoryPanel extends LotCodeManagerDialogBox {
	private final Label dateToSearchLabel = new Label("Today's Date:");
	private final TextBox dateBox = new TextBox();
	
	public ActiveInventoryPanel() {
		super (ACTIVE_INVENTORY_ITEMS_TITLE, false, true);
		createInventoryLotService().getInUseInventoryLots(createInventoryLotServiceCallback(this));
		center();
	}

	@Override
	public void updateDB() {
		// Nothing to Update
	}

	@Override
	Widget[] getHeaderWidgets() {
		dateBox.setText(DATE_FORMAT.format(new Date()));
		dateBox.setReadOnly(true);
		dateBox.setStyleName("dateBox");
		return new Widget[] {dateToSearchLabel, dateBox};
	}

	@Override
	CellTable<InventoryLotDto> getCellTable() {
		return newMultiSelectionCellTable(DEFAULT_HEADERS);
	}
}