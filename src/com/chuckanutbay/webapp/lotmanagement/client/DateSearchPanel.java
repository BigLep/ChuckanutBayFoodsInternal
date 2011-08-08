package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.common.client.IconUtil.SEARCH;
import static com.chuckanutbay.webapp.common.client.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Date;
import java.util.List;

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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class DateSearchPanel extends LotCodeManagerPanel implements ClickHandler {
	//Checked-In Components
	private final VerticalPanel dateSearchPanel = new VerticalPanel();
	private final HorizontalPanel dateToSearchPanel = new HorizontalPanel();
	private final Label dateToSearchLabel = new Label("Date to Search:");
	private final DateBox dateBox = new DateBox();
	private final FlexTable dateSearchFlexTable = new FlexTable();
	private final Button searchButton = new Button();
	private DialogBox dialogBox;
	private final List<InventoryLotDto> dateMatchList = newArrayList();
	private final RpcHelper rpcHelper = new RpcHelper();
	
	public DateSearchPanel() {
		setUpPanel();
		rpcHelper.dbGetDateMatchInUseIngredients(dateBox.getValue(), dateMatchList, this);
	}
	
	@Override
	public void setUpPanel() {
		//Set Up Components
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(DATE_FORMAT));
			dateBox.setValue(new Date(), true);
			dateBox.setStyleName("dateBox");
			//Set Up searchButton
			createButtonWithIcon(searchButton, SEARCH, "Search");
			searchButton.setWidth("250px");
			searchButton.addClickHandler(this);
			//Set Up dateToSearchPanel
			dateToSearchPanel.setSpacing(5);
			dateToSearchPanel.setStyleName("headerPanel");
			//Set Up dateSearchFlexTable
			setupdateSearchFlexTableHeader();
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
		dateSearchFlexTable.setText(0,2,"Quantity");
		dateSearchFlexTable.setText(0,3,"Checked-In Date");
		dateSearchFlexTable.setText(0,4,"In-Use Date");
		dateSearchFlexTable.setText(0,5,"Used-Up Date");
		dateSearchFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		dateSearchFlexTable.addStyleName("FlexTable");
	}

	@Override
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
	
	@Override
	public void populateFlexTable() {
	    int row = 1;
		for (InventoryLotDto dateMatch : dateMatchList) {
		    // add new row to dateSearchFlexTable
		    dateSearchFlexTable.setText(row,0,dateMatch.getCode());
			dateSearchFlexTable.setText(row,1,dateMatch.getInventoryItem().getDescription());
			dateSearchFlexTable.setText(row,2,Integer.toString(dateMatch.getQuantity()));
			dateSearchFlexTable.setText(row,3,DATE_FORMAT.format(dateMatch.getReceivedDatetime()));
			dateSearchFlexTable.setText(row,4,DATE_FORMAT.format(dateMatch.getStartUseDatetime()));
			if (dateMatch.getEndUseDatetime() != null) {
				dateSearchFlexTable.setText(row,5,DATE_FORMAT.format(dateMatch.getEndUseDatetime()));
			}
			else dateSearchFlexTable.setText(row,5,"In-Use");
			row++;
		}
	}

	private void resetdateSearchFlexTable() {
		dateSearchFlexTable.removeAllRows();
		setupdateSearchFlexTableHeader();
	}
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == searchButton) {
			resetdateSearchFlexTable();
			searchDates();
		}
	}

	@Override
	void updateDB() {
		// No update necessary
	}
}
