package com.chuckanutbay.LotCodeManager.client;

import static com.chuckanutbay.LotCodeManager.client.LotCodeUtil.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class ActiveIngredientsPanel extends LotCodeManagerPanel {
	//Checked-In Components
	VerticalPanel activeIngredientsPanel = new VerticalPanel();
	HorizontalPanel dateToSearchPanel = new HorizontalPanel();
	Label dateToSearchLabel = new Label("Today's Date:");
	TextBox dateBox = new TextBox();
	FlexTable activeIngredientsFlexTable = new FlexTable();
	DialogBox dialogBox;
	ArrayList<ItemInInventory> activeIngredientList = new ArrayList<ItemInInventory>();
	
	public ActiveIngredientsPanel() { 
		dbGetInUseIngredients(activeIngredientList, this);
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up dateBox
			dateBox.setText(dateFormat.format(new Date()));
			dateBox.setReadOnly(true);
			dateBox.setWidth("70px");
			//Set Up dateToSearchPanel
			dateToSearchPanel.setSpacing(5);
			dateToSearchPanel.setStyleName("headerPanel");
			//Set Up activeIngredientsFlexTable
			setupactiveIngredientsFlexTableHeader();
			activeIngredientsFlexTable.setWidth("600px");
		//Add components to dateToSearchPanel
		dateToSearchPanel.add(dateToSearchLabel);
		dateToSearchPanel.add(dateBox);
		//Assemble activeIngredientsPanel
		activeIngredientsPanel.setSpacing(5);
		activeIngredientsPanel.add(dateToSearchPanel);
		activeIngredientsPanel.add(activeIngredientsFlexTable);
		populateActiveIngredientsFlexTable();
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "View Active Ingredients", true, true);
	}

	private void setupactiveIngredientsFlexTableHeader() {
		activeIngredientsFlexTable.setText(0,0,"Lot Code");
		activeIngredientsFlexTable.setText(0,1,"Ingredient Type");
		activeIngredientsFlexTable.setText(0,2,"Checked-In Date");
		activeIngredientsFlexTable.setText(0,3,"In-Use Date");
		activeIngredientsFlexTable.getRowFormatter().addStyleName(0, "activeIngredientsFlexTableHeader");
		activeIngredientsFlexTable.addStyleName("activeIngredientsFlexTable");
		
	}

	public Panel getPanel() {
		return activeIngredientsPanel;
	}
	
	private void populateActiveIngredientsFlexTable() {
		if(activeIngredientList.isEmpty()){
			Window.alert("There are no Active Ingredients");
		}
		else {
		    int row = 1;
			for (ItemInInventory activeIngredient : activeIngredientList) {
			    // add new row to activeIngredientsFlexTable
			    activeIngredientsFlexTable.setText(row,0,activeIngredient.getLotCode());
				activeIngredientsFlexTable.setText(row,1,activeIngredient.getItemType());
				activeIngredientsFlexTable.setText(row,2,dateFormat.format(activeIngredient.getCheckedInDate()));
				activeIngredientsFlexTable.setText(row,3,dateFormat.format(activeIngredient.getInUseDate()));
				row++;
			}
		}
	}

	void updateDB() {
		// Nothing to Update
	}
}