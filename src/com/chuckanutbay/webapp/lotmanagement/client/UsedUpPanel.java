package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.DATE_BOX_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.FLEX_TABLE_WIDTH;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.dateFormat;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.icons;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makeButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.newArrayList;

import java.util.ArrayList;
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
import com.google.gwt.user.datepicker.client.DateBox;

public class UsedUpPanel extends LotCodeManagerPanel {
	//Checked-In Components
	private VerticalPanel usedUpIngredientPanel = new VerticalPanel();
	private HorizontalPanel usedUpDatePanel = new HorizontalPanel();
	private Label dateBoxLabel = new Label("In Use Date:");
	private DateBox dateBox = new DateBox();
	private DialogBox dialogBox;
	private FlexTable usedUpIngredientFlexTable = new FlexTable();
	private List<InventoryLotDto> usedUpIngredientList = newArrayList();
	private RpcHelper rpcHelper = new RpcHelper();
	
	public UsedUpPanel() {
		setUpPanel();
		rpcHelper.dbGetInUseIngredients(usedUpIngredientList, this);
	}
	
	public void setUpPanel() {
		log("starting set up");
		//Set Up Components
			//Set Up dateBox
			dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
			dateBox.setValue(new Date(), true);
			dateBox.setWidth(DATE_BOX_WIDTH);
			//Set Up usedUpDatePanel
			usedUpDatePanel.setSpacing(5);
			//Set Up usedUpIngredientFlexTable
			usedUpIngredientFlexTable.setWidth(FLEX_TABLE_WIDTH);
			usedUpIngredientFlexTable.setText(0,0,"Lot Code");
			usedUpIngredientFlexTable.setText(0,1,"Ingredient Type");
			usedUpIngredientFlexTable.setText(0,2,"Checked-In Date");
			usedUpIngredientFlexTable.setText(0,3,"In-Use Date");
			usedUpIngredientFlexTable.setText(0,4,"Used-Up Date");
			usedUpIngredientFlexTable.setText(0,5,"Mark");
			usedUpIngredientFlexTable.getRowFormatter().addStyleName(0, "FlexTableHeader");
			usedUpIngredientFlexTable.addStyleName("FlexTable");
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
		log("populating");
		if(usedUpIngredientList.isEmpty()) {
			Window.alert("There are no Ingredients to mark");
		}
		else {
			final ArrayList<Integer> hashCodeList = new ArrayList<Integer>();
			for (InventoryLotDto inUseIngredient : usedUpIngredientList) {
				hashCodeList.add(inUseIngredient.hashCode());
			    final int rowToMark = hashCodeList.indexOf(inUseIngredient.hashCode());
			    // create markIngredientButton with handler
			    Button markIngredientButton = new Button();
			    makeButtonWithIcon(markIngredientButton, icons.cancelIcon(), "Used Up"); 
			    markIngredientButton.addClickHandler(new ClickHandler() {
			        public void onClick(ClickEvent event) {
			        	usedUpIngredientList.get(rowToMark).setEndUseDatetime(dateBox.getValue());
			        	usedUpIngredientFlexTable.clearCell((rowToMark + 1), 5);
						usedUpIngredientFlexTable.setText((rowToMark + 1),4,dateFormat.format(usedUpIngredientList.get(rowToMark).getEndUseDatetime()));
			        }
			        });
			    // add new row to usedUpIngredientFlexTable
			    usedUpIngredientFlexTable.setText((rowToMark + 1),0,inUseIngredient.getCode());
				usedUpIngredientFlexTable.setText((rowToMark + 1),1,inUseIngredient.getInventoryItem().getDescription());
				usedUpIngredientFlexTable.setText((rowToMark + 1),2,dateFormat.format(inUseIngredient.getReceivedDatetime()));
				usedUpIngredientFlexTable.setText((rowToMark + 1),3,dateFormat.format(inUseIngredient.getStartUseDatetime()));
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
		rpcHelper.dbSetUsedUpIngredients(usedUpIngredientList);
	}
}
