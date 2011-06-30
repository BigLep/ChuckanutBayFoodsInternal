package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.common.client.IconUtil.CANCEL;
import static com.chuckanutbay.webapp.common.client.IconUtil.CHECKMARK;
import static com.chuckanutbay.webapp.common.client.IconUtil.DATABASE;
import static com.chuckanutbay.webapp.common.client.IconUtil.DATE;
import static com.chuckanutbay.webapp.common.client.IconUtil.LIST;
import static com.chuckanutbay.webapp.common.client.IconUtil.SEARCH;
import static com.chuckanutbay.webapp.common.client.IconUtil.WARNING;
import static com.chuckanutbay.webapp.common.client.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makePopupVisible;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

final class LotCodeManager  implements ClickHandler, EntryPoint, MouseOverHandler, MouseOutHandler {
	
	//Main UI Components
	private final VerticalPanel mainPanel = new VerticalPanel();
	private final FocusPanel focusPanel = new FocusPanel();
	private final Label lotCodeManagerLabel = new Label("Lot Code Manager");
	private final Label markInventoryLabel = new Label("Mark Inventory As:");
	private final Label queryLabel = new Label("Query Lot Code Data:");
	private final Label viewFullInventoryHistoryLabel = new Label("Inventory History:");
	private final HorizontalPanel buttonsPanel = new HorizontalPanel();
	private final HorizontalPanel markInventoryButtonsHPanel = new HorizontalPanel();
	private final HorizontalPanel viewFullInventoryHistoryHPanel = new HorizontalPanel();
	private final HorizontalPanel queryButtonsHPanel = new HorizontalPanel();
	private final VerticalPanel markInventoryVPanel = new VerticalPanel();
	private final VerticalPanel viewFullInventoryHistoryVPanel = new VerticalPanel();
	private final VerticalPanel queryVPanel = new VerticalPanel();
	private final Button checkedInButton = new Button();
	private final Button inUseButton = new Button();
	private final Button usedUpButton = new Button();
	private final Button lotCodeSearch = new Button();
	private final Button dateSearch = new Button ();
	private final Button activeIngredientsButton = new Button ();
	private final Button viewFullInventoryHistoryButton = new Button();
	private final DecoratedPopupPanel lotCodeSearchPopup = new DecoratedPopupPanel(true);
	private final DecoratedPopupPanel dateSearchPopup = new DecoratedPopupPanel(true);
	private final DecoratedPopupPanel activeIngredientsPopup = new DecoratedPopupPanel(true);
	private static int POPUP_POSITION_FROM_TOP = 25;
	private static int POPUP_POSITION_FROM_LEFT = 5;
	private LotCodeManagerPanel lotCodeManagerPanel;
	
