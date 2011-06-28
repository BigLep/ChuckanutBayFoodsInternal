package com.chuckanutbay.webapp.lotmanagement.client;

import static com.chuckanutbay.webapp.lotmanagement.client.LotCodeUtil.log;

import java.util.Date;
import java.util.List;

import com.chuckanutbay.webapp.common.client.InventoryItemServiceAsync;
import com.chuckanutbay.webapp.common.client.InventoryLotServiceAsync;
import com.chuckanutbay.webapp.common.client.ServiceUtils.*;
import com.chuckanutbay.webapp.common.client.ServiceUtils;
import com.chuckanutbay.webapp.common.shared.InventoryItemDto;
import com.chuckanutbay.webapp.common.shared.InventoryLotDto;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RpcHelper {
	private final InventoryLotServiceAsync inventoryLotService;
	private final InventoryItemServiceAsync inventoryItemService;

	public RpcHelper() {
		inventoryLotService = ServiceUtils.createInventoryLotService();
		inventoryItemService = ServiceUtils.createInventoryItemService();
	}

	/**
	 * {@link AsyncCallback} for when nothing needs to be done with the response.
	 */
	private static final AsyncCallback<Void> voidCallback = new DefaultAsyncCallback<Void>() {
		@Override
		public void onSuccess(Void result) {
		}
	};

	public static AsyncCallback<List<InventoryItemDto>> createInventoryItemServiceCallback(final LotCodeManagerPanel senderObject, final List<InventoryItemDto> inventoryItemList) {
		return new DefaultAsyncCallback<List<InventoryItemDto>>() {
			@Override
			public void onSuccess(List<InventoryItemDto> dbQBItemList) {
				log("success on server");
				// TODO: This null check, clear, addAll sequence can be put into a utility method in the base module: CollectionUtils.replace(Collection<T> c, Collection<T> replacements)
				if (inventoryItemList != null) {
					inventoryItemList.clear();
				}
				inventoryItemList.addAll(dbQBItemList);
				// TODO: put this null and size check into a utility method within the base module: CollectionUtils.isEmpty(Collection c)
				if(inventoryItemList != null && inventoryItemList.size() > 0) {
					log("A good return is coming");
				}
				senderObject.populateFlexTable();
			}
		};
	}

	public static AsyncCallback<List<InventoryLotDto>> createInventoryLotServiceCallback(final LotCodeManagerPanel senderObject, final List<InventoryLotDto> inventoryLotList) {
		return new DefaultAsyncCallback<List<InventoryLotDto>>() {
			@Override
			public void onSuccess(List<InventoryLotDto> dbItemInInventoryList) {
				log("success on server");
				if (inventoryLotList != null) {
					inventoryLotList.clear();
				}
				inventoryLotList.addAll(dbItemInInventoryList);
				if(inventoryLotList != null && inventoryLotList.size() > 0) {
					log("A good return is coming");
				}
				senderObject.populateFlexTable();
			}
		};
	}

	// FIXME: you can get rid of all these methods.  Instead, callers can basically do this code instead directly.
	public void dbGetQBItems(List<InventoryItemDto> argQBItemList, LotCodeManagerPanel sender) {
		inventoryItemService.getInventoryItems(createInventoryItemServiceCallback(sender, argQBItemList));
	}

	public void dbGetCheckedInIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
		inventoryLotService.getUnusedIngredientLots(createInventoryLotServiceCallback(sender, argItemInInventoryList));
	}

	public void dbGetInUseIngredients(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
		inventoryLotService.getInUseIngredientLots(createInventoryLotServiceCallback(sender, argItemInInventoryList));
	}

	public void dbGetFullIngredientHistory(List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
		inventoryLotService.getFullIngredientHistory(createInventoryLotServiceCallback(sender, argItemInInventoryList));
	}

	public void dbGetLotCodeMatchIngredients(String lotCode, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
		inventoryLotService.getLotCodeMatchIngredients(lotCode, createInventoryLotServiceCallback(sender, argItemInInventoryList));
	}

	public void dbGetDateMatchInUseIngredients(Date date, List<InventoryLotDto> argItemInInventoryList, LotCodeManagerPanel sender) {
		inventoryLotService.getDateMatchInUseIngredients(date, createInventoryLotServiceCallback(sender, argItemInInventoryList));
	}

	public void dbSetCheckedInIngredients(List<InventoryLotDto> checkedInIngredients) {
		inventoryLotService.setUnusedIngredientLots(checkedInIngredients, voidCallback);
	}

	public void dbSetInUseIngredients(List<InventoryLotDto> inUseIngredients) {
		inventoryLotService.setInUseIngredientLots(inUseIngredients, voidCallback);
	}

	public void dbSetUsedUpIngredients(List<InventoryLotDto> usedUpIngredients) {
		inventoryLotService.setUsedUpInventoryLots(usedUpIngredients, voidCallback);
	}

}
