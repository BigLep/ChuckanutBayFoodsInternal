package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryLotService;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.USED_UP_HEADERS;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.USED_UP_TITLE;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.VOID_CALLBACK;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.createInventoryLotServiceCallback;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class UsedUpPanel extends LotCodeManagerDialogBox implements SelectionChangeEvent.Handler {
	private final Label dateBoxLabel = new Label("Used Up Date:");
	private final DateBox dateBox = new DateBox();
	
	public UsedUpPanel() {
		super(USED_UP_TITLE, true, true);
		createInventoryLotService().getInUseInventoryLots(createInventoryLotServiceCallback(this));
		center();
		addSelectionChangeHandler(this);
	}
	
	@Override
	Widget[] getHeaderWidgets() {
		dateBox.setFormat(new DateBox.DefaultFormat(DATE_FORMAT));
		dateBox.setValue(new Date(), true);
		dateBox.setStyleName("dateBox");
		return new Widget[] {dateBoxLabel, dateBox};
	}
	
	@Override
	void updateDB() {
		MultiSelectionModel<InventoryLotDto> model = (MultiSelectionModel<InventoryLotDto>) getSelectionModel();
		createInventoryLotService().setUsedUpInventoryLots(newArrayList(model.getSelectedSet()), VOID_CALLBACK);
	}

	@Override
	CellTable<InventoryLotDto> getCellTable() {
		return newMultiSelectionCellTable(USED_UP_HEADERS);
	}
	
	@Override
	public void onSelectionChange(SelectionChangeEvent event) {
		MultiSelectionModel<InventoryLotDto> model = (MultiSelectionModel<InventoryLotDto>) getSelectionModel();
		for (InventoryLotDto dto : cellTableData) {
			dto.setEndUseDatetime(null);
		}
		for (InventoryLotDto dto : model.getSelectedSet()) {
			dto.setEndUseDatetime(dateBox.getValue());
		}
		cellTable.redraw();
	}
}
