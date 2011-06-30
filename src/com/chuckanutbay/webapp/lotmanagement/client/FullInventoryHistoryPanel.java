package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.common.shared.IconUtil.BACK;
import static com.chuckanutbay.webapp.common.shared.IconUtil.NEXT;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_FORMAT;
import static com.chuckanutbay.webapp.common.shared.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.List;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FullInventoryHistoryPanel extends LotCodeManagerPanel implements ClickHandler, ChangeHandler {
	//Checked-In Components
	private final VerticalPanel viewFullIngredientHistoryPanel = new VerticalPanel();
	private final HorizontalPanel	headerPanel = new HorizontalPanel();
	private final Label currentlyViewingLabel = new Label();
	private final Label visibleRowsLabel = new Label("Visible Rows");
	private final ListBox visibleRowsListBox = new ListBox();
	private final FlexTable viewFullIngredientHistoryFlexTable = new FlexTable();
	private final Button backButton = new Button();
	private final Button nextButton = new Button();
	private final HorizontalPanel	buttonsPanel = new HorizontalPanel();
	private DialogBox dialogBox;
	private int visibleRows = 10;
	private int itemIndex = 0;
	private int rowsAdded = 0;
	private final List<InventoryLotDto> itemInInventoryList = newArrayList();
	private final RpcHelper rpcHelper = new RpcHelper();
	
	private static final String NUMBER_OF_VISIBLE_ROWS[] = {"10", "25", "50", "100"};
	
	
	public FullInventoryHistoryPanel() { 
		setUpPanel();
		rpcHelper.dbGetFullIngredientHistory(itemInInventoryList, this);
	}
	
	@Override
	public void setUpPanel() {
		for(String visibleRowsOption : NUMBER_OF_VISIBLE_ROWS) {
			visibleRowsListBox.addItem(visibleRowsOption);
		}
		visibleRowsListBox.addChangeHandler(this);
		//Set Up headerPanel
		headerPanel.add(currentlyViewingLabel);
		headerPanel.add(visibleRowsLabel);
		headerPanel.add(visibleRowsListBox);
		headerPanel.setStyleName("headerPanel");
		//Set Up inUseIngredientFlexTable
		setupInUseIngredientFlexTable();
		//Set Up buttonsPanel
		buttonsPanel.setSpacing(5);
		//Set Up buttons
		createButtonWithIcon(backButton, BACK, "Back");
		createButtonWithIcon(nextButton, NEXT, "Next");
		LotCodeUtil.addClickHandler(this, backButton, nextButton);
		backButton.setStyleName("navigationButton");
		nextButton.setStyleName("navigationButton");
		buttonsPanel.add(backButton);
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
		viewFullIngredientHistoryFlexTable.setText(0,2,"Quantity");
		viewFullIngredientHistoryFlexTable.setText(0,3,"Checked-In Date");
		viewFullIngredientHistoryFlexTable.setText(0,3,"In-Use Date");
		viewFullIngredientHistoryFlexTable.setText(0,5,"Used-Up Date");
		viewFullIngredientHistoryFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		viewFullIngredientHistoryFlexTable.addStyleName("FlexTable");
	}
	
	@Override
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
					viewFullIngredientHistoryFlexTable.setText(row,2,Integer.toString(itemInInventoryList.get((row - 1) + itemIndex).getQuantity()));
					if (itemInInventoryList.get((row - 1) + itemIndex).getReceivedDatetime() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,3,DATE_FORMAT.format(itemInInventoryList.get((row - 1) + itemIndex).getReceivedDatetime()));
					}
					if (itemInInventoryList.get((row - 1) + itemIndex).getStartUseDatetime() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,4,DATE_FORMAT.format(itemInInventoryList.get((row - 1) + itemIndex).getStartUseDatetime()));
					}
					if (itemInInventoryList.get((row - 1) + itemIndex).getEndUseDatetime() != null) {
						viewFullIngredientHistoryFlexTable.setText(row,5,DATE_FORMAT.format(itemInInventoryList.get((row - 1) + itemIndex).getEndUseDatetime()));
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
	
	@Override
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

	@Override
	public Panel getPanel() {
		return viewFullIngredientHistoryPanel;
	}

	@Override
	void updateDB() {
		// Nothing to Update
	}

	@Override
	public void onChange(ChangeEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == visibleRowsListBox) {
			visibleRows = Integer.parseInt(visibleRowsListBox.getItemText(visibleRowsListBox.getSelectedIndex()));
			itemIndex = 0;
			populateFlexTable();
		}
	}
}
