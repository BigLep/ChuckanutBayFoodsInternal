package com.chuckanutbay.webapp.lotmanagement.server;

import java.util.ArrayList;
import java.util.Collection;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Technology;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
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
}
