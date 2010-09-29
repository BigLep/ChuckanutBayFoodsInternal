package com.chuckanutbay.LotCodeManager.client;
import static com.chuckanutbay.LotCodeManager.client.LotCodeUtil.*;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class FullInventoryHistoryPanel extends LotCodeManagerPanel implements ClickHandler {
	//Checked-In Components
	VerticalPanel viewFullIngredientHistoryPanel = new VerticalPanel();
	Label currentlyViewingLabel = new Label();
	FlexTable viewFullIngredientHistoryFlexTable = new FlexTable();
	Button nextButton = new Button();
	DialogBox dialogBox;
	int itemIndex = 0;
	int rowsAdded = 0;
	ArrayList<ItemInInventory> itemInInventoryList = new ArrayList<ItemInInventory>();
	
	public FullInventoryHistoryPanel() { 
		dbGetFullIngredientHistory(itemInInventoryList, this);
	}
	
	public void setUpPanel() {
		//Set Up Components
		//Set Up inUseIngredientFlexTable
		setupInUseIngredientFlexTable();
		viewFullIngredientHistoryFlexTable.setWidth("600px");
		populateFlexTable();
		//Set Up saveButton
		makeButtonWithIcon(nextButton, icons.refreshIcon(), "Next 25");
		nextButton.setWidth("600px");
		nextButton.addClickHandler(this);
		//Assemble inUseIngredientPanel
		viewFullIngredientHistoryPanel.setSpacing(5);
		viewFullIngredientHistoryPanel.add(currentlyViewingLabel);
		viewFullIngredientHistoryPanel.add(viewFullIngredientHistoryFlexTable);
		viewFullIngredientHistoryPanel.add(nextButton);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "View Full Inventory History", true, true);
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
	
	private void populateFlexTable() {
		if (itemInInventoryList.isEmpty()) {
			Window.alert("There are no records to view");
		}
		else {
			rowsAdded = 0;
			for (int row = 1; row <= 25; row++) {
				if (itemInInventoryList.size() >=  (row + itemIndex)) {
				    // add new row to inUseIngredientFlexTable
					viewFullIngredientHistoryFlexTable.setText(row,0,itemInInventoryList.get((row - 1) + itemIndex).getLotCode());
					viewFullIngredientHistoryFlexTable.setText(row,1,itemInInventoryList.get((row - 1) + itemIndex).getItemType());
					if (itemInInventoryList.get((row - 1) + itemIndex).getCheckedInDate() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,2,dateFormat.format(itemInInventoryList.get((row - 1) + itemIndex).getCheckedInDate()));
					}
					if (itemInInventoryList.get((row - 1) + itemIndex).getInUseDate() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,3,dateFormat.format(itemInInventoryList.get((row - 1) + itemIndex).getInUseDate()));
					}
					if (itemInInventoryList.get((row - 1) + itemIndex).getUsedUpDate() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,4,dateFormat.format(itemInInventoryList.get((row - 1) + itemIndex).getUsedUpDate()));
					}
					rowsAdded++;
				}
			}
			if (rowsAdded > 0) {
				currentlyViewingLabel.setText("Currently Viewing " + (1 + itemIndex) + " - " + (rowsAdded + itemIndex));
				currentlyViewingLabel.setWidth("590px");
				currentlyViewingLabel.setStyleName("headerPanel");
			}
			itemIndex += 25;
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
}
