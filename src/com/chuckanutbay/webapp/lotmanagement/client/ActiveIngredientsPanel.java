package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_BOX_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.FLEX_TABLE_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ActiveIngredientsPanel extends LotCodeManagerPanel {
	//Checked-In Components
	private VerticalPanel activeIngredientsPanel = new VerticalPanel();
	private HorizontalPanel dateToSearchPanel = new HorizontalPanel();
	private Label dateToSearchLabel = new Label("Today's Date:");
	private TextBox dateBox = new TextBox();
	private FlexTable activeIngredientsFlexTable = new FlexTable();
	private DialogBox dialogBox;
	private List<InventoryLotDto> activeIngredientList = newArrayList();
	private RpcHelper rpcHelper = new RpcHelper();
	
	public ActiveIngredientsPanel() { 
		setUpPanel();
		rpcHelper.dbGetInUseIngredients(activeIngredientList, this);
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up dateBox
			dateBox.setText(dateFormat.format(new Date()));
			dateBox.setReadOnly(true);
			dateBox.setWidth(DATE_BOX_WIDTH);
			//Set Up dateToSearchPanel
			dateToSearchPanel.setSpacing(5);
			dateToSearchPanel.setStyleName("headerPanel");
			//Set Up activeIngredientsFlexTable
			setupactiveIngredientsFlexTableHeader();
			activeIngredientsFlexTable.setWidth(FLEX_TABLE_WIDTH);
		//Add components to dateToSearchPanel
		dateToSearchPanel.add(dateToSearchLabel);
		dateToSearchPanel.add(dateBox);
		//Assemble activeIngredientsPanel
		activeIngredientsPanel.setSpacing(5);
		activeIngredientsPanel.add(dateToSearchPanel);
		activeIngredientsPanel.add(activeIngredientsFlexTable);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "View Active Ingredients", false, true);
	}

	private void setupactiveIngredientsFlexTableHeader() {
		activeIngredientsFlexTable.setText(0,0,"Lot Code");
		activeIngredientsFlexTable.setText(0,1,"Ingredient Type");
		activeIngredientsFlexTable.setText(0,2,"Checked-In Date");
		activeIngredientsFlexTable.setText(0,3,"In-Use Date");
		activeIngredientsFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
		activeIngredientsFlexTable.addStyleName("FlexTable");
		
	}

	public Panel getPanel() {
		return activeIngredientsPanel;
	}
	
	public void populateFlexTable() {
		if(activeIngredientList.isEmpty()){
			Window.alert("There are no Active Ingredients");
		}
		else {
		    int row = 1;
			for (InventoryLotDto activeIngredient : activeIngredientList) {
			    // add new row to activeIngredientsFlexTable
			    activeIngredientsFlexTable.setText(row,0,activeIngredient.getCode());
				activeIngredientsFlexTable.setText(row,1,activeIngredient.getInventoryItem().getDescription());
				activeIngredientsFlexTable.setText(row,2,dateFormat.format(activeIngredient.getReceivedDatetime()));
				activeIngredientsFlexTable.setText(row,3,dateFormat.format(activeIngredient.getStartUseDatetime()));
				row++;
			}
		}
	}

	void updateDB() {
		// Nothing to Update
	}
}