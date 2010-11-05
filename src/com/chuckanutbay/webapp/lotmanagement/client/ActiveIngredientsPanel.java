package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

public class ActiveIngredientsPanel extends LotCodeManagerPanel {
	//Checked-In Components
	VerticalPanel activeIngredientsPanel = new VerticalPanel();
	HorizontalPanel dateToSearchPanel = new HorizontalPanel();
	Label dateToSearchLabel = new Label("Today's Date:");
	TextBox dateBox = new TextBox();
	FlexTable activeIngredientsFlexTable = new FlexTable();
	DialogBox dialogBox;
	List<InventoryLotDto> activeIngredientList = newArrayList();
	RpcHelper rpcHelper = new RpcHelper();
	
	public ActiveIngredientsPanel() { 
		setUpPanel();
		rpcHelper.dbGetInUseIngredients(activeIngredientList, this);
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up dateBox
			dateBox.setText(dateFormat.format(new Date()));
			dateBox.setReadOnly(true);
			dateBox.setWidth("80px");
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