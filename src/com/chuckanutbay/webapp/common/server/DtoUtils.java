package com.chuckanutbay.webapp.common.server;

import java.util.ArrayList;
import java.util.Collection;

import com.chuckanutbay.businessobjects.Activity;
import com.chuckanutbay.businessobjects.Employee;
import com.chuckanutbay.businessobjects.EmployeeWorkInterval;
import com.chuckanutbay.businessobjects.EmployeeWorkIntervalActivityPercentage;
import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Technology;
import com.chuckanutbay.webapp.common.shared.ActivityDto;
import com.chuckanutbay.webapp.common.shared.Barcode;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalActivityPercentageDto;
import com.chuckanutbay.webapp.common.shared.EmployeeWorkIntervalDto;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
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
	
	public static EmployeeDto toEmployeeDto(Employee input) {
		EmployeeDto output = new EmployeeDto();
		output.setId(input.getId());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		output.setBarcodeNumber(new Barcode(input.getBarcodeNumber()));
		return output;
	}
	
	public static Employee fromEmployeeDto(EmployeeDto input) {
		Employee output = new Employee();
		output.setId(input.getId());
		output.setBarcodeNumber(input.getBarcodeNumber().getBarcodeNumber());
		output.setFirstName(input.getFirstName());
		output.setLastName(input.getLastName());
		return output;
	}
	
	public static ActivityDto toActivityDto(Activity input) {
		ActivityDto output = new ActivityDto();
		output.setId(input.getId());
		output.setName(input.getName());
		return output;
	}
	
	public static Activity fromActivityDto(ActivityDto input) {
		Activity output = new Activity();
		output.setId(input.getId());
		output.setName(input.getName());
		return output;
	}
	
	public static EmployeeWorkInterval fromEmployeeWorkIntervalDto(EmployeeWorkIntervalDto input) {
		EmployeeWorkInterval output = new EmployeeWorkInterval();
		output.setStartDateTime(input.getStartDateTime());
		output.setEndDateTime(input.getEndDateTime());
		return output;
	}
	
	public static EmployeeWorkIntervalDto toEmployeeWorkIntervalDto(EmployeeWorkInterval input) {
		EmployeeWorkIntervalDto output = new EmployeeWorkIntervalDto();
		output.setStartDateTime(input.getStartDateTime());
		output.setEndDateTime(input.getEndDateTime());
		return output;
	}
	
	public static EmployeeWorkIntervalActivityPercentageDto toEmployeeWorkIntervalActivityPercentageDto(EmployeeWorkIntervalActivityPercentage input) {
		EmployeeWorkIntervalActivityPercentageDto output = new EmployeeWorkIntervalActivityPercentageDto();
		output.setActivity(toActivityDto(input.getActivity()));
		output.setPercentage(input.getPercentage());
		return output;
	}
	
	public static EmployeeWorkIntervalActivityPercentage fromEmployeeWorkIntervalActivityPercentageDto(EmployeeWorkIntervalActivityPercentageDto input) {
		EmployeeWorkIntervalActivityPercentage output = new EmployeeWorkIntervalActivityPercentage();
		output.setActivity(fromActivityDto(input.getActivity()));
		output.setPercentage(input.getPercentage());
		return output;
	}

	public static final Function<InventoryLot, InventoryLotDto> toInventoryLotDto = new Function<InventoryLot, InventoryLotDto>() {
		@Override
		public InventoryLotDto apply(InventoryLot input) {
			InventoryLotDto output = new InventoryLotDto();
			output.setId(input.getId());
			output.setCode(input.getCode());
			output.setInventoryItem(DtoUtils.toInventoryItemDto.apply(input.getInventoryItem()));
			output.setReceivedDatetime(input.getReceivedDatetime());
			output.setStartUseDatetime(input.getStartUseDatetime());
			output.setEndUseDatetime(input.getEndUseDatetime());
			return output;
		}
	};

	public static final Function<InventoryLotDto, InventoryLot> fromInventoryLotDto = new Function<InventoryLotDto, InventoryLot>() {
		@Override
		public InventoryLot apply(InventoryLotDto input) {
			InventoryLot output = new InventoryLot();
			output.setId(input.getId());
			output.setCode(input.getCode());
			output.setInventoryItem(DtoUtils.fromInventoryItemDto.apply(input.getInventoryItem()));
			output.setQuantity(1);
			output.setReceivedDatetime(input.getReceivedDatetime());
			output.setStartUseDatetime(input.getStartUseDatetime());
			output.setEndUseDatetime(input.getEndUseDatetime());
			return output;
		}
	};

	public static final Function<InventoryItem, InventoryItemDto> toInventoryItemDto = new Function<InventoryItem, InventoryItemDto>() {
		@Override
		public InventoryItemDto apply(InventoryItem input) {
			return new InventoryItemDto(input.getId(), input.getDescription());
		}
	};

	public static final Function<InventoryItemDto, InventoryItem> fromInventoryItemDto = new Function<InventoryItemDto, InventoryItem>() {
		@Override
		public InventoryItem apply(InventoryItemDto input) {
			return new InventoryItem(input.getId(), input.getDescription());
		}
	};
	
	public static final Function<Activity, ActivityDto> toActivityDto = new Function<Activity, ActivityDto>() {
		@Override
		public ActivityDto apply(Activity input) {
			return toActivityDto(input);
		}
	};

	public static final Function<Employee, EmployeeDto> toEmployeeDto = new Function<Employee, EmployeeDto>() {

		@Override
		public EmployeeDto apply(Employee input) {
			return toEmployeeDto(input);
		}
		
	};
	
	public static final Function<EmployeeWorkIntervalActivityPercentageDto, EmployeeWorkIntervalActivityPercentage> fromEmployeeWorkIntervalActivityPercentageDto = new Function<EmployeeWorkIntervalActivityPercentageDto, EmployeeWorkIntervalActivityPercentage>() {

		@Override
		public EmployeeWorkIntervalActivityPercentage apply(
				EmployeeWorkIntervalActivityPercentageDto input) {
			return fromEmployeeWorkIntervalActivityPercentageDto(input);
		}
		
	};
	
	public static final Function<EmployeeWorkIntervalActivityPercentage, EmployeeWorkIntervalActivityPercentageDto> toEmployeeWorkIntervalActivityPercentageDto = new Function<EmployeeWorkIntervalActivityPercentage, EmployeeWorkIntervalActivityPercentageDto>() {

		@Override
		public EmployeeWorkIntervalActivityPercentageDto apply(
				EmployeeWorkIntervalActivityPercentage input) {
			return toEmployeeWorkIntervalActivityPercentageDto(input);
		}
		
	};
}
