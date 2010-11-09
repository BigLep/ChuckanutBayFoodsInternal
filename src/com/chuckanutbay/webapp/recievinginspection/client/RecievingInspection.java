package com.chuckanutbay.webapp.recievinginspection.client;

import static com.chuckanutbay.webapp.recievinginspection.client.RecievingInspectionUtil.*;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.recievinginspection.shared.InventoryItemDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.TabPanel;

public class RecievingInspection implements EntryPoint {
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private RadioButton chckbxAcceptable;
	private RadioButton chckbxInfestation;
	private RadioButton chckbxFilthy;
	private RadioButton chckbxMoldy;
	private RadioButton chckbxWrongTemp;
	private RadioButton chckbxTrailorLockedN;
	private RadioButton chckbxTrailorLockedY;
	private RadioButton chckBxAllergensY;
	private RadioButton chckbxAllergensN;
	private RadioButton chckBxGlassN;
	private RadioButton chckBxGlassY;
	private RadioButton chckbxGood;
	private RadioButton chckbxBad;
	private RadioButton chckBxLotCodesY;
	private ListBox listBox1;
	private IntegerBox integerBox1;
	private RadioButton chckBxY1;
	private TextBox textBox_1;
	private DateBox dateBox;
	private TextBox textBox;
	private ListBox listBox2;
	private ListBox listBox6;
	private ListBox listBox3;
	private ListBox listBox4;
	private ListBox listBox5;
	private IntegerBox integerBox2;
	private IntegerBox integerBox3;
	private IntegerBox integerBox4;
	private IntegerBox integerBox5;
	private IntegerBox integerBox6;
	private RadioButton chckBxY2;
	private RadioButton chckBxN1;
	private RadioButton chckBxLotCodesN;
	private RadioButton radioButton;
	private RadioButton radioButton_1;
	private RadioButton radioButton_2;
	private RadioButton radioButton_3;
	private RadioButton radioButton_4;
	private RadioButton radioButton_5;
	private RadioButton radioButton_6;
	private RadioButton radioButton_7;
	private RadioButton radioButton_8;
	private RpcHelper rpcHelper = new RpcHelper();
	private List<InventoryItemDto> inventoryItemList = newArrayList();
	private TextArea txtrAdditionalNotes;
	private Label lblAdditionalNotes;
	private VerticalPanel verticalPanel;
	private TabPanel tabPanel;
	private Label lblQuickbooksPo;
	private TextBox textBox_2;
	private Label lblQuickbooksPo_1;
	private ScrollPanel scrollPanel;
	
	@Override
	public void onModuleLoad() {
		generateUI();
		rpcHelper.dbGetQBItems(inventoryItemList, this);
		

	}

	private void generateUI() {
		mainPanel.setSpacing(5);
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setWidth("750px");
		rootPanel.add(mainPanel, 0, 0);
		mainPanel.setSize("822px", "651px");
		
		tabPanel = new TabPanel();
		mainPanel.add(tabPanel);
		tabPanel.setSize("750px", "406px");
		
		verticalPanel = new VerticalPanel();
		tabPanel.add(verticalPanel, "New Recieving Inspection", false);
		verticalPanel.setSize("740px", "607px");
		verticalPanel.setSpacing(5);
		verticalPanel.setStyleName("mainPanel");
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		InlineLabel title = new InlineLabel("Recieving Inspection Form");
		verticalPanel.add(title);
		title.setStyleName("dashboardTitle");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSpacing(5);
		horizontalPanel.setHeight("24px");
		
		Label lblDate = new Label("Date:");
		horizontalPanel.add(lblDate);
		
		dateBox = new DateBox();
		dateBox.setValue(new Date());
		dateBox.setFormat(new DefaultFormat(DateTimeFormat.getFormat("MMM d, yyyy")));
		horizontalPanel.add(dateBox);
		
		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel.add(simplePanel);
		
		Label lblInspectedByInitals = new Label("Inspected By Initals:");
		horizontalPanel.add(lblInspectedByInitals);
		
		textBox = new TextBox();
		horizontalPanel.add(textBox);
		
		HorizontalPanel horizontalPanel_5 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_5);
		horizontalPanel_5.setSpacing(15);
		
