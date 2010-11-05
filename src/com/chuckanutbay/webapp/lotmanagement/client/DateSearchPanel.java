package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_BOX_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.FLEX_TABLE_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makeButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class DateSearchPanel extends LotCodeManagerPanel implements ClickHandler {
	//Checked-In Components
	private VerticalPanel dateSearchPanel = new VerticalPanel();
	private HorizontalPanel dateToSearchPanel = new HorizontalPanel();
	private Label dateToSearchLabel = new Label("Date to Search:");
	private DateBox dateBox = new DateBox();
	private FlexTable dateSearchFlexTable = new FlexTable();
	private Button searchButton = new Button();
	private DialogBox dialogBox;
	private List<InventoryLotDto> dateMatchList = newArrayList();
	private RpcHelper rpcHelper = new RpcHelper();
	
	public DateSearchPanel() {
		setUpPanel();
		rpcHelper.dbGetDateMatchInUseIngredients(dateBox.getValue(), dateMatchList, this);
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
			dateBox.setValue(new Date(), true);
			dateBox.setWidth(DATE_BOX_WIDTH);
			//Set Up searchButton
			makeButtonWithIcon(searchButton, icons.searchIcon(), "Search");
			searchButton.setWidth("250px");
			searchButton.addClickHandler(this);
			//Set Up dateToSearchPanel
			dateToSearchPanel.setSpacing(5);
			dateToSearchPanel.setStyleName("headerPanel");
			//Set Up dateSearchFlexTable
			setupdateSearchFlexTableHeader();
			dateSearchFlexTable.setWidth(FLEX_TABLE_WIDTH);
		//Add components to dateToSearchPanel
		dateToSearchPanel.add(dateToSearchLabel);
		dateToSearchPanel.add(dateBox);
		dateToSearchPanel.add(searchButton);
		//Assemble dateSearchPanel
		dateSearchPanel.setSpacing(5);
		dateSearchPanel.add(dateToSearchPanel);
		dateSearchPanel.add(dateSearchFlexTable);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "Search for Lot-Codes in-use by date", false, true);
	}

	private void setupdateSearchFlexTableHeader() {
		dateSearchFlexTable.setText(0,0,"Lot Code");
		dateSearchFlexTable.setText(0,1,"Ingredient Type");
		dateSearchFlexTable.setText(0,2,"Checked-In Date");
		dateSearchFlexTable.setText(0,3,"In-Use Date");
		dateSearchFlexTable.setText(0,4,"Used-Up Date");
		dateSearchFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		dateSearchFlexTable.addStyleName("FlexTable");
	}

	public Panel getPanel() {
		return dateSearchPanel;
	}
	
	private void searchDates() {
		dateMatchList.clear();
		if (dateBox.getValue() == null) {
			Window.alert("Please enter a date");
		}
		else  rpcHelper.dbGetDateMatchInUseIngredients(dateBox.getValue(), dateMatchList, this);
	}
	
	public void populateFlexTable() {
	    int row = 1;
		for (InventoryLotDto dateMatch : dateMatchList) {
		    // add new row to dateSearchFlexTable
		    dateSearchFlexTable.setText(row,0,dateMatch.getCode());
			dateSearchFlexTable.setText(row,1,dateMatch.getInventoryItem().getDescription());
			dateSearchFlexTable.setText(row,2,dateFormat.format(dateMatch.getReceivedDatetime()));
			dateSearchFlexTable.setText(row,3,dateFormat.format(dateMatch.getStartUseDatetime()));
			if (dateMatch.getEndUseDatetime() != null) {
				dateSearchFlexTable.setText(row,4,dateFormat.format(dateMatch.getEndUseDatetime()));
			}
			else dateSearchFlexTable.setText(row,4,"In-Use");
			row++;
		}
	}

	private void resetdateSearchFlexTable() {
		dateSearchFlexTable.removeAllRows();
		setupdateSearchFlexTableHeader();
	}
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == searchButton) {
			resetdateSearchFlexTable();
			searchDates();
		}
	}

	void updateDB() {
		// No update necessary
	}
}
