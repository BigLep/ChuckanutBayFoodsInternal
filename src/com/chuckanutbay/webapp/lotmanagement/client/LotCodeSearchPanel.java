package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.common.client.IconUtil.SEARCH;
import static com.chuckanutbay.webapp.common.client.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createInventoryLotService;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DEFAULT_HEADERS;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.LOT_CODE_SEARCH_TITLE;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.validateAndFormatLotCode;
import static com.chuckanutbay.webapp.lotmanagement.client.RpcHelper.createInventoryLotServiceCallback;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LotCodeSearchPanel extends LotCodeManagerDialogBox {
	Label codeToSearchLabel = new Label("Lot Code to Search:");
	TextBox codeTextBox = new TextBox();
	Button searchButton = createButtonWithIcon(SEARCH, "Search");
	
	public LotCodeSearchPanel() {
		super (LOT_CODE_SEARCH_TITLE, false, true);
		center();
    	codeTextBox.selectAll();
	}
	
	private void searchLotCodes() {
		String lotCode = validateAndFormatLotCode(codeTextBox.getText());
		if (lotCode == null) {
	    	codeTextBox.selectAll();
	    } else { 
	    	createInventoryLotService().getLotCodeMatchInventory(lotCode, createInventoryLotServiceCallback(this));
		}
	}
	
	@Override
	void updateDB() {
		//Nothing to update
	}

	@Override
	Widget[] getHeaderWidgets() {
		//Set Up codeTextBox
		codeTextBox.setText("Enter Lot Code Here ...");
		//Set Up searchButton
		searchButton.setWidth("250px");
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchLotCodes();
		    	codeTextBox.selectAll();
			}
		});
    	return new Widget[] {codeToSearchLabel, codeTextBox, searchButton};
	}

	@Override
	CellTable<InventoryLotDto> getCellTable() {
		return newMultiSelectionCellTable(DEFAULT_HEADERS);
	}
}