		FlexTable flexTable = new FlexTable();
		horizontalPanel_5.add(flexTable);
		
		lblQuickbooksPo = new Label("Quickbooks PO:");
		flexTable.setWidget(0, 0, lblQuickbooksPo);
		
		textBox_2 = new TextBox();
		flexTable.setWidget(0, 1, textBox_2);
		
		Label lblTruckingCompany = new Label("Trucking Company:");
		flexTable.setWidget(1, 0, lblTruckingCompany);
		
		textBox_1 = new TextBox();
		flexTable.setWidget(1, 1, textBox_1);
		
		Label lblCondition = new Label("Condition:");
		flexTable.setWidget(2, 0, lblCondition);
		
		chckbxAcceptable = new RadioButton("Condition");
		chckbxAcceptable.setText("Acceptable");
		chckbxAcceptable.setStyleName("greenText");
		flexTable.setWidget(2, 1, chckbxAcceptable);
		
		chckbxInfestation = new RadioButton("Condition");
		chckbxInfestation.setText("Infestation");
		flexTable.setWidget(3, 1, chckbxInfestation);
		
		chckbxFilthy = new RadioButton("Condition");
		chckbxFilthy.setText("Filthy");
		flexTable.setWidget(4, 1, chckbxFilthy);
		
		chckbxMoldy = new RadioButton("Condition");
		chckbxMoldy.setText("Moldy");
		flexTable.setWidget(5, 1, chckbxMoldy);
		
		chckbxWrongTemp = new RadioButton("Condition");
		chckbxWrongTemp.setText("Wrong Temp");
		flexTable.setWidget(6, 1, chckbxWrongTemp);
		
		Label lblTrailorLocked = new Label("Trailor Locked:");
		flexTable.setWidget(7, 0, lblTrailorLocked);
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel_1.setSpacing(3);
		flexTable.setWidget(7, 1, horizontalPanel_1);
		
		chckbxTrailorLockedY = new RadioButton("TrailorLocked");
		chckbxTrailorLockedY.setText("Yes");
		chckbxTrailorLockedY.setStyleName("greenText");
		horizontalPanel_1.add(chckbxTrailorLockedY);
		
		chckbxTrailorLockedN = new RadioButton("No");
		chckbxTrailorLockedN.setText("No");
		chckbxTrailorLockedN.setName("TrailorLocked");
		horizontalPanel_1.add(chckbxTrailorLockedN);
		chckbxTrailorLockedN.setWidth("40px");
		
		Label lblAllergens = new Label("Allergens:");
		flexTable.setWidget(8, 0, lblAllergens);
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setSpacing(3);
		flexTable.setWidget(8, 1, horizontalPanel_2);
		
		chckBxAllergensY = new RadioButton("Allergens");
		chckBxAllergensY.setText("Yes");
		chckBxAllergensY.setStyleName("greenText");
		horizontalPanel_2.add(chckBxAllergensY);
		
		chckbxAllergensN = new RadioButton("Allergens");
		chckbxAllergensN.setText("No");
		horizontalPanel_2.add(chckbxAllergensN);
		chckbxAllergensN.setWidth("40px");
		
		Label lblplaceP = new Label("(Place a Pink Allergen sticker on Allergens)");
		lblplaceP.setStyleName("smallText");
		flexTable.setWidget(9, 0, lblplaceP);
		flexTable.getFlexCellFormatter().setColSpan(9, 0, 2);
		
		Label lblProductInGlass = new Label("Product in Glass:");
		flexTable.setWidget(10, 0, lblProductInGlass);
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_3.setSpacing(3);
		flexTable.setWidget(10, 1, horizontalPanel_3);
		
		chckBxGlassY = new RadioButton("Glass");
		chckBxGlassY.setText("Yes");
		horizontalPanel_3.add(chckBxGlassY);
		
