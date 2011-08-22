package com.chuckanutbay.webapp.traylabelgenerator.client;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class OpenOrdersTreeModel implements TreeViewModel {
		
		private final MultiSelectionModel<SalesOrderLineItemDto> selectionModel = new MultiSelectionModel<SalesOrderLineItemDto>();
		private final DefaultSelectionEventManager<SalesOrderLineItemDto> selectionManager = DefaultSelectionEventManager.createCheckboxManager();
		private final List<SalesOrderLineItemDto> data;
		private String currentFlavor;
		
		public OpenOrdersTreeModel(final List<SalesOrderLineItemDto> lineItems) {
			data = lineItems;
		}
		
		@Override
		public <T> NodeInfo<?> getNodeInfo(T value) {
			if (value == null) {
		        // LEVEL 0.
		        // We passed null as the root value. Return the flavors.
				
				List<String> flavors = newArrayList();
				for (SalesOrderLineItemDto lineItem : data) {
					String flavor;
					if (lineItem.getSubItemDto() == null) {
						flavor = lineItem.getQuickbooksItemDto().getFlavor();
					} else {
						flavor = lineItem.getQuickbooksItemDto().getFlavor();
					}
					if (!flavors.contains(flavor)) {
						flavors.add(flavor);
					}
				}

		        // Create a data provider that contains the list of composers.
		        ListDataProvider<String> dataProvider = new ListDataProvider<String>(flavors);

		        // Create a cell to display a composer.
		        Cell<String> cell = new AbstractCell<String>() {
					@Override
					public void render(
							com.google.gwt.cell.client.Cell.Context context,
							String value, SafeHtmlBuilder sb) {
						if (value != null) {
							sb.appendEscaped(value);
						}
					}
		        };

		        // Return a node info that pairs the data provider and the cell.
		        return new DefaultNodeInfo<String>(dataProvider, cell);
		        
		      } else if (value instanceof String) {
		    	  // LEVEL 1.
		    	  // We want the children of the flavor. Return the salesOrders.
		    	  
		    	  currentFlavor = (String) value;
		    	  
		    	  List<SalesOrderDto> salesOrders = newArrayList();
		    	  
		    	  for (SalesOrderLineItemDto lineItem : data) {
		    		  SalesOrderDto salesOrder = lineItem.getSalesOrderDto();
		    		  String flavor;
		    		  if (lineItem.getSubItemDto() == null) {
		    			  flavor = lineItem.getQuickbooksItemDto().getFlavor();
		    		  } else {
		    			  flavor = lineItem.getQuickbooksItemDto().getFlavor();
		    		  }
		    		  if(flavor.equals(value) && !salesOrders.contains(salesOrder)) {
		    			  salesOrders.add(salesOrder);
		    		  }
		    	  }
		    	  
		    	  ListDataProvider<SalesOrderDto> dataProvider = new ListDataProvider<SalesOrderDto>(salesOrders);
		    	  Cell<SalesOrderDto> cell = new AbstractCell<SalesOrderDto>() {
		    		  @Override
		    		  public void render(Context context, SalesOrderDto value, SafeHtmlBuilder sb) {
		    			  if (value != null) {
		    				  sb.appendEscaped(
		    						  value.getCustomerName() + " " + 
		    						  DateTimeFormat.getFormat("M/d").format(value.getShipdate()));
		    			  }
		    		  }
		    	  };
		    	  return new DefaultNodeInfo<SalesOrderDto>(dataProvider, cell);
		    	  
		      } else if (value instanceof SalesOrderDto) {
		    	  // LEVEL 2 - LEAF.
		    	  // We want the children of the salesOrderLineItem. Return the songs.
		    	  List<SalesOrderLineItemDto> lineItems = newArrayList();
		    	  
		    	  for (SalesOrderLineItemDto lineItem : data) {
		    		  String flavor;
		    		  if (lineItem.getSubItemDto() == null) {
		    			  flavor = lineItem.getQuickbooksItemDto().getFlavor();
		    		  } else {
		    			  flavor = lineItem.getQuickbooksItemDto().getFlavor();
		    		  }
		    		  if(lineItem.getSalesOrderDto().equals(value) && flavor.equals(currentFlavor)) {
		    			  lineItems.add(lineItem);
		    		  }
		    	  }
		    	  
		    	  ListDataProvider<SalesOrderLineItemDto> dataProvider = new ListDataProvider<SalesOrderLineItemDto>(lineItems);
		    	// Construct a composite cell that includes a checkbox.
		    	  // Following form Gwt Showcase CellBrowser example.
		    	    List<HasCell<SalesOrderLineItemDto, ?>> hasCells = new ArrayList<HasCell<SalesOrderLineItemDto, ?>>();
		    	    hasCells.add(new HasCell<SalesOrderLineItemDto, Boolean>() {

		    	      private CheckboxCell cell = new CheckboxCell(true, false);

		    	      @Override
					public Cell<Boolean> getCell() {
		    	        return cell;
		    	      }

		    	      @Override
					public FieldUpdater<SalesOrderLineItemDto, Boolean> getFieldUpdater() {
		    	        return null;
		    	      }

		    	      @Override
					public Boolean getValue(SalesOrderLineItemDto object) {
		    	        return selectionModel.isSelected(object);
		    	      }
		    	    });
		    	    hasCells.add(new HasCell<SalesOrderLineItemDto, SalesOrderLineItemDto>() {

		    	    	Cell<SalesOrderLineItemDto> cell = new AbstractCell<SalesOrderLineItemDto>() {
				    		  @Override
				    		  public void render(Context context, SalesOrderLineItemDto value, SafeHtmlBuilder sb) {
				    			  if (value != null) {
				    				  sb.appendEscaped("Item: " + value.getQuickbooksItemDto().getId())
				    				  .appendHtmlConstant("<br>")
				    				  .appendEscaped("Cases: " + value.getCases());
				    			  }
				    		  }
				    	  };

		    	      @Override
					public Cell<SalesOrderLineItemDto> getCell() {
		    	        return cell;
		    	      }

		    	      @Override
					public FieldUpdater<SalesOrderLineItemDto, SalesOrderLineItemDto> getFieldUpdater() {
		    	        return null;
		    	      }

		    	      @Override
					public SalesOrderLineItemDto getValue(SalesOrderLineItemDto object) {
		    	        return object;
		    	      }
		    	    });
		    	    Cell<SalesOrderLineItemDto> cell = new CompositeCell<SalesOrderLineItemDto>(hasCells) {
		    	      @Override
		    	      public void render(Context context, SalesOrderLineItemDto value, SafeHtmlBuilder sb) {
		    	        sb.appendHtmlConstant("<table><tbody><tr>");
		    	        super.render(context, value, sb);
		    	        sb.appendHtmlConstant("</tr></tbody></table>");
		    	      }

		    	      @Override
		    	      protected Element getContainerElement(Element parent) {
		    	        // Return the first TR element in the table.
		    	        return parent.getFirstChildElement().getFirstChildElement().getFirstChildElement();
		    	      }

		    	      @Override
		    	      protected <X> void render(Context context, SalesOrderLineItemDto value,
		    	          SafeHtmlBuilder sb, HasCell<SalesOrderLineItemDto, X> hasCell) {
		    	        Cell<X> cell = hasCell.getCell();
		    	        sb.appendHtmlConstant("<td>");
		    	        cell.render(context, hasCell.getValue(value), sb);
		    	        sb.appendHtmlConstant("</td>");
		    	      }
		    	    };
		    	  
		    	  return new DefaultNodeInfo<SalesOrderLineItemDto>(dataProvider, cell, selectionModel, selectionManager, null);
		      }
			return null;
		}

		@Override
		public boolean isLeaf(Object value) {
			// The leaf nodes are SalesOrderLineItemDtos
			if (value instanceof SalesOrderLineItemDto) {
				return true;
			}
			return false;
		}
		
		public MultiSelectionModel<SalesOrderLineItemDto> getSelectionModel() {
			return selectionModel;
		}
	}