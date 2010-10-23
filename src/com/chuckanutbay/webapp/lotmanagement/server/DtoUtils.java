package com.chuckanutbay.webapp.lotmanagement.server;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryItemDto;
import com.chuckanutbay.webapp.lotmanagement.shared.InventoryLotDto;
import com.google.common.base.Function;

public class DtoUtils {
	
	public static final Function<InventoryLot, InventoryLotDto> toInventoryLotDto = new Function<InventoryLot, InventoryLotDto>() {
		@Override
		public InventoryLotDto apply(InventoryLot input) {
			InventoryLotDto output = new InventoryLotDto();
			output.setId(input.getId());
			output.setCode(input.getCode());
			output.setInventoryItem(new InventoryItemDto(input.getInventoryItem().getId(), input.getInventoryItem().getDescription()));
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
			output.setInventoryItem(new InventoryItem(input.getInventoryItem().getId(), input.getInventoryItem().getDescription()));
			output.setReceivedDatetime(input.getReceivedDatetime());
			output.setStartUseDatetime(input.getStartUseDatetime());
			output.setEndUseDatetime(input.getEndUseDatetime());
			return output;
		}
	};
	
	public static final Function<InventoryItem, InventoryItemDto> toInventoryItemDto = new Function<InventoryItem, InventoryItemDto>() {
		@Override
		public InventoryItemDto apply(InventoryItem input) {
			InventoryItemDto output = new InventoryItemDto(input.getId(), input.getDescription());
			return output;
		}
	};
	
	public static final Function<InventoryItemDto, InventoryItem> fromInventoryItemDto = new Function<InventoryItemDto, InventoryItem>() {
		@Override
		public InventoryItem apply(InventoryItemDto input) {
			InventoryItem output = new InventoryItem(input.getId(), input.getDescription());
			return output;
		}
	};
}