		chckBxGlassN = new RadioButton("Glass");
		chckBxGlassN.setText("No");
		horizontalPanel_3.add(chckBxGlassN);
		chckBxGlassN.setWidth("40px");
		
		Label lblfollowGlassPolicy = new Label("(Follow glass policy)");
		lblfollowGlassPolicy.setStyleName("smallText");
		flexTable.setWidget(11, 0, lblfollowGlassPolicy);
		
		Label lblConditionOfProduct = new Label("Product Condition:");
		flexTable.setWidget(12, 0, lblConditionOfProduct);
		
		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4.setSpacing(3);
		flexTable.setWidget(12, 1, horizontalPanel_4);
		
		chckbxGood = new RadioButton("ProductCondition");
		chckbxGood.setText("Good");
		chckbxGood.setStyleName("greenText");
		horizontalPanel_4.add(chckbxGood);
		
		chckbxBad = new RadioButton("ProductCondition");
		chckbxBad.setText("Bad");
		horizontalPanel_4.add(chckbxBad);
		chckbxBad.setWidth("40px");
		
		Label lblLotCodesEntered = new Label("Lot Codes Entered:");
		flexTable.setWidget(13, 0, lblLotCodesEntered);
		
		HorizontalPanel horizontalPanel_6 = new HorizontalPanel();
		horizontalPanel_6.setSpacing(3);
		flexTable.setWidget(13, 1, horizontalPanel_6);
		
		chckBxLotCodesY = new RadioButton("LotCode");
		chckBxLotCodesY.setText("Yes");
		chckBxLotCodesY.setStyleName("greenText");
		horizontalPanel_6.add(chckBxLotCodesY);
		
		chckBxLotCodesN = new RadioButton("LotCode");
		chckBxLotCodesN.setText("No");
		horizontalPanel_6.add(chckBxLotCodesN);
		chckBxLotCodesN.setWidth("40px");
		
		FlexTable flexTable_1 = new FlexTable();
		horizontalPanel_5.add(flexTable_1);
		horizontalPanel_5.setCellHorizontalAlignment(flexTable_1, HasHorizontalAlignment.ALIGN_CENTER);
		
		Label lblTemperatures = new Label("Temperatures");
		flexTable_1.setWidget(0, 0, lblTemperatures);
		
		Label lblDairy = new Label("Dairy: between 35 and 45 degrees F");
		lblDairy.setStyleName("smallText");
		flexTable_1.setWidget(1, 0, lblDairy);
		
		Label lblFrozen = new Label("Frozen: 22F or less");
		lblFrozen.setStyleName("smallText");
		flexTable_1.setWidget(2, 0, lblFrozen);
		
		Label lblProduct = new Label("Product");
		flexTable_1.setWidget(3, 0, lblProduct);
		
		Label lblTemperature = new Label("Temp");
		flexTable_1.setWidget(3, 1, lblTemperature);
		
		Label lblAcceptable = new Label("Acceptable");
		flexTable_1.setWidget(3, 2, lblAcceptable);
		flexTable_1.getFlexCellFormatter().setColSpan(1, 0, 3);
		flexTable_1.getFlexCellFormatter().setColSpan(2, 0, 2);
		flexTable_1.getFlexCellFormatter().setColSpan(0, 0, 3);
		
		listBox1 = new ListBox();
		flexTable_1.setWidget(4, 0, listBox1);
		listBox1.setWidth("100px");
		
		integerBox1 = new IntegerBox();
		integerBox1.setAlignment(TextAlignment.RIGHT);
		flexTable_1.setWidget(4, 1, integerBox1);
		integerBox1.setWidth("30px");
		
		HorizontalPanel horizontalPanel_7 = new HorizontalPanel();
		horizontalPanel_7.setSpacing(3);
		flexTable_1.setWidget(4, 2, horizontalPanel_7);
		
		chckBxY1 = new RadioButton("Temperatures1");
		chckBxY1.setText("Yes");
		chckBxY1.setStyleName("greenText");
		horizontalPanel_7.add(chckBxY1);
		
