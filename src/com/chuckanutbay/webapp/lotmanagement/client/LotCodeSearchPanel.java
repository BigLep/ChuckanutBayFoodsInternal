package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.FLEX_TABLE_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makeButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.ArrayList;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LotCodeSearchPanel extends LotCodeManagerPanel implements ClickHandler {
	//Checked-In Components
	VerticalPanel lotCodeSearchPanel = new VerticalPanel();
	HorizontalPanel codeToSearchPanel = new HorizontalPanel();
	Label codeToSearchLabel = new Label("Lot Code to Search:");
	TextBox codeTextBox = new TextBox();
	FlexTable lotCodeSearchFlexTable = new FlexTable();
	Button searchButton = new Button();
	DialogBox dialogBox;
	ArrayList<InventoryLotDto> lotCodeMatchList = newArrayList();
	RpcHelper rpcHelper = new RpcHelper();
	
	public LotCodeSearchPanel() {
		setUpPanel();
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up codeTextBox
			codeTextBox.setText("Enter Lot Code Here ...");
			//Set Up searchButton
			makeButtonWithIcon(searchButton, icons.searchIcon(), "Search");
			searchButton.setWidth("250px");
			searchButton.addClickHandler(this);
			//Set Up codeToSearchPanel
			codeToSearchPanel.setSpacing(5);
			codeToSearchPanel.setStyleName("headerPanel");
			//Set Up lotCodeSearchFlexTable
			setupLotCodeSearchFlexTableHeader();
			lotCodeSearchFlexTable.setWidth(FLEX_TABLE_WIDTH);
		//Add components to codeToSearchPanel
		codeToSearchPanel.add(codeToSearchLabel);
		codeToSearchPanel.add(codeTextBox);
		codeToSearchPanel.add(searchButton);
		//Assemble lotCodeSearchPanel
		lotCodeSearchPanel.setSpacing(5);
		lotCodeSearchPanel.add(codeToSearchPanel);
		lotCodeSearchPanel.add(lotCodeSearchFlexTable);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "Search for Lot-Codes", false, true);
		//Highlight codeTextBox
    	codeTextBox.selectAll();
	}

	private void setupLotCodeSearchFlexTableHeader() {
		lotCodeSearchFlexTable.setText(0,0,"Lot Code");
		lotCodeSearchFlexTable.setText(0,1,"Ingredient Type");
		lotCodeSearchFlexTable.setText(0,2,"Checked-In Date");
		lotCodeSearchFlexTable.setText(0,3,"In-Use Date");
		lotCodeSearchFlexTable.setText(0,4,"Used-Up Date");
		lotCodeSearchFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		lotCodeSearchFlexTable.addStyleName("FlexTable");
	}

	public Panel getPanel() {
		return lotCodeSearchPanel;
	}
	
	public void highlightLotCodeBox() {
		codeTextBox.selectAll();
	}
	
	private void searchLotCodes() {
		lotCodeMatchList.clear();
		String lotCode = codeTextBox.getText().toUpperCase().trim();
		// lot code must be between 1 and 20 chars that are numbers, or letters.
		if (!lotCode.matches("[0-9A-Z\\s]{1,20}")) {
	    	Window.alert("'" + lotCode + "' is not a valid Lot Code.");
	    	codeTextBox.selectAll();
	    }
		else { 
			rpcHelper.dbGetLotCodeMatchIngredients(lotCode, lotCodeMatchList, this);
		}
	}
	
	public void populateFlexTable() {
	    if (lotCodeMatchList.isEmpty()) {
	    	Window.alert("There are no matching Lot Codes");
	    	codeTextBox.selectAll();
	    }
	    else {
		    int row = 1;
			for (InventoryLotDto lotCodeMatch : lotCodeMatchList) {
			    // add new row to lotCodeSearchFlexTable
			    lotCodeSearchFlexTable.setText(row,0,lotCodeMatch.getCode());
				lotCodeSearchFlexTable.setText(row,1,lotCodeMatch.getInventoryItem().getDescription());
				if (lotCodeMatch.getReceivedDatetime() != null) {
					lotCodeSearchFlexTable.setText(row,2,dateFormat.format(lotCodeMatch.getReceivedDatetime()));
				}
				if (lotCodeMatch.getStartUseDatetime() != null) {
					lotCodeSearchFlexTable.setText(row,3,dateFormat.format(lotCodeMatch.getStartUseDatetime()));
				}
				if (lotCodeMatch.getEndUseDatetime() != null) {
					lotCodeSearchFlexTable.setText(row,4,dateFormat.format(lotCodeMatch.getEndUseDatetime()));
				}
				row++;
			}
	    }
	}

	private void resetLotCodeSearchFlexTable() {
		lotCodeSearchFlexTable.removeAllRows();
		setupLotCodeSearchFlexTableHeader();
	}
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == searchButton) {
			resetLotCodeSearchFlexTable();
			searchLotCodes();
			highlightLotCodeBox();
		}
	}

	void updateDB() {
		//Nothing to update
	}
}
