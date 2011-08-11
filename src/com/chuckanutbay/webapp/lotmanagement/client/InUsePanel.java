package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryLotService;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.IN_USE_HEADERS;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.IN_USE_TITLE;
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

public class InUsePanel extends LotCodeManagerDialogBox implements SelectionChangeEvent.Handler {
	private final Label dateBoxLabel = new Label("In Use Date:");
	private final DateBox dateBox = new DateBox();

	public InUsePanel() {
		super (IN_USE_TITLE, true, true);
		createInventoryLotService().getUnusedInventoryLots(createInventoryLotServiceCallback(this));
		center();
		addSelectionChangeHandler(this);
	}

	@Override
	void updateDB() {
		MultiSelectionModel<InventoryLotDto> model = (MultiSelectionModel<InventoryLotDto>) getSelectionModel();
		createInventoryLotService().setInUseInventoryLots(newArrayList(model.getSelectedSet()), VOID_CALLBACK);
	}

	@Override
	Widget[] getHeaderWidgets() {
		dateBox.setFormat(new DateBox.DefaultFormat(DATE_FORMAT));
		dateBox.setValue(new Date(), true);
		dateBox.setStyleName("dateBox");
		return new Widget[] {dateBoxLabel, dateBox};
	}

	@Override
	CellTable<InventoryLotDto> getCellTable() {
		return newMultiSelectionCellTable(IN_USE_HEADERS);
	}

	@Override
	public void onSelectionChange(SelectionChangeEvent event) {
		MultiSelectionModel<InventoryLotDto> model = (MultiSelectionModel<InventoryLotDto>) getSelectionModel();
		for (InventoryLotDto dto : cellTableData) {
			dto.setStartUseDatetime(null);
		}
		for (InventoryLotDto dto : model.getSelectedSet()) {
			dto.setStartUseDatetime(dateBox.getValue());
		}
		cellTable.redraw();
	}
}
