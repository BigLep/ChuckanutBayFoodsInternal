package com.chuckanutbay.webapp.traylabelgenerator.client;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.client.CbCellTable;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;

public class TrayLabelGeneratorUtil {
	
	public static final DateTimeFormat SHORT_DATE_FORMAT = DateTimeFormat.getFormat("M/d");
	public static final NumberFormat SHORT_DECIMAL = NumberFormat.getFormat("#,##0.#");
	
	public static enum TrayLabelHeader {
		Customer, ShipDate, ItemId, LotCode, Cases, Cakes, Trays, Print, SalesOrder, Delete
	}
	
	public static final TrayLabelHeader[] NEW_TRAY_LABELS = {
		TrayLabelHeader.Customer,
		TrayLabelHeader.ShipDate,
		TrayLabelHeader.ItemId,
		TrayLabelHeader.LotCode,
		TrayLabelHeader.Cakes,
		TrayLabelHeader.Cases,
		TrayLabelHeader.Trays
	};
	
	public static final TrayLabelHeader[] EDIT_TRAY_LABELS = {
		TrayLabelHeader.Delete,
		TrayLabelHeader.SalesOrder,
		TrayLabelHeader.ItemId,
		TrayLabelHeader.Customer,
		TrayLabelHeader.ShipDate,
		TrayLabelHeader.LotCode,
		TrayLabelHeader.Cakes,
		TrayLabelHeader.Cases,
		TrayLabelHeader.Trays,
		TrayLabelHeader.Print
	};
	
	/**
	 * @see {@link #getColumn(TrayLabelHeader, CbCellTable, TrayLabelTableUpdateHandler)}
	 * @param cellTable
	 * @param updateHandler
	 * @param headers
	 * @return
	 */
	public static List<Column<TrayLabelDto, ?>> getColumns(final CbCellTable<TrayLabelDto> cellTable, TrayLabelTableUpdateHandler updateHandler, TrayLabelHeader...headers) {
		List<Column<TrayLabelDto, ?>> columns = newArrayList();
		for (TrayLabelHeader header : headers) {
			columns.add(getColumn(header, cellTable, updateHandler));
		}
		return columns;
	}
	
