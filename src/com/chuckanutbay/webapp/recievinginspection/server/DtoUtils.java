package com.chuckanutbay.webapp.recievinginspection.server;

import java.util.ArrayList;
import java.util.Collection;

import com.chuckanutbay.businessobjects.InventoryItem;
import com.chuckanutbay.businessobjects.InventoryLot;
import com.chuckanutbay.documentation.Technology;
import com.chuckanutbay.webapp.recievinginspection.shared.InventoryItemDto;
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


	public static final Function<InventoryItem, InventoryItemDto> toInventoryItemDto = new Function<InventoryItem, InventoryItemDto>() {
		@Override
		public InventoryItemDto apply(InventoryItem input) {
			return new InventoryItemDto(input.getId(), input.getDescription());
		}
	};
}