	@Override
	public void onModuleLoad() {
		GWT.log("Starting UP");
		//Set Up iconButtons
		createButtonWithIcon(checkedInButton, CHECKMARK, "Checked-In");
		createButtonWithIcon(inUseButton, WARNING, "In Use");
		createButtonWithIcon(usedUpButton, CANCEL, "Used Up");
		createButtonWithIcon(lotCodeSearch, SEARCH, "Lot Code Search");
		createButtonWithIcon(dateSearch, DATE, "Date Search");
		createButtonWithIcon(activeIngredientsButton, LIST, "Active Ingredients");
		createButtonWithIcon(viewFullInventoryHistoryButton, DATABASE, "View Full Inventory History");
		
		//Add Handlers
		LotCodeUtil.addClickHandler(this, checkedInButton, inUseButton, usedUpButton, lotCodeSearch, dateSearch, activeIngredientsButton, viewFullInventoryHistoryButton);
		LotCodeUtil.addMouseOverHandler(this, lotCodeSearch, dateSearch, activeIngredientsButton);
		lotCodeSearch.addMouseOutHandler(this);
		dateSearch.addMouseOutHandler(this);
		activeIngredientsButton.addMouseOutHandler(this);
		
		//Set Text of Pop-Ups
		lotCodeSearchPopup.setWidget(new Label("Search for the information of a specific Lot Code"));
		dateSearchPopup.setWidget(new Label("Search for the Lot Codes in use on a specific date"));
		activeIngredientsPopup.setWidget(new Label("Displays all currently In-Use items"));
		
		//Set up mainPanel
			//Set up lotCodeManagerLabel Style
			lotCodeManagerLabel.setStyleName("lotCodeManagerLabel");
			//Set up HorizontalButtonPanels
				//Add Buttons to markInventoryButtonsHPanel
				markInventoryButtonsHPanel.add(checkedInButton);
				markInventoryButtonsHPanel.add(inUseButton);
				markInventoryButtonsHPanel.add(usedUpButton);
				markInventoryButtonsHPanel.setSpacing(5);
				//Add Buttons to queryButtonsHPanel
				queryButtonsHPanel.add(lotCodeSearch);
				queryButtonsHPanel.add(dateSearch);
				queryButtonsHPanel.add(activeIngredientsButton);
				queryButtonsHPanel.setSpacing(5);
				//Add Buttons to viewFullInventoryHistoryHPanel
				viewFullInventoryHistoryHPanel.add(viewFullInventoryHistoryButton);
				viewFullInventoryHistoryHPanel.setSpacing(5);
			
			//Set up VerticalButtonPanels
				markInventoryVPanel.add(markInventoryLabel);
				markInventoryVPanel.add(markInventoryButtonsHPanel);
				markInventoryVPanel.setStyleName("buttonsPanel");
				queryVPanel.add(queryLabel);
				queryVPanel.add(queryButtonsHPanel);
				queryVPanel.setStyleName("buttonsPanel");
				viewFullInventoryHistoryVPanel.add(viewFullInventoryHistoryLabel);
				viewFullInventoryHistoryVPanel.add(viewFullInventoryHistoryHPanel);
				viewFullInventoryHistoryVPanel.setStyleName("buttonsPanel");
			
			//Set up ButtonsPanel
				buttonsPanel.add(markInventoryVPanel);
				buttonsPanel.add(queryVPanel);
				buttonsPanel.add(viewFullInventoryHistoryVPanel);
				buttonsPanel.setSpacing(5);
				
		mainPanel.add(lotCodeManagerLabel);
		mainPanel.add(buttonsPanel);
		mainPanel.setSpacing(5);
		mainPanel.setStyleName("roundedCorners");
		mainPanel.setCellHorizontalAlignment(buttonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHorizontalAlignment(lotCodeManagerLabel, HasHorizontalAlignment.ALIGN_CENTER);
		focusPanel.add(mainPanel);
		//Add mainPanel to rootPanel
		RootPanel rootPanel = RootPanel.get("LotCodeManager");
		rootPanel.add(focusPanel);
		GWT.log("Finnished Starting UP");
	} 
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == checkedInButton) {
			lotCodeManagerPanel = new CheckedInPanel();
		}
		else if (sender == inUseButton) {
			lotCodeManagerPanel = new InUsePanel();
		}
		else if (sender == usedUpButton) {
			lotCodeManagerPanel = new UsedUpPanel();
		}
		else if (sender == lotCodeSearch) {
			lotCodeManagerPanel = new LotCodeSearchPanel();
		}
		else if (sender == dateSearch) {
			lotCodeManagerPanel = new DateSearchPanel();
		}
		else if (sender == activeIngredientsButton) {
			lotCodeManagerPanel = new ActiveIngredientsPanel();
		}
		else if (sender == viewFullInventoryHistoryButton) {
			lotCodeManagerPanel = new FullInventoryHistoryPanel();
		}
	}
	
	@Override
	public void onMouseOver(MouseOverEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == lotCodeSearch) {
			makePopupVisible(lotCodeSearchPopup, sender, POPUP_POSITION_FROM_TOP, POPUP_POSITION_FROM_LEFT);
		}
		else if (sender == dateSearch) {
			makePopupVisible(dateSearchPopup, sender, POPUP_POSITION_FROM_TOP, POPUP_POSITION_FROM_LEFT);
		}
		else if (sender == activeIngredientsButton) {
			makePopupVisible(activeIngredientsPopup, sender, POPUP_POSITION_FROM_TOP, POPUP_POSITION_FROM_LEFT);
		}
	}

	@Override
	public void onMouseOut(MouseOutEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == lotCodeSearch) {
			lotCodeSearchPopup.hide();
		}
		else if (sender == dateSearch) {
			dateSearchPopup.hide();
		}
		else if (sender == activeIngredientsButton) {
			activeIngredientsPopup.hide();
		}
	}
}

