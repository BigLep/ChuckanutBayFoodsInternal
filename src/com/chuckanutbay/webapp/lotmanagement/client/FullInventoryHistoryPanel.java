package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.FLEX_TABLE_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makeButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.List;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
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
	private VerticalPanel viewFullIngredientHistoryPanel = new VerticalPanel();
	private HorizontalPanel	headerPanel = new HorizontalPanel();
	private Label currentlyViewingLabel = new Label();
	private Label visibleRowsLabel = new Label("Visible Rows");
	private ListBox visibleRowsListBox = new ListBox();
	private FlexTable viewFullIngredientHistoryFlexTable = new FlexTable();
	private Button backButton = new Button();
	private Button nextButton = new Button();
	private HorizontalPanel	buttonsPanel = new HorizontalPanel();
	private DialogBox dialogBox;
	private int visibleRows = 10;
	private int itemIndex = 0;
	private int rowsAdded = 0;
	private List<InventoryLotDto> itemInInventoryList = newArrayList();
	private RpcHelper rpcHelper = new RpcHelper();
	
	private static final String NUMBER_OF_VISIBLE_ROWS[] = {"10", "25", "50", "100"};
	private static final String NAVIGATION_BUTTON_WIDTH = "400px";
	
	
	public FullInventoryHistoryPanel() { 
		setUpPanel();
		rpcHelper.dbGetFullIngredientHistory(itemInInventoryList, this);
	}
	
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
		viewFullIngredientHistoryFlexTable.setWidth(FLEX_TABLE_WIDTH);
		//Set Up buttonsPanel
		buttonsPanel.setSpacing(5);
		//Set Up backButton
		makeButtonWithIcon(backButton, icons.backIcon(), "Back");
		backButton.setWidth(NAVIGATION_BUTTON_WIDTH);
		backButton.addClickHandler(this);
		buttonsPanel.add(backButton);
		//Set Up nextButton
		makeButtonWithIcon(nextButton, icons.nextIcon(), "Next");
		nextButton.setWidth(NAVIGATION_BUTTON_WIDTH);
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
		viewFullIngredientHistoryFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		viewFullIngredientHistoryFlexTable.addStyleName("FlexTable");
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
