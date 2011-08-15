package com.chuckanutbay.webapp.common.server;

import java.util.ArrayList;
import java.util.Collection;

import com.chuckanutbay.businessobjects.Activity;
import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.EmployeeWorkIntervalActivityPercentage;
import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.businessobjects.InventoryLotStickerColor;
import com.chuckanutbay.businessobjects.dao.InventoryItemDao;
import com.chuckanutbay.businessobjects.dao.InventoryItemHibernateDao;
import com.chuckanutbay.documentation.Technology;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalDto;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotStickerColorDto;
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
	
}
