package com.chuckanutbay.webapp.common.server;

import static com.chuckanutbay.businessobjects.util.BusinessObjectsUtil.getCakesPerCase;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chuckanutbay.businessobjects.Activity;
import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.EmployeeWorkIntervalActivityPercentage;
import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.businessobjects.QuickbooksItem;
import com.chuckanutbay.businessobjects.QuickbooksSubItem;
import com.chuckanutbay.businessobjects.SalesOrder;
import com.chuckanutbay.businessobjects.SalesOrderLineItem;
import com.chuckanutbay.businessobjects.TrayLabel;
import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.QuickbooksItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.SalesOrderLineItemHibernateDao;
import com.chuckanutbay.businessobjects.dao.TrayLabelHibernateDao;
import com.chuckanutbay.documentation.Technology;
import com.chuckanutbay.webapp.common.client.CollectionsUtils;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalDto;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotStickerColorDto;
import com.chuckanutbay.webapp.common.shared.QuickbooksItemDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderDto;
import com.chuckanutbay.webapp.common.shared.SalesOrderLineItemDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class DtoUtils {

	/**
	 * {@link Technology#Gwt} friendly form of {@link Lists#transform(java.util.List, Function)}
	 * as it returns an {@link ArrayList}, which {@link Technology#Gwt} can handle.
	 * @param <F>
	 * @param <T>
	 * @param fromCollection
	 * @param function
	 * @return
	 */
	public static <F,T> ArrayList<T> transform(Collection<F> fromCollection, Function<F, T> function) {
		return Lists.newArrayList(Collections2.transform(fromCollection, function));
	}
	
	public static final Function<Activity,ActivityDto> toActivityDtoFunction = new Function<Activity, ActivityDto>() {
		@Override
		public ActivityDto apply(Activity input) {
			ActivityDto output = new ActivityDto();
			output.setId(input.getId());
			output.setName(input.getName());
			return output;
		}
	};
	
	public static final Function<ActivityDto, Activity> fromActivityDtoFunction = new Function<ActivityDto, Activity>() {

		@Override
		public Activity apply(ActivityDto input) {
			Activity output = new Activity();
			output.setId(input.getId());
			output.setName(input.getName());
			return output;
		}
		
	};
	
	public static final Function<Employee,EmployeeDto> toEmployeeDtoFunction = new Function<Employee, EmployeeDto>() {
		@Override
		public EmployeeDto apply(Employee input) {
			EmployeeDto output = new EmployeeDto();
			output.setId(input.getId());
			output.setFirstName(input.getFirstName());
			output.setLastName(input.getLastName());
			output.setBarcodeNumber(input.getBarcodeNumber());
			return output;
		}
	};
	
	public static final Function<EmployeeDto, Employee> fromEmployeeDtoFunction = new Function<EmployeeDto, Employee>() {

		@Override
		public Employee apply(EmployeeDto input) {
			Employee output = new Employee();
			output.setId(input.getId());
			output.setBarcodeNumber(input.getBarcodeNumber());
			output.setFirstName(input.getFirstName());
			output.setLastName(input.getLastName());
			return output;
		}
		
	};
	
	public static final Function<EmployeeWorkInterval,EmployeeWorkIntervalDto> toEmployeeWorkIntervalDtoFunction = new Function<EmployeeWorkInterval, EmployeeWorkIntervalDto>() {
		@Override
		public EmployeeWorkIntervalDto apply(EmployeeWorkInterval input) {
			EmployeeWorkIntervalDto output = new EmployeeWorkIntervalDto();
			output.setStartDateTime(input.getStartDateTime());
			output.setEndDateTime(input.getEndDateTime());
			output.setComment(input.getComment());
			return output;
		}
	};
	
	public static final Function<EmployeeWorkIntervalActivityPercentage, EmployeeWorkIntervalActivityPercentageDto> toEmployeeWorkIntervalActivityPercentageDtoFunction = new Function<EmployeeWorkIntervalActivityPercentage, EmployeeWorkIntervalActivityPercentageDto>() {
		@Override
		public EmployeeWorkIntervalActivityPercentageDto apply(EmployeeWorkIntervalActivityPercentage input) {
			EmployeeWorkIntervalActivityPercentageDto output = new EmployeeWorkIntervalActivityPercentageDto();
			output.setActivity(toActivityDtoFunction.apply(input.getActivity()));
			output.setPercentage(input.getPercentage());
			return output;
		}
	};
	
	public static final Function<EmployeeWorkIntervalActivityPercentageDto, EmployeeWorkIntervalActivityPercentage> fromEmployeeWorkIntervalActivityPercentageDtoFunction = new Function<EmployeeWorkIntervalActivityPercentageDto, EmployeeWorkIntervalActivityPercentage>() {

		@Override
		public EmployeeWorkIntervalActivityPercentage apply(
				EmployeeWorkIntervalActivityPercentageDto input) {
			EmployeeWorkIntervalActivityPercentage output = new EmployeeWorkIntervalActivityPercentage();
			output.setActivity(fromActivityDtoFunction.apply(input.getActivity()));
			output.setPercentage(input.getPercentage());
			return output;
		}
		
	};

	public static final Function<InventoryLot, InventoryLotDto> toInventoryLotDtoFunction = new Function<InventoryLot, InventoryLotDto>() {
		@Override
		public InventoryLotDto apply(InventoryLot input) {
			InventoryLotDto output = new InventoryLotDto();
			output.setId(input.getId());
			output.setInventoryLotStickerColor(DtoUtils.toInventoryLotStickerColorDtoFunction.apply(input.getInventoryLotStickerColor()));
			output.setCode(input.getCode());
			output.setInventoryItem(DtoUtils.toInventoryItemDtoFunction.apply(input.getInventoryItem()));
			output.setQuantity(input.getQuantity());
			output.setReceivedDatetime(input.getReceivedDatetime());
			output.setStartUseDatetime(input.getStartUseDatetime());
			output.setEndUseDatetime(input.getEndUseDatetime());
			return output;
		}
	};

	public static final Function<InventoryLotDto, InventoryLot> fromInventoryLotDtoFunction = new Function<InventoryLotDto, InventoryLot>() {
		@Override
		public InventoryLot apply(InventoryLotDto input) {
			InventoryLot output = new InventoryLot();
			output.setId(input.getId());
			if (input.getInventoryLotStickerColor() != null) {
				output.setInventoryLotStickerColor(DtoUtils.fromInventoryLotStickerColorDtoFunction.apply(input.getInventoryLotStickerColor()));
			}
			output.setCode(input.getCode());
			InventoryItemDao itemDao = new InventoryItemHibernateDao();
			output.setInventoryItem(itemDao.findById(input.getInventoryItem().getId()));
			output.setQuantity(input.getQuantity());
			output.setReceivedDatetime(input.getReceivedDatetime());
			output.setStartUseDatetime(input.getStartUseDatetime());
			output.setEndUseDatetime(input.getEndUseDatetime());
			return output;
		}
	};
	
	public static final Function<InventoryLotStickerColor, InventoryLotStickerColorDto> toInventoryLotStickerColorDtoFunction = new Function<InventoryLotStickerColor, InventoryLotStickerColorDto>() {
		@Override
		public InventoryLotStickerColorDto apply(InventoryLotStickerColor input) {
			InventoryLotStickerColorDto output = new InventoryLotStickerColorDto();
			output.setId(input.getId());
			output.setName(input.getName());
			return output;
		}
	};
	
	public static final Function<InventoryLotStickerColorDto, InventoryLotStickerColor> fromInventoryLotStickerColorDtoFunction = new Function<InventoryLotStickerColorDto, InventoryLotStickerColor>() {

		@Override
		public InventoryLotStickerColor apply(InventoryLotStickerColorDto input) {
			InventoryLotStickerColor output = new InventoryLotStickerColor();
			output.setId(input.getId());
			output.setName(input.getName());
			return output;
		}
		
	};

	public static final Function<InventoryItem, InventoryItemDto> toInventoryItemDtoFunction = new Function<InventoryItem, InventoryItemDto>() {
		@Override
		public InventoryItemDto apply(InventoryItem input) {
			return new InventoryItemDto(input.getId(), input.getDescription());
		}
	};

	public static final Function<InventoryItemDto, InventoryItem> fromInventoryItemDtoFunction = new Function<InventoryItemDto, InventoryItem>() {
		@Override
		public InventoryItem apply(InventoryItemDto input) {
			return new InventoryItem(input.getId(), input.getDescription());
		}
	};
	
	public static final Function<List<SalesOrder>, Set<SalesOrderLineItemDto>> toSalesOrderLineItemDtosFunction = new Function<List<SalesOrder>, Set<SalesOrderLineItemDto>>() {

		@Override
		public Set<SalesOrderLineItemDto> apply(List<SalesOrder> salesOrders) {
			Set<SalesOrderLineItemDto> lineItems = new HashSet<SalesOrderLineItemDto>();
			for (SalesOrder salesOrder : salesOrders) {
				for (SalesOrderLineItem lineItem : salesOrder.getSalesOrderLineItems()) {
					Set<QuickbooksSubItem> subItems = lineItem.getQuickbooksItem().getSubItems();
					if (subItems != null && !subItems.isEmpty()) {//This is an item with sub items
						for (QuickbooksSubItem subItem : subItems) {
							SalesOrderLineItemDto lineItemDto = new SalesOrderLineItemDto();
							lineItemDto.setId(lineItem.getId());
							lineItemDto.setSalesOrderDto(DtoUtils.toSalesOrderDtoFunction.apply(salesOrder));
							lineItemDto.setCases(lineItem.getCases());
							lineItemDto.setQuickbooksItemDto(DtoUtils.toQuickbooksItemDtoFunction.apply(lineItem.getQuickbooksItem()));
							lineItemDto.setSubItemDto(DtoUtils.toQuickbooksItemDtoFromSubItemFunction.apply(subItem));
							lineItems.add(lineItemDto);
						}
					} else {//There are no sub items
						SalesOrderLineItemDto lineItemDto = new SalesOrderLineItemDto();
						lineItemDto.setId(lineItem.getId());
						lineItemDto.setCases(lineItem.getCases());
						lineItemDto.setSalesOrderDto(DtoUtils.toSalesOrderDtoFunction.apply(salesOrder));
						lineItemDto.setQuickbooksItemDto(DtoUtils.toQuickbooksItemDtoFunction.apply(lineItem.getQuickbooksItem()));
						lineItems.add(lineItemDto);
					}
				}
			}
			return lineItems;
		}
		
	};
	
	public static Map<String, QuickbooksItemDto> toQuickbooksItemDtos(List<QuickbooksItem> quickbooksItems) {
		Map<String, QuickbooksItemDto> dtos = new HashMap<String, QuickbooksItemDto>();
		for (QuickbooksItem qbItem : quickbooksItems) {
			if (!CollectionsUtils.isEmpty(qbItem.getSubItems())) {
				for (QuickbooksSubItem subItem : qbItem.getSubItems()) {
					QuickbooksItemDto qbItemDto = new QuickbooksItemDto();
					qbItemDto.setId(qbItem.getId() + " " + subItem.getSubItem().getFlavor());
					qbItemDto.setInstructions(qbItem.getInstructions());
					qbItemDto.setFlavor(subItem.getSubItem().getFlavor());
					qbItemDto.setSize(subItem.getSubItem().getSize());
					qbItemDto.setShortName(subItem.getSubItem().getShortName());
					if (qbItem.getCasesPerTray() != null && subItem.getCakesPerCase() != null) {
						qbItemDto.setCasesPerTray(qbItem.getCasesPerTray() * (getCakesPerCase(qbItem) / subItem.getCakesPerCase()));
						qbItemDto.setCakesPerCase(subItem.getCakesPerCase());
					}
					dtos.put(qbItemDto.getId(), qbItemDto);
				}
			} else {
				QuickbooksItemDto qbItemDto = new QuickbooksItemDto();
				qbItemDto.setId(qbItem.getId());
				qbItemDto.setFlavor(qbItem.getFlavor());
				qbItemDto.setSize(qbItem.getSize());
				qbItemDto.setShortName(qbItem.getShortName());
				qbItemDto.setInstructions(qbItem.getInstructions());
				if (qbItem.getCasesPerTray() != null) {
					qbItemDto.setCasesPerTray(qbItem.getCasesPerTray());
				}
				qbItemDto.setCakesPerCase(getCakesPerCase(qbItem));
				dtos.put(qbItemDto.getId(), qbItemDto);
			}
		}
		return dtos;
	}
	
	public static final Function<SalesOrder, SalesOrderDto> toSalesOrderDtoFunction = new Function<SalesOrder, SalesOrderDto>() {

		@Override
		public SalesOrderDto apply(SalesOrder input) {
			SalesOrderDto output = new SalesOrderDto();
			output.setId(input.getId());
			output.setCustomerName(input.getCustomerName());
			output.setCustomerInstructions(input.getSpecialInstructions());
			output.setShipdate(input.getShipDate());
			return output;
		}
		
	};
	
	public static final Function<SalesOrderLineItem, SalesOrderLineItemDto> toSalesOrderLineItemDtoFunction = new Function<SalesOrderLineItem, SalesOrderLineItemDto>() {

		@Override
		public SalesOrderLineItemDto apply(SalesOrderLineItem input) {
			SalesOrderLineItemDto output = new SalesOrderLineItemDto();
			output.setId(input.getId());
			output.setCases(input.getCases());
			output.setSalesOrderDto(toSalesOrderDtoFunction.apply(input.getSalesOrder()));
			output.setQuickbooksItemDto(toQuickbooksItemDtoFunction.apply(input.getQuickbooksItem()));
			return output;
		}
		
	};
	
	public static final Function<QuickbooksItem, QuickbooksItemDto> toQuickbooksItemDtoFunction = new Function<QuickbooksItem, QuickbooksItemDto>() {

		@Override
		public QuickbooksItemDto apply(QuickbooksItem input) {
			if (input != null) {
				QuickbooksItemDto output = new QuickbooksItemDto();
				output.setId(input.getId());
				output.setInstructions(input.getInstructions());
				output.setFlavor(input.getFlavor());
				output.setSize(input.getSize());
				output.setShortName(input.getShortName());
				if (input.getCasesPerTray() != null) {
					output.setCasesPerTray(input.getCasesPerTray());
				}
				output.setCakesPerCase(getCakesPerCase(input));
				return output;
			} else {
				return null;
			}
		}
		
	};
	
	public static final Function<QuickbooksItemDto, QuickbooksItem> fromQuickbooksItemDtoFunction = new Function<QuickbooksItemDto, QuickbooksItem>() {

		@Override
		public QuickbooksItem apply(QuickbooksItemDto input) {
			QuickbooksItem output = new QuickbooksItem();
			output.setId(input.getId());
			output.setInstructions(input.getInstructions());
			output.setFlavor(input.getFlavor());
			output.setSize(input.getSize());
			output.setShortName(input.getShortName());
			output.setCasesPerTray(input.getCasesPerTray());
			return output;
		}
		
	};
	
	public static final Function<QuickbooksSubItem, QuickbooksItemDto> toQuickbooksItemDtoFromSubItemFunction = new Function<QuickbooksSubItem, QuickbooksItemDto>() {

		@Override
		public QuickbooksItemDto apply(QuickbooksSubItem input) {
			QuickbooksItem subItem = input.getSubItem();
			QuickbooksItemDto output = new QuickbooksItemDto();
			output.setId(subItem.getId());
			output.setInstructions(subItem.getInstructions());
			output.setFlavor(subItem.getFlavor());
			output.setSize(subItem.getSize());
			output.setShortName(subItem.getShortName());
			if (input.getCakesPerCase() != null && input.getCakesPerCase() != null && subItem.getCasesPerTray() != null) {
				output.setCasesPerTray(subItem.getCasesPerTray() * (getCakesPerCase(subItem) / input.getCakesPerCase()));
				output.setCakesPerCase(input.getCakesPerCase());
			}
			return output;
		}
		
	};
	
	public static final Function<TrayLabel, TrayLabelDto> toTrayLabelDtoFunction = new Function<TrayLabel, TrayLabelDto>() {

		@Override
		public TrayLabelDto apply(TrayLabel input) {
			TrayLabelDto output = new TrayLabelDto();
			output.setId(input.getId());
			if (input.getQuickbooksItem() != null) {//It is an Inventory Tray Label
				output.setSalesOrderLineItemDto(new SalesOrderLineItemDto("INVENTORY", toQuickbooksItemDtoFunction.apply(input.getQuickbooksItem()), DtoUtils.toQuickbooksItemDtoFunction.apply(input.getQuickbooksSubItem())));
			} else {//It is a normal Tray Label
				output.setSalesOrderLineItemDto(toSalesOrderLineItemDtoFunction.apply(input.getSalesOrderLineItem()));
				if (input.getQuickbooksSubItem() != null) {
					output.getSalesOrderLineItemDto().setSubItemDto(toQuickbooksItemDtoFunction.apply(input.getQuickbooksSubItem()));
				}
			}
			output.setCases(input.getCases());
			if (input.getCakesPerCase() != null && input.getCasesPerTray() != null) {
				output.setCakesPerCase(input.getCakesPerCase());
				output.setCasesPerTray(input.getCasesPerTray());
			}
			output.setMaximumCases(input.getCases());
			output.setLotCode(input.getLotCode());
			return output;
		}
		
	};
	
	public static final Function<TrayLabelDto, List<TrayLabel>> fromTrayLabelDtoFunction = new Function<TrayLabelDto, List<TrayLabel>>() {

		@Override
		public List<TrayLabel> apply(TrayLabelDto input) {
			if (input.getId() != null) {//This Tray Label is already in the database
				return newArrayList(new TrayLabelHibernateDao().findById(input.getId()));
			} else {//It isn't in the database
				SalesOrderLineItemDto lineItemDto = input.getSalesOrderLineItemDto();
				List<TrayLabel> trayLabels = newArrayList();
				double casesPerTray = getCasesPerTray(lineItemDto);
				if (casesPerTray != 0.0) {
					double remainder;
					for (remainder = (input.getCases()/casesPerTray); remainder >= 1; remainder--) {
						trayLabels.add(convert(lineItemDto, casesPerTray, input));
					}
					if (remainder > 0) {
						trayLabels.add(convert(lineItemDto, remainder*casesPerTray, input));
					}
				}
				return trayLabels;
			}
		}
		
		private TrayLabel convert(SalesOrderLineItemDto input, double cases, TrayLabelDto trayLabelDto) {
			TrayLabel output = new TrayLabel();
			output.setCases(cases);
			output.setCakesPerCase(trayLabelDto.getCakesPerCase());
			output.setCasesPerTray(trayLabelDto.getCasesPerTray());
			output.setLotCode(trayLabelDto.getLotCode());
			QuickbooksItemDto subItemDto = input.getSubItemDto();
			if (subItemDto != null) {
				output.setQuickbooksSubItem(new QuickbooksItemHibernateDao().findById(subItemDto.getId()));
			}
			if (input.getSalesOrderDto().getCustomerName().equals("INVENTORY")) {//It is an inventory tray label
				String id = input.getQuickbooksItemDto().getId();
				int indexOfSpace = id.indexOf(" ");
				if (indexOfSpace == -1) {//No sub items
					output.setQuickbooksItem(new QuickbooksItemHibernateDao().findById(id));
				} else {//Has a sub item
					output.setQuickbooksItem(new QuickbooksItemHibernateDao().findById(id.substring(0, indexOfSpace)));
					for (QuickbooksSubItem subItem : output.getQuickbooksItem().getSubItems()) {
						if (subItem.getSubItem().getFlavor().equals(id.substring(indexOfSpace + 1))) {//Is the correct sub item
							output.setQuickbooksSubItem(subItem.getSubItem());
							break;
						}
					}
				}
				
			} else {//It is a normal tray label
				output.setSalesOrderLineItem(new SalesOrderLineItemHibernateDao().findById(input.getId()));
			}
			return output;
		}
		
		private double getCasesPerTray(SalesOrderLineItemDto input) {
			if (input.getSubItemDto() == null) {//No sub item
				return input.getQuickbooksItemDto().getCasesPerTray();
			} else {//has sub item
				return input.getSubItemDto().getCasesPerTray();
			}
		}
		
	};
}
