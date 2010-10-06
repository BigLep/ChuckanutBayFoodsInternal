package com.chuckanutbay.LotCodeManager.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.chuckanutbay.LotCodeManager.client.LotCodeUtil.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;

public class UsedUpPanel extends LotCodeManagerPanel {
	//Checked-In Components
	VerticalPanel usedUpIngredientPanel = new VerticalPanel();
	HorizontalPanel usedUpDatePanel = new HorizontalPanel();
	Label dateBoxLabel = new Label("In Use Date:");
	DateBox dateBox = new DateBox();
	DialogBox dialogBox;
	FlexTable usedUpIngredientFlexTable = new FlexTable();
	List<ItemInInventory> usedUpIngredientList = new ArrayList<ItemInInventory>();
	
	public UsedUpPanel() {
		setUpPanel();
		dbGetInUseIngredients(usedUpIngredientList, this);
	}
	
	public void setUpPanel() {
		//Set Up Components
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
			dateBox.setValue(new Date(), true);
			dateBox.setWidth("70px");
			//Set Up usedUpDatePanel
			usedUpDatePanel.setSpacing(5);
			//Set Up usedUpIngredientFlexTable
			usedUpIngredientFlexTable.setWidth("600px");
			usedUpIngredientFlexTable.setText(0,0,"Lot Code");
			usedUpIngredientFlexTable.setText(0,1,"Ingredient Type");
			usedUpIngredientFlexTable.setText(0,2,"Checked-In Date");
			usedUpIngredientFlexTable.setText(0,3,"In-Use Date");
			usedUpIngredientFlexTable.setText(0,4,"Used-Up Date");
			usedUpIngredientFlexTable.setText(0,5,"Mark");
			usedUpIngredientFlexTable.getRowFormatter().addStyleName(0, "usedUpIngredientFlexTableHeader");
			usedUpIngredientFlexTable.addStyleName("usedUpIngredientFlexTable");
		//Add components to usedUpDatePanel
		usedUpDatePanel.add(dateBoxLabel);
		usedUpDatePanel.add(dateBox);
		//Assemble usedUpIngredientPanel
		usedUpIngredientPanel.setSpacing(5);
		usedUpIngredientPanel.add(usedUpDatePanel);
		usedUpIngredientPanel.add(usedUpIngredientFlexTable);
		//Create Dialog Box
		dialogBox = new LotCodeManagerDialogBox(this, "Mark Inventory As Used-Up", true, true);
	}
	
	public void populateFlexTable() {
		if(usedUpIngredientList.isEmpty()) {
			Window.alert("There are no Ingredients to mark");
		}
		else {
			final ArrayList<Integer> hashCodeList = new ArrayList<Integer>();
			for (ItemInInventory inUseIngredient : usedUpIngredientList) {
				hashCodeList.add(inUseIngredient.hashCode());
			    final int rowToMark = hashCodeList.indexOf(inUseIngredient.hashCode());
			    // create markIngredientButton with handler
			    Button markIngredientButton = new Button();
			    makeButtonWithIcon(markIngredientButton, icons.cancelIcon(), "Used Up"); 
			    markIngredientButton.addClickHandler(new ClickHandler() {
			        public void onClick(ClickEvent event) {
			        	usedUpIngredientList.get(rowToMark).setUsedUpDate(dateBox.getValue());
			        	usedUpIngredientFlexTable.clearCell((rowToMark + 1), 5);
						usedUpIngredientFlexTable.setText((rowToMark + 1),4,dateFormat.format(usedUpIngredientList.get(rowToMark).getUsedUpDate()));
			        }
			        });
			    // add new row to usedUpIngredientFlexTable
			    usedUpIngredientFlexTable.setText((rowToMark + 1),0,inUseIngredient.getLotCode());
				usedUpIngredientFlexTable.setText((rowToMark + 1),1,inUseIngredient.getItemType());
				usedUpIngredientFlexTable.setText((rowToMark + 1),2,dateFormat.format(inUseIngredient.getCheckedInDate()));
				usedUpIngredientFlexTable.setText((rowToMark + 1),3,dateFormat.format(inUseIngredient.getInUseDate()));
				usedUpIngredientFlexTable.setText((rowToMark + 1),4,"");
				usedUpIngredientFlexTable.setWidget((rowToMark + 1),5,markIngredientButton);
				usedUpIngredientFlexTable.getCellFormatter().addStyleName((rowToMark + 1),5,"usedUpIngredientFlexTableRemoveButton");
			}
		}
	}

	public Panel getPanel() {
		return usedUpIngredientPanel;
	}

	void updateDB() {
		dbSetUsedUpIngredients(usedUpIngredientList);
	}
}