	/**
	 * Generates a column for the given {@link TrayLabelHeader}
	 * @param header 
	 * @param cellTable Used to get get selection model, and redraw when cells change.
	 * @param updateHandler {@link TrayLabelTableUpdateHandler#onTrayLabelUpdate(TrayLabelDto)} called when a cell is updated.
	 * @return
	 */
	public static Column<TrayLabelDto, ?> getColumn(TrayLabelHeader header, final CbCellTable<TrayLabelDto> cellTable, final TrayLabelTableUpdateHandler updateHandler) {
		switch (header) {
		case Customer:
    	    return new TextColumn<TrayLabelDto>() {
    	        @Override
    	        public String getValue(TrayLabelDto object) {
    	        	String customerName = object.getSalesOrderLineItemDto().getSalesOrderDto().getCustomerName();
    	        	if (customerName == null) {
    	        		return "";
    	        	} else {
    	        		return customerName;
    	        	}
    	        }
    	    };
		case ShipDate:
    		return new TextColumn<TrayLabelDto>() {
    	    	@Override
    	    	public String getValue(TrayLabelDto object) {
    	    		Date shipDate = object.getSalesOrderLineItemDto().getSalesOrderDto().getShipdate();
    				if (shipDate == null) {
    	        		return "";
    	        	} else {
    	        		return SHORT_DATE_FORMAT.format(shipDate);
    	        	}
    	    	}
    	    };
		case ItemId:
			return new TextColumn<TrayLabelDto>() {
    	        @Override
    	        public String getValue(TrayLabelDto object) {
    	        	String itemId = object.getSalesOrderLineItemDto().getQuickbooksItemDto().getId();
    	        	if (itemId == null) {
    	        		return "";
    	        	} else {
    	        		return itemId;
    	        	}
    	        }
    	    };
		case LotCode:
			Column<TrayLabelDto, String> lotCodeColumn = new Column<TrayLabelDto, String>(new EditTextCell()) {

				@Override
				public String getValue(TrayLabelDto object) {
					String lotCode = object.getLotCode();
    	        	if (lotCode == null) {
    	        		return "";
    	        	} else {
    	        		return lotCode;
    	        	}
				}
				
    	    };
    	    lotCodeColumn.setFieldUpdater(new FieldUpdater<TrayLabelDto, String>() {

				@Override
				public void update(int index, TrayLabelDto object, String value) {
					value = value.toUpperCase().trim();
					if(isValidLotCode(value)) {
						object.setLotCode(value);
						updateHandler.onTrayLabelUpdate(object);
					} else {
						Window.alert("Not a valid Lot Code. Valid example: 2B192A1");
					}
					cellTable.redraw();
				}
    	    	
    	    });
    	    return lotCodeColumn;
		case Cakes:
			Column<TrayLabelDto, String> cakesColumn = new Column<TrayLabelDto, String>(new EditTextCell()) {

				@Override
    	        public String getValue(TrayLabelDto object) {
					if (object.getCakesPerCase() != 0.0) {
						return SHORT_DECIMAL.format(object.getCakesPerCase() * object.getCases());
					} else {
						return "-";
					}
    	        }
				
    	    };
    	    cakesColumn.setFieldUpdater(new FieldUpdater<TrayLabelDto, String>() {

				@Override
				public void update(int index, TrayLabelDto object, String value) {
					double cakesPerCase;
					if (object.getSalesOrderLineItemDto().getSubItemDto() == null) {//No sub item
						cakesPerCase = object.getSalesOrderLineItemDto().getQuickbooksItemDto().getCakesPerCase();
					} else {//has sub item
						cakesPerCase = object.getSalesOrderLineItemDto().getSubItemDto().getCakesPerCase();
					}
					double maximumCakes = object.getMaximumCases() * cakesPerCase;
					try {
						double newCakesDouble = Double.parseDouble(value);
						if (newCakesDouble > maximumCakes) {
							throw new LargerValueException();
						} else {
							object.setCases(newCakesDouble / cakesPerCase);
							updateHandler.onTrayLabelUpdate(object);
						} 
					} catch (NumberFormatException nme){
						Window.alert("Not a valid decimal");
					} catch (LargerValueException e) {
						Window.alert("Cakes must be less than or equal to " + SHORT_DECIMAL.format(maximumCakes));
					}
					cellTable.redraw();
				}
    	    	
    	    });
    	    return cakesColumn;
		case Cases:
			Column<TrayLabelDto, String> casesColumn = new Column<TrayLabelDto, String>(new EditTextCell()) {

				@Override
    	        public String getValue(TrayLabelDto object) {
    	        	return SHORT_DECIMAL.format(object.getCases());
    	        }
				
    	    };
    	    casesColumn.setFieldUpdater(new FieldUpdater<TrayLabelDto, String>() {

				@Override
				public void update(int index, TrayLabelDto object, String value) {
					double maximumCases = object.getMaximumCases();
					try {
						double newCasesDouble = Double.parseDouble(value);
						if (newCasesDouble > maximumCases) {
							throw new LargerValueException();
						} else {
							object.setCases(newCasesDouble);
							updateHandler.onTrayLabelUpdate(object);
						}
					} catch (NumberFormatException nme){
						Window.alert("Not a valid decimal");
					} catch (LargerValueException e) {
						Window.alert("Cases must be less than or equal to " + SHORT_DECIMAL.format(maximumCases));
					}
					cellTable.redraw();
				}
    	    	
    	    });
    	    return casesColumn;
		case Trays:
			Column<TrayLabelDto, String> traysColumn = new Column<TrayLabelDto, String>(new EditTextCell()) {

				@Override
    	        public String getValue(TrayLabelDto object) {
					if (object.getCasesPerTray() != 0.0) {
						return SHORT_DECIMAL.format(object.getCases() / object.getCasesPerTray());
					} else {
						return "-";
					}
    	        	
    	        }
				
    	    };
    	    traysColumn.setFieldUpdater(new FieldUpdater<TrayLabelDto, String>() {

				@Override
				public void update(int index, TrayLabelDto object, String value) {
					double casesPerTray;
					if (object.getSalesOrderLineItemDto().getSubItemDto() == null) {//No sub item
						casesPerTray = object.getSalesOrderLineItemDto().getQuickbooksItemDto().getCasesPerTray();
					} else {//has sub item
						casesPerTray = object.getSalesOrderLineItemDto().getSubItemDto().getCasesPerTray();
					}
					double maximumTrays = object.getMaximumCases() / casesPerTray;
					try {
						double newTraysDouble = Double.parseDouble(value);
						if (newTraysDouble > maximumTrays) {
							throw new LargerValueException();
						} else {
							object.setCases(newTraysDouble * casesPerTray);
							updateHandler.onTrayLabelUpdate(object);
						}
					} catch (NumberFormatException nme){
						Window.alert("Not a valid decimal");
					} catch (LargerValueException e) {
						Window.alert("Trays must be less than or equal to " + SHORT_DECIMAL.format(maximumTrays));
					}
					cellTable.redraw();
				}
    	    	
    	    });
    	    return traysColumn;
		case SalesOrder:
			return new TextColumn<TrayLabelDto>() {
    	        @Override
    	        public String getValue(TrayLabelDto object) {
    	        	return "" + object.getSalesOrderLineItemDto().getSalesOrderDto().getId();
    	        }
    	    };
		case Print:
			Column<TrayLabelDto, String> printColumn = new Column<TrayLabelDto, String>(new ButtonCell()) {
    			@Override
    			public String getValue(TrayLabelDto object) {
    				return "Print";
    			}
    		};
    		printColumn.setFieldUpdater(new FieldUpdater<TrayLabelDto, String>() {
				@Override
				public void update(int index, TrayLabelDto object, String value) {
					boolean isSelected = cellTable.getSelectionModel().isSelected(object); 
					if (isSelected) {
    		    		cellTable.getSelectionModel().setSelected(object, false);
    		    	} else {
    		    		cellTable.getSelectionModel().setSelected(object, true);
    		    	}
				}
    		});
    		return printColumn;
		case Delete:
			Column<TrayLabelDto, String> deleteColumn = new Column<TrayLabelDto, String>(new ButtonCell()) {
    			@Override
    			public String getValue(TrayLabelDto object) {
    				return "Delete";
    			}
    		};
    		deleteColumn.setFieldUpdater(new FieldUpdater<TrayLabelDto, String>() {

				@Override
				public void update(int index, TrayLabelDto object, String value) {
					boolean deleteObject = Window.confirm("Are you sure you want to delete this Tray Label (" + object.getSalesOrderLineItemDto().getQuickbooksItemDto().getId() + " for " + object.getSalesOrderLineItemDto().getSalesOrderDto().getCustomerName() + ")? If so, click 'OK'"); 
					if (deleteObject) {
    		    		object.getSalesOrderLineItemDto().setQuickbooksItemDto(null);
    		    		updateHandler.onTrayLabelUpdate(object);
    		    		cellTable.removeTableData(object);
    		    	} 
				}
    			
    		});
    		return deleteColumn;
		}
		return null;
	}
	
