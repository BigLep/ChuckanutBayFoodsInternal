package com.chuckanutbay.LotCodeManager.client;
import static com.chuckanutbay.LotCodeManager.client.LotCodeUtil.*;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class LotCodeSearchPanel extends LotCodeManagerPanel implements ClickHandler {
	//Checked-In Components
	VerticalPanel lotCodeSearchPanel = new VerticalPanel();
	HorizontalPanel codeToSearchPanel = new HorizontalPanel();
	Label codeToSearchLabel = new Label("Lot Code to Search:");
	TextBox codeTextBox = new TextBox();
	FlexTable lotCodeSearchFlexTable = new FlexTable();
	Button searchButton = new Button();
	DialogBox dialogBox;
	ArrayList<ItemInInventory> lotCodeMatchList = new ArrayList<ItemInInventory>();
	
	public LotCodeSearchPanel() {
		dbGetLotCodeMatchIngredients("", lotCodeMatchList, this);
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
			lotCodeSearchFlexTable.setWidth("600px");
		//Add components to codeToSearchPanel
		codeToSearchPanel.add(codeToSearchLabel);
		codeToSearchPanel.add(codeTextBox);
		codeToSearchPanel.add(searchButton);
		//Assemble lotCodeSearchPanel
		lotCodeSearchPanel.setSpacing(5);
		lotCodeSearchPanel.add(codeToSearchPanel);
		lotCodeSearchPanel.add(lotCodeSearchFlexTable);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "Search for Lot-Codes", true, true);
		//Highlight codeTextBox
    	codeTextBox.selectAll();
	}

	private void setupLotCodeSearchFlexTableHeader() {
		lotCodeSearchFlexTable.setText(0,0,"Lot Code");
		lotCodeSearchFlexTable.setText(0,1,"Ingredient Type");
		lotCodeSearchFlexTable.setText(0,2,"Checked-In Date");
		lotCodeSearchFlexTable.setText(0,3,"In-Use Date");
		lotCodeSearchFlexTable.setText(0,4,"Used-Up Date");
		lotCodeSearchFlexTable.getRowFormatter().addStyleName(0, "lotCodeSearchFlexTableHeader");
		lotCodeSearchFlexTable.addStyleName("lotCodeSearchFlexTable");
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
			dbGetLotCodeMatchIngredients(lotCode, lotCodeMatchList, this);
			if (lotCodeMatchList.isEmpty()) {
				Window.alert("There are no items with Lot Code " + lotCode);
				codeTextBox.selectAll();
			}
		}
	}
	
	private void populateLotCodeSearchFlexTable() {
	    final DateTimeFormat dateFormat = DateTimeFormat.getShortDateFormat();
	    int row = 1;
		for (ItemInInventory lotCodeMatch : lotCodeMatchList) {
		    // add new row to lotCodeSearchFlexTable
		    lotCodeSearchFlexTable.setText(row,0,lotCodeMatch.getLotCode());
			lotCodeSearchFlexTable.setText(row,1,lotCodeMatch.getItemType());
			if (lotCodeMatch.getCheckedInDate() != null) {
				lotCodeSearchFlexTable.setText(row,2,dateFormat.format(lotCodeMatch.getCheckedInDate()));
			}
			if (lotCodeMatch.getInUseDate() != null) {
				lotCodeSearchFlexTable.setText(row,3,dateFormat.format(lotCodeMatch.getInUseDate()));
			}
			if (lotCodeMatch.getUsedUpDate() != null) {
				lotCodeSearchFlexTable.setText(row,4,dateFormat.format(lotCodeMatch.getUsedUpDate()));
			}
			row++;
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
			populateLotCodeSearchFlexTable();
			highlightLotCodeBox();
		}
	}

	void updateDB() {
		//Nothing to update
	}
}