		chckBxN1 = new RadioButton("Temperatures1");
		chckBxN1.setText("No");
		horizontalPanel_7.add(chckBxN1);
		chckBxN1.setWidth("40px");
		
		listBox2 = new ListBox();
		flexTable_1.setWidget(5, 0, listBox2);
		listBox2.setWidth("100px");
		
		integerBox2 = new IntegerBox();
		integerBox2.setAlignment(TextAlignment.RIGHT);
		flexTable_1.setWidget(5, 1, integerBox2);
		integerBox2.setWidth("30px");
		
		HorizontalPanel horizontalPanel_8 = new HorizontalPanel();
		horizontalPanel_8.setSpacing(3);
		flexTable_1.setWidget(5, 2, horizontalPanel_8);
		
		chckBxY2 = new RadioButton("Temperatures2");
		chckBxY2.setText("Yes");
		chckBxY2.setStyleName("greenText");
		horizontalPanel_8.add(chckBxY2);
		
		radioButton_4 = new RadioButton("Temperatures2");
		radioButton_4.setText("No");
		horizontalPanel_8.add(radioButton_4);
		radioButton_4.setWidth("40px");
		
		listBox3 = new ListBox();
		flexTable_1.setWidget(6, 0, listBox3);
		listBox3.setWidth("100px");
		
		integerBox3 = new IntegerBox();
		integerBox3.setAlignment(TextAlignment.RIGHT);
		flexTable_1.setWidget(6, 1, integerBox3);
		integerBox3.setWidth("30px");
		
		HorizontalPanel horizontalPanel_9 = new HorizontalPanel();
		horizontalPanel_9.setSpacing(3);
		flexTable_1.setWidget(6, 2, horizontalPanel_9);
		
		radioButton = new RadioButton("Temperatures3");
		radioButton.setText("Yes");
		radioButton.setStyleName("greenText");
		horizontalPanel_9.add(radioButton);
		
		radioButton_5 = new RadioButton("Temperatures3");
		radioButton_5.setText("No");
		horizontalPanel_9.add(radioButton_5);
		radioButton_5.setWidth("40px");
		
		listBox4 = new ListBox();
		flexTable_1.setWidget(7, 0, listBox4);
		listBox4.setWidth("100px");
		
		integerBox4 = new IntegerBox();
		integerBox4.setAlignment(TextAlignment.RIGHT);
		flexTable_1.setWidget(7, 1, integerBox4);
		integerBox4.setWidth("30px");
		
		HorizontalPanel horixontalPanel_10 = new HorizontalPanel();
		horixontalPanel_10.setSpacing(3);
		flexTable_1.setWidget(7, 2, horixontalPanel_10);
		
		radioButton_1 = new RadioButton("Temperatures4");
		radioButton_1.setText("Yes");
		radioButton_1.setStyleName("greenText");
		horixontalPanel_10.add(radioButton_1);
		
		radioButton_6 = new RadioButton("Temperatures4");
		radioButton_6.setText("No");
		horixontalPanel_10.add(radioButton_6);
		radioButton_6.setWidth("40px");
		
		listBox5 = new ListBox();
		flexTable_1.setWidget(8, 0, listBox5);
		listBox5.setWidth("100px");
		
		integerBox5 = new IntegerBox();
		integerBox5.setAlignment(TextAlignment.RIGHT);
		flexTable_1.setWidget(8, 1, integerBox5);
		integerBox5.setWidth("30px");
		
		HorizontalPanel horizontalPanel_11 = new HorizontalPanel();
		horizontalPanel_11.setSpacing(3);
		flexTable_1.setWidget(8, 2, horizontalPanel_11);
		
		radioButton_2 = new RadioButton("Temperatures5");
		radioButton_2.setText("Yes");
		radioButton_2.setStyleName("greenText");
		horizontalPanel_11.add(radioButton_2);
		
