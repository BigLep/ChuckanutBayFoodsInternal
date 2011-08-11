 package com.chuckanutbay.webapp.lotmanagement.client;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newHorizontalPanel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newVerticalPanel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.setSpacing;
import static com.chuckanutbay.webapp.common.client.IconUtil.CANCEL;
import static com.chuckanutbay.webapp.common.client.IconUtil.CHECKMARK;
import static com.chuckanutbay.webapp.common.client.IconUtil.DATABASE;
import static com.chuckanutbay.webapp.common.client.IconUtil.DATE;
import static com.chuckanutbay.webapp.common.client.IconUtil.LIST;
import static com.chuckanutbay.webapp.common.client.IconUtil.SEARCH;
import static com.chuckanutbay.webapp.common.client.IconUtil.WARNING;
import static com.chuckanutbay.webapp.common.client.IconUtil.createButtonWithIcon;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.addClickHandler;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.addMouseOverAndOutHandlers;
import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.makePopupVisible;

import com.chuckanutbay.webapp.common.client.GwtWidgetHelper;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

final class LotCodeManager  implements ClickHandler, EntryPoint, MouseOverHandler, MouseOutHandler {
	
	//Main UI Components
	private Button checkedInButton;
	private Button inUseButton;
	private Button usedUpButton;
	private Button lotCodeSearch;
	private Button dateSearch;
	private Button activeIngredientsButton;
	private Button viewFullInventoryHistoryButton;
	private final DecoratedPopupPanel lotCodeSearchPopup = new DecoratedPopupPanel(true);
	private final DecoratedPopupPanel dateSearchPopup = new DecoratedPopupPanel(true);
	private final DecoratedPopupPanel activeIngredientsPopup = new DecoratedPopupPanel(true);
	private static final int POPUP_POSITION_FROM_TOP = 25;
	private static final int POPUP_POSITION_FROM_LEFT = 5;
	private LotCodeManagerDialogBox lotCodeManagerDialogBox;
	
	@Override
	public void onModuleLoad() {
		GWT.log("Starting UP");
		//Set Up iconButtons
		checkedInButton = createButtonWithIcon(CHECKMARK, "Checked-In");
		inUseButton = createButtonWithIcon(WARNING, "In Use");
		usedUpButton = createButtonWithIcon(CANCEL, "Used Up");
		lotCodeSearch = createButtonWithIcon(SEARCH, "Lot Code Search");
		dateSearch = createButtonWithIcon(DATE, "Date Search");
		activeIngredientsButton = createButtonWithIcon(LIST, "Active Ingredients");
		viewFullInventoryHistoryButton = createButtonWithIcon(DATABASE, "View Full Inventory History");
		
		//Add Handlers
		addClickHandler(this, checkedInButton, inUseButton, usedUpButton, lotCodeSearch, dateSearch, activeIngredientsButton, viewFullInventoryHistoryButton);
		addMouseOverAndOutHandlers(this, lotCodeSearch, dateSearch, activeIngredientsButton);
		
		//Set Text of Pop-Ups
		lotCodeSearchPopup.setWidget(new Label("Search for the information of a specific Lot Code"));
		dateSearchPopup.setWidget(new Label("Search for the Lot Codes in use on a specific date"));
		activeIngredientsPopup.setWidget(new Label("Displays all currently In-Use items"));
		
		//Set up mainPanel
			//Set up lotCodeManagerLabel Style
			Label lotCodeManagerLabel = new Label("Lot Code Manager");
			lotCodeManagerLabel.setStyleName("lotCodeManagerLabel");
			//Set up HorizontalButtonPanels
				HorizontalPanel markInventoryButtonsHPanel = newHorizontalPanel(checkedInButton, inUseButton, usedUpButton);
				HorizontalPanel queryButtonsHPanel = newHorizontalPanel(lotCodeSearch, dateSearch, activeIngredientsButton);
				HorizontalPanel viewFullInventoryHistoryHPanel = newHorizontalPanel(viewFullInventoryHistoryButton);
			
			//Set up VerticalButtonPanels
				VerticalPanel markInventoryVPanel = newVerticalPanel(new Label("Mark Inventory As:"), markInventoryButtonsHPanel);
				VerticalPanel queryVPanel = newVerticalPanel(new Label("Query Lot Code Data:"), queryButtonsHPanel);
				VerticalPanel viewFullInventoryHistoryVPanel = newVerticalPanel(new Label("Inventory History:"), viewFullInventoryHistoryHPanel);
				GwtWidgetHelper.setStyleName("buttonsPanel", markInventoryVPanel, queryVPanel, viewFullInventoryHistoryVPanel);
			
			//Set up ButtonsPanel
				HorizontalPanel buttonsPanel = newHorizontalPanel(markInventoryVPanel, queryVPanel, viewFullInventoryHistoryVPanel);
				
		VerticalPanel mainPanel = newVerticalPanel(lotCodeManagerLabel, buttonsPanel);
		mainPanel.setStyleName("roundedCorners");
		mainPanel.setCellHorizontalAlignment(buttonsPanel, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setCellHorizontalAlignment(lotCodeManagerLabel, HasHorizontalAlignment.ALIGN_CENTER);
		setSpacing(5, markInventoryButtonsHPanel, queryButtonsHPanel, viewFullInventoryHistoryHPanel, buttonsPanel, mainPanel);
		//Add mainPanel to rootPanel
		RootPanel.get("LotCodeManager").add(mainPanel);
		GWT.log("Finnished Starting UP");
	} 
	
	@Override
	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();
		if (sender == checkedInButton) {
			lotCodeManagerDialogBox = new CheckedInPanel();
		}
		else if (sender == inUseButton) {
			lotCodeManagerDialogBox = new InUsePanel();
		}
		else if (sender == usedUpButton) {
			lotCodeManagerDialogBox = new UsedUpPanel();
		}
		else if (sender == lotCodeSearch) {
			lotCodeManagerDialogBox = new LotCodeSearchPanel();
		}
		else if (sender == dateSearch) {
			lotCodeManagerDialogBox = new DateSearchPanel();
		}
		else if (sender == activeIngredientsButton) {
			lotCodeManagerDialogBox = new ActiveInventoryPanel();
		}
		else if (sender == viewFullInventoryHistoryButton) {
			lotCodeManagerDialogBox = new FullInventoryHistoryPanel();
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