	/**
	 * Converts any number of {@link TrayLabelHeader}'s into string values.
	 * @param headers
	 * @return
	 */
	public static List<String> getHeaderStrings(TrayLabelHeader...headers) {
		List<String> headerStrings = newArrayList();
		for (TrayLabelHeader header : headers) {
			headerStrings.add(getHeaderString(header));
		}
		return headerStrings;
	}
	
	/**
	 * Converts a {@link TrayLabelHeader} into a sting value.
	 * @param header
	 * @return
	 */
	public static String getHeaderString(TrayLabelHeader header) {
		switch (header) {
		case Customer: return "Cusotmer";
		case ShipDate: return "Ship Date";
		case ItemId: return "Item-Id";
		case LotCode: return "Lot Code";
		case Cases: return "Cases";
		case Cakes: return "Cakes";
		case Trays: return "Trays";
		case Print: return "Print";
		case SalesOrder: return "Sales Order";
		case Delete: return "Delete";
		}
		return "";
	}
	
	/**
	 * 
	 * @param lotCode
	 * @return true if lotCode is in proper format: crew (4A, 3B, 2C), day of year (<366), batch (A), year of decade (<10).
	 */
	public static boolean isValidLotCode(String lotCode) {
		boolean isValid = false;
		if (lotCode.matches("[0-9][A-Z][0-9][0-9][0-9][A-Z][0-9]")) {//Valid format
			String crew = lotCode.substring(0,2);
			if (crew.equals("4A") || crew.equals("3C") || crew.equals("2B")) {//Valid crew
				int dayOfYear = Integer.parseInt(lotCode.substring(2,5));
				if (dayOfYear <= 365) {//Valid day of year
					if (lotCode.substring(5,6).equals("A")) {//Valid batch
						if (Integer.parseInt(lotCode.substring(6,7)) < 10) {//Valid year of decade
							isValid = true;
						}
					}
				}
			}
		}
		return isValid;
	}
	
	public static List<TrayLabelDto> toTrayLabelDtos(Collection<SalesOrderLineItemDto> lineItems, String lotCode) {
		List<TrayLabelDto> trayLabels = newArrayList();
		for (SalesOrderLineItemDto lineItem : lineItems) {
			trayLabels.add(toTrayLabelDto(lineItem, lotCode));
		}
		return trayLabels;
	}
	
	public static TrayLabelDto toTrayLabelDto(SalesOrderLineItemDto lineItem, String lotCode) {
		
		double casesPerTray;
		double cakesPerCase;
		if (lineItem.getSubItemDto() == null) {//No sub item
			casesPerTray = lineItem.getQuickbooksItemDto().getCasesPerTray();
			cakesPerCase = lineItem.getQuickbooksItemDto().getCakesPerCase();
		} else {//has sub item
			casesPerTray = lineItem.getSubItemDto().getCasesPerTray();
			cakesPerCase = lineItem.getSubItemDto().getCakesPerCase();
		}
		
		return new TrayLabelDto()
			.setSalesOrderLineItemDto(lineItem)
			.setCases(lineItem.getCases())
			.setCasesPerTray(casesPerTray)
			.setCakesPerCase(cakesPerCase)
			.setMaximumCases(lineItem.getCases())
			.setLotCode(lotCode);
	}
	
	public static TrayLabelDto newInventoryTrayLabel(QuickbooksItemDto qbItem, String lotCode) {
		SalesOrderDto salesOrder = new SalesOrderDto();
		salesOrder.setCustomerName("INVENTORY");
		SalesOrderLineItemDto lineItem = new SalesOrderLineItemDto();
		lineItem.setSalesOrderDto(salesOrder);
		lineItem.setQuickbooksItemDto(qbItem);
		return new TrayLabelDto().setSalesOrderLineItemDto(lineItem)
				.setLotCode(lotCode)
				.setCases(0)
				.setMaximumCases(1000000000);
	}

}