		radioButton_7 = new RadioButton("Temperatures5");
		radioButton_7.setText("No");
		horizontalPanel_11.add(radioButton_7);
		radioButton_7.setWidth("40px");
		
		listBox6 = new ListBox();
		flexTable_1.setWidget(9, 0, listBox6);
		listBox6.setWidth("100px");
		flexTable_1.getCellFormatter().setHorizontalAlignment(9, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
		integerBox6 = new IntegerBox();
		integerBox6.setAlignment(TextAlignment.RIGHT);
		flexTable_1.setWidget(9, 1, integerBox6);
		integerBox6.setWidth("30px");
		
		HorizontalPanel horizontalPanel_12 = new HorizontalPanel();
		horizontalPanel_12.setSpacing(3);
		flexTable_1.setWidget(9, 2, horizontalPanel_12);
		
		radioButton_3 = new RadioButton("Temperatures6");
		radioButton_3.setText("Yes");
		radioButton_3.setStyleName("greenText");
		horizontalPanel_12.add(radioButton_3);
		
		radioButton_8 = new RadioButton("Temperatures6");
		radioButton_8.setText("No");
		horizontalPanel_12.add(radioButton_8);
		radioButton_8.setWidth("40px");
		flexTable_1.getCellFormatter().setHorizontalAlignment(3, 2, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		lblAdditionalNotes = new Label("Additional Notes");
		flexTable_1.setWidget(10, 0, lblAdditionalNotes);
		
		txtrAdditionalNotes = new TextArea();
		flexTable_1.setWidget(11, 0, txtrAdditionalNotes);
		txtrAdditionalNotes.setSize("220px", "60px");
		flexTable_1.getFlexCellFormatter().setColSpan(11, 0, 3);
		flexTable_1.getCellFormatter().setHorizontalAlignment(11, 0, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable_1.getFlexCellFormatter().setColSpan(10, 0, 2);
		
		HorizontalPanel horizontalPanel_10 = new HorizontalPanel();
		verticalPanel.add(horizontalPanel_10);
		horizontalPanel_10.setSpacing(10);
		
		Button button = new Button("New button");
		makeButtonWithIcon(button, icons.cancelIcon(), "Cancel");
		horizontalPanel_10.add(button);
		
		Button button_1 = new Button("New button");
		makeButtonWithIcon(button_1, icons.saveIcon(), "Save");
		horizontalPanel_10.add(button_1);
		
		scrollPanel = new ScrollPanel();
		scrollPanel.setAlwaysShowScrollBars(true);
		tabPanel.add(scrollPanel, "New tab", false);
		scrollPanel.setSize("734px", "606px");
		
		FlexTable flexTable_2 = new FlexTable();
		scrollPanel.setWidget(flexTable_2);
		flexTable_2.setSize("100%", "100%");
		flexTable_2.setStyleName("mainPanel");
		
		Label lblDate_1 = new Label("Date:");
		flexTable_2.setWidget(0, 0, lblDate_1);
		
		Label lblInspector = new Label("Inspector:");
		flexTable_2.setWidget(0, 1, lblInspector);
		
		lblQuickbooksPo_1 = new Label("Quickbooks PO:");
		flexTable_2.setWidget(0, 2, lblQuickbooksPo_1);
		
		Label lblNote = new Label("Note:");
		flexTable_2.setWidget(0, 3, lblNote);
	}

	public void populateListBoxes() {
		listBox1.addItem("---");
		listBox2.addItem("---");
		listBox3.addItem("---");
		listBox4.addItem("---");
		listBox5.addItem("---");
		listBox6.addItem("---");
		for(InventoryItemDto inventoryItem : inventoryItemList) {
			listBox1.addItem(inventoryItem.getDescription());
			listBox2.addItem(inventoryItem.getDescription());
			listBox3.addItem(inventoryItem.getDescription());
			listBox4.addItem(inventoryItem.getDescription());
			listBox5.addItem(inventoryItem.getDescription());
			listBox6.addItem(inventoryItem.getDescription());
		}
		
	}
}
