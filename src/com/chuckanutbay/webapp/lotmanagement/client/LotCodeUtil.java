package com.chuckanutbay.webapp.lotmanagement.client;

import java.util.Date;
import java.util.logging.Logger;

import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionModel;

// FIXME: add javadocs
public class LotCodeUtil {
	public static final String ACTIVE_INVENTORY_ITEMS_TITLE = "View Active Inventory Items";
	public static final String FULL_INVENTORY_HISTORY_TITLE = "View Full Inventory History";
	public static final String DATE_SEARCH_TITLE = "Search for Lot Codes In-Use by Date";
	public static final String LOT_CODE_SEARCH_TITLE = "Search for Lot-Codes";
	public static final String USED_UP_TITLE = "Mark Inventory As Used-Up";
	public static final String IN_USE_TITLE = "Mark Inventory As In-Use";
	public static final String CHECK_IN_TITLE = "Check-In Inventory Items";
	
	public static final InventoryLotHeader[] IN_USE_HEADERS = {
		InventoryLotHeader.CheckBox,
		InventoryLotHeader.LotCode, 
		InventoryLotHeader.Description, 
		InventoryLotHeader.Qty, 
		InventoryLotHeader.CheckedInDate, 
		InventoryLotHeader.InUseDate
	};
	
	public static final InventoryLotHeader[] USED_UP_HEADERS = {
		InventoryLotHeader.CheckBox,
		InventoryLotHeader.LotCode, 
		InventoryLotHeader.Description, 
		InventoryLotHeader.Qty, 
		InventoryLotHeader.CheckedInDate, 
		InventoryLotHeader.InUseDate,
		InventoryLotHeader.UsedUpDate
	};
	
	public static final InventoryLotHeader[] ACTIVE_INVENTORY_HEADERS = {
		InventoryLotHeader.LotCode, 
		InventoryLotHeader.Description, 
		InventoryLotHeader.Qty, 
		InventoryLotHeader.CheckedInDate, 
		InventoryLotHeader.InUseDate
	};
	
	public static final InventoryLotHeader[] CHECKED_IN_PANEL_HEADERS = {
		InventoryLotHeader.LotCode, 
		InventoryLotHeader.Description, 
		InventoryLotHeader.Qty, 
		InventoryLotHeader.CheckedInDate,
		InventoryLotHeader.RemoveCheckBox
	};
	
	public static final InventoryLotHeader[] DEFAULT_HEADERS = {
		InventoryLotHeader.LotCode, 
		InventoryLotHeader.Description, 
		InventoryLotHeader.Qty, 
		InventoryLotHeader.CheckedInDate, 
		InventoryLotHeader.InUseDate,
		InventoryLotHeader.UsedUpDate,
	};
	
	public static enum InventoryLotHeader {
		LotCode, Description, Qty, CheckedInDate, InUseDate, UsedUpDate, CheckBox, RemoveCheckBox
	}
	
	/**
	 * @deprecated Use {@link Logger} instead
	 * @see "http://code.google.com/webtoolkit/doc/latest/DevGuideLogging.html"
	 */
    @Deprecated
	static public void log(String message) {
    	GWT.log(message);
    }

	static public void makePopupVisible(DecoratedPopupPanel popup, Widget sender, int top, int left) {
        int adjustedLeft = sender.getAbsoluteLeft() + left;
        int adjustedTop = sender.getAbsoluteTop() + top;
        popup.setPopupPosition(adjustedLeft, adjustedTop);
        popup.setWidth("150px");
        popup.show();
	}

    public static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("MMM d, yyyy");

    
    public static void addMouseOverHandler(MouseOverHandler handler, FocusWidget... widgets) {
		for (FocusWidget widget : widgets) {
			widget.addMouseOverHandler(handler);
		}
	}
    
    public static void addClickHandler(ClickHandler handler, FocusWidget... widgets) {
		for (FocusWidget widget : widgets) {
			widget.addClickHandler(handler);
		}
	}
    
    public static void addMouseOutHandler(MouseOutHandler handler, FocusWidget... widgets) {
		for (FocusWidget widget : widgets) {
			widget.addMouseOutHandler(handler);
		}
	}
    
    public static <H extends MouseOverHandler & MouseOutHandler> void addMouseOverAndOutHandlers(H handler, FocusWidget... widgets) {
    	addMouseOverHandler(handler, widgets);
    	addMouseOutHandler(handler, widgets);
    }
    
    public static Column<InventoryLotDto, ?> getColumn(InventoryLotHeader header, final SelectionModel<InventoryLotDto> selectionModel) {
    	switch (header) {
    	case LotCode: 
    	    return new TextColumn<InventoryLotDto>() {
    	        @Override
    	        public String getValue(InventoryLotDto object) {
    	        	return object.getCode();
    	        }
    	    };
    	case Description: 
    		return new TextColumn<InventoryLotDto>() {
    	        @Override
    	        public String getValue(InventoryLotDto object) {
    	        	return object.getInventoryItem().getDescription();
    	        }
    	    };
    	case Qty: 
    		return new TextColumn<InventoryLotDto>() {
    	        @Override
    	        public String getValue(InventoryLotDto object) {
    	        	return new Integer(object.getQuantity()).toString();
    	        }
    	    };
    	case CheckedInDate:
    	    return new Column<InventoryLotDto, Date>(new DateCell(DATE_FORMAT)) {
    	    	@Override
    	    	public Date getValue(InventoryLotDto object) {
    	    		return object.getReceivedDatetime();
    	    	}
    	    };
    	case InUseDate:
    		return new Column<InventoryLotDto, Date>(new DateCell(DATE_FORMAT)) {
    	    	@Override
    	    	public Date getValue(InventoryLotDto object) {
    	    		return object.getStartUseDatetime();
    	    	}
    	    };
    	case UsedUpDate:
    		return new Column<InventoryLotDto, Date>(new DateCell(DATE_FORMAT)) {
    	    	@Override
    	    	public Date getValue(InventoryLotDto object) {
    	    		return object.getEndUseDatetime();
    	    	}
    	    };
    	case RemoveCheckBox:
    	case CheckBox:
    		return new Column<InventoryLotDto, Boolean>(new CheckboxCell(true, false)) {
    			@Override
    			public Boolean getValue(InventoryLotDto object) {
    		    	  return selectionModel.isSelected(object);
    			}
    		};
    	}
    	return null;
    }
    
    public static String getHeaderString(InventoryLotHeader header) {
    	switch (header) {
    	case LotCode: return "Lot Code";
    	case Description: return "Description";
    	case Qty: return "Quantity";
    	case CheckedInDate: return "Checked-In Date";
    	case InUseDate: return "In-Use Date";
    	case UsedUpDate: return "Used-Up Date";
    	case RemoveCheckBox: return "Remove";
    	}
		return "";
    }
    
    /**
     * Removes trailing white space and upper-cases the lotCode. Checks that the lotCode is between 1 and 20 chars that are numbers, or letters.
     * @param lotCode
     * @return Returns null if lotCode is invalid.
     */
    public static String validateAndFormatLotCode(String lotCode) {
    	lotCode = lotCode.toUpperCase().trim();
		// lot code must be between 1 and 20 chars that are numbers, or letters.
		if (!lotCode.matches("[0-9A-Z\\s]{1,20}")) {
	    	Window.alert("'" + lotCode + "' is not a valid Lot Code.");
	    	return null;
		} else {
			return lotCode;
		}
    }
}
