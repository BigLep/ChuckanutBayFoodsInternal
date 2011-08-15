package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryLotService;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DEFAULT_HEADERS;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.FULL_INVENTORY_HISTORY_TITLE;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.createInventoryLotServiceCallback;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Widget;

public class FullInventoryHistoryPanel extends LotCodeManagerDialogBox {

	public FullInventoryHistoryPanel() { 
		super (FULL_INVENTORY_HISTORY_TITLE, false, false);
		createInventoryLotService().getAll(createInventoryLotServiceCallback(this));
		center();
	}

	@Override
	Widget[] getHeaderWidgets() {
		return new Widget[0];
	}

	@Override
	CellTable<InventoryLotDto> getCellTable() {
		return newMultiSelectionCellTable(DEFAULT_HEADERS);
	}
}
