package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.*;

import java.util.ArrayList;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class FullInventoryHistoryPanel extends LotCodeManagerPanel implements ClickHandler, ChangeHandler {
	//Checked-In Components
	VerticalPanel viewFullIngredientHistoryPanel = new VerticalPanel();
	HorizontalPanel	headerPanel = new HorizontalPanel();
	Label currentlyViewingLabel = new Label();
	Label visibleRowsLabel = new Label("Visible Rows");
	ListBox visibleRowsListBox = new ListBox();
	FlexTable viewFullIngredientHistoryFlexTable = new FlexTable();
	Button backButton = new Button();
	Button nextButton = new Button();
	HorizontalPanel	buttonsPanel = new HorizontalPanel();
	DialogBox dialogBox;
	int visibleRows = 10;
	int itemIndex = 0;
	int rowsAdded = 0;
	ArrayList<InventoryLotDto> itemInInventoryList = new ArrayList<InventoryLotDto>();
	
	public FullInventoryHistoryPanel() { 
		setUpPanel();
		dbGetFullIngredientHistory(itemInInventoryList, this);
	}
	
	public void setUpPanel() {
		//Set Up Components
		visibleRowsListBox.addItem("10");
		visibleRowsListBox.addItem("25");
		visibleRowsListBox.addItem("50");
		visibleRowsListBox.addItem("100");
		visibleRowsListBox.addChangeHandler(this);
		//Set Up headerPanel
		headerPanel.add(currentlyViewingLabel);
		headerPanel.add(visibleRowsLabel);
		headerPanel.add(visibleRowsListBox);
		headerPanel.setStyleName("headerPanel");
		//Set Up inUseIngredientFlexTable
		setupInUseIngredientFlexTable();
		viewFullIngredientHistoryFlexTable.setWidth("600px");
		//Set Up buttonsPanel
		buttonsPanel.setSpacing(5);
		//Set Up backButton
		makeButtonWithIcon(backButton, icons.backIcon(), "Back");
		backButton.setWidth("295px");
		backButton.addClickHandler(this);
		buttonsPanel.add(backButton);
		//Set Up nextButton
		makeButtonWithIcon(nextButton, icons.nextIcon(), "Next");
		nextButton.setWidth("295px");
		nextButton.addClickHandler(this);
		buttonsPanel.add(nextButton);
		//Assemble inUseIngredientPanel
		viewFullIngredientHistoryPanel.setSpacing(5);
		viewFullIngredientHistoryPanel.add(headerPanel);
		viewFullIngredientHistoryPanel.add(viewFullIngredientHistoryFlexTable);
		viewFullIngredientHistoryPanel.add(buttonsPanel);
		headerPanel.setCellHorizontalAlignment(visibleRowsLabel, HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.setCellHorizontalAlignment(visibleRowsListBox, HasHorizontalAlignment.ALIGN_LEFT);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "View Full Inventory History", false, true);
	}

	private void setupInUseIngredientFlexTable() {
		viewFullIngredientHistoryFlexTable.setText(0,0,"Lot Code");
		viewFullIngredientHistoryFlexTable.setText(0,1,"Ingredient Type");
		viewFullIngredientHistoryFlexTable.setText(0,2,"Checked-In Date");
		viewFullIngredientHistoryFlexTable.setText(0,3,"In-Use Date");
		viewFullIngredientHistoryFlexTable.setText(0,4,"Used-Up Date");
		viewFullIngredientHistoryFlexTable.getRowFormatter().addStyleName(0, "viewFullIngredientHistoryFlexTableHeader");
		viewFullIngredientHistoryFlexTable.addStyleName("viewFullIngredientHistoryFlexTable");
	}
	
	public void populateFlexTable() {
		if (itemInInventoryList.isEmpty()) {
			Window.alert("There are no records to view");
		}
		else {
			rowsAdded = 0;
			for (int row = 1; row <= visibleRows; row++) {
				if (itemInInventoryList.size() >=  (row + itemIndex)) {
				    // add new row to inUseIngredientFlexTable
					viewFullIngredientHistoryFlexTable.setText(row,0,itemInInventoryList.get((row - 1) + itemIndex).getCode());
					viewFullIngredientHistoryFlexTable.setText(row,1,itemInInventoryList.get((row - 1) + itemIndex).getInventoryItem().getDescription());
					if (itemInInventoryList.get((row - 1) + itemIndex).getReceivedDatetime() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,2,dateFormat.format(itemInInventoryList.get((row - 1) + itemIndex).getReceivedDatetime()));
					}
					if (itemInInventoryList.get((row - 1) + itemIndex).getStartUseDatetime() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,3,dateFormat.format(itemInInventoryList.get((row - 1) + itemIndex).getStartUseDatetime()));
					}
					if (itemInInventoryList.get((row - 1) + itemIndex).getEndUseDatetime() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,4,dateFormat.format(itemInInventoryList.get((row - 1) + itemIndex).getEndUseDatetime()));
					}
					rowsAdded++;
				}
			}
			if (rowsAdded > 0) {
				currentlyViewingLabel.setText("Currently Viewing " + (1 + itemIndex) + " - " + (rowsAdded + itemIndex) + " of " + itemInInventoryList.size());
			}
			itemIndex += visibleRows;
		}
	}
	
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == nextButton) {
			if (itemInInventoryList.size() > itemIndex) {
				resetIngredientHistoryFlexTable();
				populateFlexTable();
			} else {
				Window.alert("No More Records");
			}
		}
		if (sender == backButton) {
			if ((itemIndex - (2 * visibleRows)) >= 0) {
				itemIndex -= (2 * visibleRows);
				resetIngredientHistoryFlexTable();
				populateFlexTable();
			} else {
				itemIndex = 0;
				resetIngredientHistoryFlexTable();
				populateFlexTable();
			}
		}
	}
	
	private void resetIngredientHistoryFlexTable() {
		viewFullIngredientHistoryFlexTable.removeAllRows();
		setupInUseIngredientFlexTable();
	}

	public Panel getPanel() {
		return viewFullIngredientHistoryPanel;
	}

	void updateDB() {
		// Nothing to Update
	}

	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == visibleRowsListBox) {
			visibleRows = Integer.parseInt(visibleRowsListBox.getItemText(visibleRowsListBox.getSelectedIndex()));
			itemIndex = 0;
			populateFlexTable();
		}
	}
}
