package com.chuckanutbay.webapp.traylabelgenerator.client;

import static com.chuckanutbay.webapp.common.client.GwtUtils.isNotNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.ListDataProvider;

public class OldOpenOrdersTreeModel extends OpenOrdersTreeModel {

	private String currentBatterType;
	private ListDataProvider<String> batterDataProvider = new ListDataProvider<String>(new ArrayList<String>());

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		if (value == null) {
			// LEVEL 0.
			// We passed null as the root value. Return the flavors.

			updateBatterDataProvider();
			// Create a cell to display a composer.
			Cell<String> cell = new AbstractCell<String>() {
				@Override
				public void render(
						Context context,
						String value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(value);
					}
				}
			};

			// Return a node info that pairs the data provider and the cell.
			return new DefaultNodeInfo<String>(batterDataProvider, cell);

		} else if (value instanceof String) {
			// LEVEL 1.
			// We want the children of the flavor. Return the salesOrders.

			currentBatterType = (String) value;
			updateSalesOrderDataProvider();
			Cell<SalesOrderDto> cell = new AbstractCell<SalesOrderDto>() {
				@Override
				public void render(Context context, SalesOrderDto value, SafeHtmlBuilder sb) {
					if (value != null) {
						sb.appendEscaped(
								value.getCustomerName() + " " + 
										value.getId());
					}
				}
			};
			return new DefaultNodeInfo<SalesOrderDto>(salesOrderDataProvider, cell);

		} else if (value instanceof SalesOrderDto) {
			// LEVEL 2 - LEAF.
			// We want the children of the salesOrderLineItem. Return the songs.

			currentSalesOrder = (SalesOrderDto) value;
			updateLineItemDataProvider();

			List<HasCell<SalesOrderLineItemDto, ?>> hasCells = new ArrayList<HasCell<SalesOrderLineItemDto, ?>>();
			hasCells.add(new HasCell<SalesOrderLineItemDto, String>() {

				private ButtonCell cell = new ButtonCell();

				@Override
				public Cell<String> getCell() {
					return cell;
				}

				@Override
				public FieldUpdater<SalesOrderLineItemDto, String> getFieldUpdater() {
					return new FieldUpdater<SalesOrderLineItemDto, String>() {
						@Override
						public void update(int index, SalesOrderLineItemDto object, String value) {
							boolean isSelected = getSelectionModel().isSelected(object); 
							if (isSelected) {
								getSelectionModel().setSelected(object, false);
							} else {
								getSelectionModel().setSelected(object, true);
							}
						}
					};
				}

				@Override
				public String getValue(SalesOrderLineItemDto object) {
					boolean isSelected = getSelectionModel().isSelected(object); 
					if (isSelected) {
						return "Remove";
					} else {
						return "Add";
					}
				}
			});
			hasCells.add(new HasCell<SalesOrderLineItemDto, SalesOrderLineItemDto>() {

				Cell<SalesOrderLineItemDto> cell = new AbstractCell<SalesOrderLineItemDto>() {
					@Override
					public void render(Context context, SalesOrderLineItemDto value, SafeHtmlBuilder sb) {
						if (value != null) {
							String itemString;
							String shortName = value.getQuickbooksItemDto().getShortName();
							if (isNotNullOrEmpty(shortName)) {
								itemString = shortName;
							} else {
								itemString = value.getQuickbooksItemDto().getId();
							}
							sb.appendEscaped("Item: " + itemString)
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

			return new DefaultNodeInfo<SalesOrderLineItemDto>(lineItemDataProvider, cell, selectionModel, selectionManager, null);
		}
		return null;
	}

	private void updateBatterDataProvider() {
		List<String> batterTypes = batterDataProvider.getList();
		batterTypes.clear();
		for (SalesOrderLineItemDto lineItem : data) {
			String batterType = getBatterType(lineItem);
			if (isNotNullOrEmpty(batterType) && !batterTypes.contains(batterType)) {//Flavor isn't null, already in the list, or blank
				batterTypes.add(batterType);
			}
		}
		Collections.sort(batterTypes, String.CASE_INSENSITIVE_ORDER);
	}

	protected void updateSalesOrderDataProvider() {
		List<SalesOrderDto> salesOrders = salesOrderDataProvider.getList();
		salesOrders.clear();

		for (SalesOrderLineItemDto lineItem : data) {
			SalesOrderDto salesOrder = lineItem.getSalesOrderDto();
			String batterType = getBatterType(lineItem);
			if(isNotNullOrEmpty(batterType) && batterType.equals(currentBatterType) && !salesOrders.contains(salesOrder)) {
				salesOrders.add(salesOrder);
			}
		}
		Collections.sort(salesOrders, new Comparator<SalesOrderDto>() {

			@Override
			public int compare(SalesOrderDto thing1,
					SalesOrderDto thing2) {
				int customerNameComparison = thing1.getCustomerName().compareTo(thing2.getCustomerName());
				if (customerNameComparison != 0) {
					return customerNameComparison;
				} else {
					return thing1.getId().compareTo(thing2.getId());
				}
			}

		});
	}

	protected void updateLineItemDataProvider() {
		List<SalesOrderLineItemDto> lineItems = lineItemDataProvider.getList();
		lineItems.clear();

		for (SalesOrderLineItemDto lineItem : data) {
			String batterType = getBatterType(lineItem);
			if(lineItem.getSalesOrderDto().equals(currentSalesOrder) && isNotNullOrEmpty(batterType) && batterType.equals(currentBatterType)) {
				lineItems.add(lineItem);
			}
		}
	}

	public void setData(Collection<SalesOrderLineItemDto> data) {
		super.data = newArrayList(data);
		updateBatterDataProvider();
	}

}