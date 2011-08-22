package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.common.client.IconUtil.SEARCH;
import static com.chuckanutbay.webapp.common.client.IconUtil.addIcon;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryLotService;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_SEARCH_TITLE;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DEFAULT_HEADERS;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.createInventoryLotServiceCallback;

import java.util.Date;

import com.chuckanutbay.webapp.common.client.CbCellTable;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class DateSearchPanel extends LotCodeManagerDialogBox {
	private final Label dateToSearchLabel = new Label("Date to Search:");
	private final DateBox dateBox = new DateBox();
	private final Button searchButton = new Button();
	
	public DateSearchPanel() {
		super (DATE_SEARCH_TITLE, false, false);
		createInventoryLotService().getInUse(createInventoryLotServiceCallback(this));
		center();
	}

	@Override
	Widget[] getHeaderWidgets() {//Set Up dateBox
		dateBox.setFormat(new DateBox.DefaultFormat(DATE_FORMAT));
		dateBox.setValue(new Date(), true);
		dateBox.setStyleName("dateBox");
		//Set Up searchButton
		addIcon(searchButton, SEARCH, "Search");
		searchButton.setWidth("250px");
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (dateBox.getValue() == null) {
					Window.alert("Please enter a date");
				} else {
					createInventoryLotService().getInUseOnDate(dateBox.getValue(), createInventoryLotServiceCallback(DateSearchPanel.this));
				}
			}
		});
		return new Widget[] {dateToSearchLabel, dateBox, searchButton};
	}

	@Override
	CbCellTable<InventoryLotDto> getCellTable() {
		return newMultiSelectionCellTable(DEFAULT_HEADERS);
	}
}
